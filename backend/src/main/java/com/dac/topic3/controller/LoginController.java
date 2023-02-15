package com.dac.topic3.controller;

import com.dac.topic3.entity.User;
import com.dac.topic3.jwtutil.JwtTokenUtil;
import com.dac.topic3.model.AuthRequest;
import com.dac.topic3.model.AuthResponse;
import com.dac.topic3.repository.UserRepository;
import io.jsonwebtoken.Claims;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class LoginController {
    @Autowired
    AuthenticationManager authManager;
    @Autowired
    JwtTokenUtil jwtUtil;
    @Autowired
    UserRepository userrepo;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request){
        try{
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getName(),request.getPassword())
            );
            User user = (User) authentication.getPrincipal();
            String accessToken = jwtUtil.generateAccessToken(user);
            AuthResponse response = new AuthResponse(accessToken);
            return ResponseEntity.ok(response);
        }catch (BadCredentialsException ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/infor")
    @RolesAllowed({"ROLE_ADMIN","ROLE_CUSTOMER","ROLE_USER"})
    public JSONObject getInfor(@RequestHeader("Authorization") String authorToken){
        String token = authorToken.substring(7);
        Claims claims = jwtUtil.parseClaims(token);
        String roles = (String) claims.get("roles");
        if(roles.equals("ROLE_ADMIN")){
            JSONObject user = new JSONObject();
            user.put("avatar" , "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
            user.put("introduction","im ...");
            user.put("name","Quang");
            JSONArray arrayRule = new JSONArray();
            arrayRule.add("admin");
            user.put("roles",arrayRule);
            return user;
        }else{
            JSONObject user = new JSONObject();
            user.put("avatar" , "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
            user.put("introduction","im ...");
            user.put("name","Quang");
            JSONArray arrayRule = new JSONArray();
            arrayRule.add("editor");
            user.put("roles",arrayRule);
            return user;
        }
    }

    @PostMapping("/logout")
    public JSONObject logout(){
        System.out.println("have been log out!");
        JSONObject temp = new JSONObject();
        temp.put("code", 2000);
        temp.put("data", "success");
        return temp;
    }

}
