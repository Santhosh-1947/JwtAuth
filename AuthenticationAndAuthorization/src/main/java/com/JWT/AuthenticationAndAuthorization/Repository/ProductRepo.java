package com.JWT.AuthenticationAndAuthorization.Repository;


import com.JWT.AuthenticationAndAuthorization.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {



}
