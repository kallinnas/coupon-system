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

    private static final int LENGTH_TOKEN = 15;
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


    @PostMapping("/login/{login}/{password}")
    public ResponseEntity<Token> login(@PathVariable String login, @PathVariable String password) throws InvalidLoginException {
            ClientSession session = system.createClientSession(login, password);
            String token = generateToken();
            tokensMap.put(token, session);
            Token myToken = new Token();
            myToken.setToken(token);
            return ResponseEntity.ok(myToken);
    }

    @GetMapping("/{token}/getAccount")
    public ResponseEntity<Integer> customersAccount(@PathVariable String token){
        ClientSession session = tokensMap.get(token);
        return ResponseEntity.ok(session.getRole());
    }

    private String generateToken() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, LENGTH_TOKEN);
    }
}
