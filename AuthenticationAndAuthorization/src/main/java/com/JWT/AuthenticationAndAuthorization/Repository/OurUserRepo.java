package com.JWT.AuthenticationAndAuthorization.Repository;


import com.JWT.AuthenticationAndAuthorization.Entities.OurUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface OurUserRepo extends JpaRepository<OurUsers, Integer> {

    Optional<OurUsers> findByEmail(String email);
}
