package com.shreyas.springEcom.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shreyas.springEcom.Service.ProductService;
import com.shreyas.springEcom.model.Products;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProductController {
    @Autowired
    private ProductService productService;
@GetMapping("/products")
public ResponseEntity <List<Products>> getProdcts(){
    return new ResponseEntity<List<Products>>(productService.getAllProducts(),HttpStatus.ACCEPTED);
}
@GetMapping("/product/{id}")
public ResponseEntity<Products> getProductById(@PathVariable int id) {
     Products products= productService.getProductById(id);
     if(products.getId()>0)
        return new ResponseEntity<>(products, HttpStatus.OK);
     else
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

}
@GetMapping("/product/{productId}/image")
public ResponseEntity<byte []> getImageByProductId(@PathVariable int productId)
{
    Products product=productService.getProductById(productId);
   if(product.getId()>0)
        return new ResponseEntity<>(product.getImageData(), HttpStatus.OK);
     else
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
}
public String getMethodName(@RequestParam String param) {
    return new String();
}

@PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Products product, @RequestPart MultipartFile imageFile) {
        Products savedProduct = null;
        try {
            savedProduct = productService.addOrUpdateProduct(product, imageFile);
            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

@PutMapping("product/{id}")
public ResponseEntity<String> updateProduct(@PathVariable int id, @RequestPart Products product, @RequestPart MultipartFile imageFile) {
    
    try {
        productService.addOrUpdateProduct(product,imageFile);
        return new ResponseEntity<>("Updated",HttpStatus.OK);
        
    } catch (IOException e) {
        return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
    }
    
}
@DeleteMapping("/product/{id}")
public ResponseEntity<String> deleteProduct(@PathVariable int id){
    Products product=productService.getProductById(id);
    if(product!=null){
        productService.deleteProduct(id);
        return new ResponseEntity<>("deletd",HttpStatus.OK);
    }
    else
       return new ResponseEntity<>(HttpStatus.NOT_FOUND);
}
@GetMapping("/products/search")
public ResponseEntity<List<Products>> searchProduct(@RequestParam String keyword) {
    List<Products> products=productService.searchProduct(keyword);
    return new ResponseEntity<>(products,HttpStatus.OK);
}


}
