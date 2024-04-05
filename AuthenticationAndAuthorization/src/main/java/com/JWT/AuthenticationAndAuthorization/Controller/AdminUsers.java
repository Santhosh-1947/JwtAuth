package com.JWT.AuthenticationAndAuthorization.Controller;


import com.JWT.AuthenticationAndAuthorization.Dto.ReqRes;
import com.JWT.AuthenticationAndAuthorization.Entities.Product;
import com.JWT.AuthenticationAndAuthorization.Repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class AdminUsers {
    @Autowired
    private ProductRepo productRepo;

    @GetMapping("/public/product")
    public ResponseEntity<Object> getAllProducts()
    {
        return ResponseEntity.ok(productRepo.findAll());
    }

    @PostMapping("/admin/saveproducts")
    public ResponseEntity<Object> products(@RequestBody ReqRes productRequest){
        Product productToSave = new Product();
        productToSave.setName(productRequest.getName());
        return ResponseEntity.ok(productRepo.save(productToSave));

    }

    @GetMapping("/user/alone")
    public ResponseEntity<Object> userAlone()
    {
        return ResponseEntity.ok("Users alone can access this api..>>");
    }

    @GetMapping("/adminuser/both")
    public ResponseEntity<Object> bothAdminAndUsersApi()
    {
        return ResponseEntity.ok("Both Admin and user can access this api..>>");
    }


}
