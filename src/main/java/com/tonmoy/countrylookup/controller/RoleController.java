package com.tonmoy.countrylookup.controller;

import com.tonmoy.countrylookup.entity.Role;
import com.tonmoy.countrylookup.service.RequestLimit;
import com.tonmoy.countrylookup.service.RoleService;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roleController")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private RequestLimit requestLimit;
    //private final Bucket bucket;

    /**
     * Set bucket limit for createNewRole API.
     */
    Bucket bucketCreateNewRole = Bucket4j.builder()
            .addLimit(requestLimit.getBandwidth())
            .build();

    //@PostMapping({"/createNewRole"})
    @RequestMapping(value = "/createNewRole", method = RequestMethod.POST)
    public ResponseEntity createNewRole(@RequestBody Role role) {

        if (bucketCreateNewRole.tryConsume(1)) {
            return ResponseEntity.ok(roleService.createNewRole(role));
        }

        return ResponseEntity.ok(HttpStatus.TOO_MANY_REQUESTS);
    }
}
