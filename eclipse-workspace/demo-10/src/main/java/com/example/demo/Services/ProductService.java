package com.example.demo.Services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Model.Product;
import com.example.demo.Repo.ProductRepo;

@Service
public class ProductService {
	@Autowired
  private	ProductRepo rr;
	public List<Product> addproducts()
	{
		return rr.findAll();
	}
	public Product getproductbyid(int id) {
		
		return rr.findById(id).orElse(null);
	}
	public Product addproducts(Product product, MultipartFile imagefile) throws IOException {
		product.setImageName(imagefile.getOriginalFilename());
		product.setImageType(imagefile.getContentType());
		product.setImageData(imagefile.getBytes());;
		return rr.save(product);
	}
	public Product updateproduct(int id, Product product, MultipartFile imagefile) throws IOException {

	    Product existing = rr.findById(id).orElse(null);

	    if(existing == null)
	        return null;
	    existing.setName(product.getName());
	    existing.setPrice(product.getPrice());
	    existing.setStockQuantity(product.getStockQuantity());
	    existing.setProductAvailable(product. getProductAvailable());
	    if(imagefile != null && !imagefile.isEmpty()) {
	        existing.setImageName(imagefile.getOriginalFilename());
	        existing.setImageType(imagefile.getContentType());
	        existing.setImageData(imagefile.getBytes());
	    }

	    return rr.save(existing);
	}
	public void deleteproduct(int id) {
		// TODO Auto-generated method stub
		rr.deleteById(id);
	}
	public List<Product> searchproducts(String keyword) {
		// TODO Auto-generated method stub
		return rr.searchproducts(keyword);
	}

}
