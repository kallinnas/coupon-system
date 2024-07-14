package com.system.coupon.rest.controller;

import com.system.coupon.data.db.UserRepository;
import com.system.coupon.data.model.Token;
import com.system.coupon.rest.ClientSession;
import com.system.coupon.rest.ex.InvalidLoginException;
import com.system.coupon.rest.UserSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class LoginController {


    private Map<String, ClientSession> tokensMap;
    private UserSystem system;
    private UserRepository userRepo;
    private ApplicationContext context;

    @Autowired
    public LoginController(@Qualifier("tokens") Map<String, ClientSession> tokensMap, UserSystem system,
                           UserRepository repository, ApplicationContext context) {
        this.tokensMap = tokensMap;
        this.system = system;
        this.userRepo = repository;
        this.context = context;
    }




    @GetMapping("/{token}/getAccount")
    public ResponseEntity<Integer> customersAccount(@PathVariable String token){
        ClientSession session = tokensMap.get(token);
        return ResponseEntity.ok(session.getRole());
    }


}
