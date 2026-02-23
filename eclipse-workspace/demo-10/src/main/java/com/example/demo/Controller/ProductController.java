package com.example.demo.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Model.Product;
import com.example.demo.Services.ProductService;

import tools.jackson.databind.ObjectMapper;
@CrossOrigin
@RestController
@RequestMapping("/api")
public class ProductController {
	@Autowired
  private ProductService service;
	@GetMapping("/products")
	public ResponseEntity< List<Product>> getproducts()
	{
		System.out.prntln("hello");
		List<Product> product = service.addproducts();
		return new ResponseEntity<>(product,HttpStatus.OK);
	}
	
	@GetMapping("/product/{id}")
	public ResponseEntity<Product> getproductbyid(@PathVariable int id)
	{
		Product product = service.getproductbyid(id);
		if(product != null)
		return new ResponseEntity<> (product, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping("/product")
	public ResponseEntity<?> addproduct(
	        @RequestPart("product") Product product,
	        @RequestPart("imageFile") MultipartFile imagefile) {

	    try {
	        Product product1 = service.addproducts(product, imagefile);
	        return new ResponseEntity<>(product1, HttpStatus.CREATED);

	    } catch (Exception e) {
	    	
	      
	        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	@GetMapping("/product/{id}/image")
	public ResponseEntity<byte[]> getimagebyid(@PathVariable int id) {

	    Product product = service.getproductbyid(id);

	    if (product.getImageData() == null) {
	        return ResponseEntity.notFound().build();
	    }

	    MediaType mediaType;

	    if (product.getImageType() != null) {
	        mediaType = MediaType.valueOf(product.getImageType());
	    } else {
	        mediaType = MediaType.APPLICATION_OCTET_STREAM; // fallback
	    }

	    return ResponseEntity.ok()
	            .contentType(mediaType)
	            .body(product.getImageData());
	}

    @PutMapping("/product/{id}")
    public ResponseEntity<String> Updateproduct(@PathVariable int id,
    		                                        @RequestPart Product product,
    		                                        @RequestPart(value = "imagefile",required = false) MultipartFile imagefile)
    {
    Product product1=null;
	try {
		product1 = service.updateproduct(id,product,imagefile);
	} catch (IOException e) {
		return new ResponseEntity<>("Not Updated",HttpStatus.BAD_REQUEST);
	}
    if(product1 != null)
    	  return new ResponseEntity<>("updated" ,HttpStatus.OK);
    else
    	  return new ResponseEntity<>("Not Uodated",HttpStatus.INTERNAL_SERVER_ERROR);
    }
   @DeleteMapping("/product/{id}")
   public ResponseEntity<String> deleteproduct(@PathVariable int id)
   {
	   Product product=service.getproductbyid(id);
	   if(product!=null)
	   {
	  service.deleteproduct(id);
	  return new ResponseEntity<>("deleted",HttpStatus.OK);
	   }
	   else
	   {
		   System.out.println("Hello");
		   return new ResponseEntity<>("Product not found",HttpStatus.NOT_FOUND);
	   }
   }
   @GetMapping("/products/search")
   public ResponseEntity<List<Product>> searchproducts(@RequestParam String keyword)
   {
	   List<Product> products = service.searchproducts(keyword);
	   return new ResponseEntity<>(products,HttpStatus.OK);
			   
   }
}
