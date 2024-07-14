package com.system.coupon.rest.controller;

import com.system.coupon.data.ex.UnknownRoleForUserException;
import com.system.coupon.data.model.Token;
import com.system.coupon.ex.InvalidTokenException;
import com.system.coupon.ex.UserAlreadyExistException;
import com.system.coupon.rest.ClientSession;
import com.system.coupon.rest.UserSystem;
import com.system.coupon.rest.Utils;
import com.system.coupon.rest.ex.InvalidLoginException;
import com.system.coupon.security.DecryptRequestBody;
import com.system.coupon.service.ISecurityService;
import com.system.coupon.service.IUserService;
import com.system.coupon.service.ex.UserIsNotExistException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;

@Data
class CommunicationModel {
    private String token;
    private String email;
    private String password;
    private int role;
}

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthController {
    @Qualifier("tokens")
    private final Map<String, ClientSession> tokensMap;
    private final ISecurityService security;
    private final IUserService service;
    private final UserSystem system;

    @DecryptRequestBody
    @PostMapping("/register")
    public ResponseEntity<Token> registration(@RequestBody String requestBody) {
        try {
            JSONObject user = new JSONObject(requestBody);
            String email = user.getString("email");
            String password = user.getString("password");

            if (!service.isUserExist(email, password)) {
                service.createUser(email, password, user.getInt("role"));
            } else {
                throw new UserAlreadyExistException(String.format("User with email %s already exists.", email));
            }

            return login(requestBody);

        } catch (JSONException | UserAlreadyExistException | UnknownRoleForUserException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Token(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Token("Internal server error"));
        }
    }

    @DecryptRequestBody
    @PostMapping("/login")
    public ResponseEntity<Token> login(@RequestBody String requestBody) {
        try {
            JSONObject user = new JSONObject(requestBody);
            String email = user.getString("email");
            String password = user.getString("password");

            if (service.isUserExist(email, password)) {
                ClientSession session = system.createClientSession(email, password);
                String token = Utils.generateToken();
                tokensMap.put(token, session);

                return initialResponseEntityToken(token);
            } else {
                throw new UserIsNotExistException(String.format("User with email %s is not exist.", email));
            }

        } catch (JSONException | InvalidLoginException | UserIsNotExistException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Token(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Token("Internal server error"));
        }
    }

    @DecryptRequestBody
    @PostMapping("/auth")
    public ResponseEntity<Token> authorizeUser(@RequestBody String requestBody) throws JSONException {
        JSONObject data = new JSONObject(requestBody);

        for (String token : tokensMap.keySet()) {
            if (token.equals(data.getString("token"))) {
                return initialResponseEntityToken(token);
            }
        }

        throw new InvalidTokenException("Your token is unidentified. Try to login again.");
    }

    private ResponseEntity<Token> initialResponseEntityToken(String token) {
        Token myToken = new Token(token);
        myToken.setToken(security.encryptData(token));
        return ResponseEntity.ok(myToken);
    }
}
