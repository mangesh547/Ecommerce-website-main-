package com.shreyas.springEcom.Service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.shreyas.springEcom.Repository.ProductRepo;
import com.shreyas.springEcom.model.Products;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepo productRepo;
    public List<Products> getAllProducts() {
       return productRepo.findAll();
    }
    public Products getProductById(int id) {
        return productRepo.findById(id).orElse(new Products(-1));
    }
    
    public Products addOrUpdateProduct(Products product, MultipartFile image) throws IOException {
        product.setImageName(image.getOriginalFilename());
        product.setImageType(image.getContentType());
        product.setImageData(image.getBytes());

        return productRepo.save(product);
    }
    public void deleteProduct(int id) {
        productRepo.deleteById(id);
    }
    public List<Products> searchProduct(String keyword) {
        return productRepo.searchProduct(keyword);
    }
   
}
