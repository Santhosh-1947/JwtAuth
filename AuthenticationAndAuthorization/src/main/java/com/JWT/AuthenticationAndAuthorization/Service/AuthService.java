package com.JWT.AuthenticationAndAuthorization.Service;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.JWT.AuthenticationAndAuthorization.Dto.ReqRes;
import com.JWT.AuthenticationAndAuthorization.Entities.OurUsers;
import com.JWT.AuthenticationAndAuthorization.Repository.OurUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AuthService {

    @Autowired
    private OurUserRepo ourUserRepo;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    public ReqRes signUp(ReqRes registrationRequest) {
        ReqRes resp = new ReqRes();

    try {
        OurUsers ourUsers = new OurUsers();
        ourUsers.setEmail(registrationRequest.getEmail());
        ourUsers.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        ourUsers.setRole(registrationRequest.getRole());
        OurUsers ourUsersResult = ourUserRepo.save(ourUsers);
        if (ourUsersResult != null && ourUsersResult.getId() > 0) {
            resp.setOurUsers(ourUsersResult);
            resp.setMessage("User Saved Successfully");
            resp.setStatusCode(200);
        }
    }catch (Exception e)
        {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
    return resp;
    }
    public ReqRes signIn(ReqRes signInRequest){
        ReqRes response = new ReqRes();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest
                    .getEmail(),signInRequest.getPassword()));
            var user = ourUserRepo.findByEmail(signInRequest.getEmail()).orElseThrow();
            System.out.println("USER IS: "+user);
            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRefreshToken(refreshToken);
            response.setExpirationTime("24Hr");
            response.setMessage("Successfully Signed In..>>>");

        }catch (Exception e){

            response.setStatusCode(500);
            response.setError(e.getMessage());

        }
        return response;
    }
    public ReqRes refreshToken(ReqRes refreshTokenRequest)
    {
        ReqRes response = new ReqRes();
        String ourEmail = jwtUtils.extractUsername(refreshTokenRequest.getToken());
        OurUsers users = ourUserRepo.findByEmail(ourEmail).orElseThrow();
        if (jwtUtils.isTokenValid(refreshTokenRequest.getToken(),users)){
            var jwt = jwtUtils.generateToken(users);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRefreshToken(refreshTokenRequest.getToken());
            response.setExpirationTime("24Hr");
            response.setMessage("Successfully Refreshed the Token....");
        }
        response.setStatusCode(500);
        return response;
    }
}
