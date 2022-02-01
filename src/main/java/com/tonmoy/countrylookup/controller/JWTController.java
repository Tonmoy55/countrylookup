package com.tonmoy.countrylookup.controller;


import com.tonmoy.countrylookup.entity.JwtRequest;
import com.tonmoy.countrylookup.entity.JwtResponse;
import com.tonmoy.countrylookup.service.JwtService;
import com.tonmoy.countrylookup.service.RequestLimit;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/jwtController")
public class JWTController {
    @Autowired
    JwtService jwtService;

    //@PostMapping({"/authenticate"})
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public JwtResponse createJWTToken(@RequestBody JwtRequest jwtRequest) throws Exception {

        return jwtService.createJwtToken(jwtRequest);
    }

}
