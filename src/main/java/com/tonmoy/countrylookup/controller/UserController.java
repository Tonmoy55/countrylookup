package com.tonmoy.countrylookup.controller;

import com.tonmoy.countrylookup.entity.User;
import com.tonmoy.countrylookup.service.RequestLimit;
import com.tonmoy.countrylookup.service.UserService;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/userController")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RequestLimit requestLimit;
    //private final Bucket bucket;

    /**
     * Set bucket limit for registerNewUser API
     */
    Bucket bucketRegisterNewUser = Bucket4j.builder()
            .addLimit(requestLimit.getBandwidth())
            .build();

    /**
     * Set bucket limit for forAdmin API
     */
    Bucket bucketForAdmin = Bucket4j.builder()
            .addLimit(requestLimit.getBandwidth())
            .build();

    /**
     * Set bucket limit for forUser API
     */
    Bucket bucketForUser = Bucket4j.builder()
            .addLimit(requestLimit.getBandwidth())
            .build();

    @PostConstruct
    public void initializeRoleAndUser() {
        userService.initializeRoleAndUser();
    }

    //@PostMapping({"/registerNewUser"} )
    @RequestMapping(value = "/registerNewUser", method = RequestMethod.POST)
    public ResponseEntity registerNewUser(@RequestBody User user) {

        if (bucketRegisterNewUser.tryConsume(1)) {
            return ResponseEntity.ok(userService.registerNewUser(user));
        }
        return ResponseEntity.ok(HttpStatus.TOO_MANY_REQUESTS);
    }

    //@GetMapping({"/forAdmin"})
    @RequestMapping(value = "/forAdmin", method = RequestMethod.GET)
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity forAdmin() {

        if (bucketForAdmin.tryConsume(1)) {
            return ResponseEntity.ok("This URL is accessible for Admin user only");
        }
        return ResponseEntity.ok(HttpStatus.TOO_MANY_REQUESTS);
    }

    // @GetMapping({"/forUser"})
    // @PreAuthorize("hasRole('User')") // For only one role for one user
    @RequestMapping(value = "/forUser", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('Admin','User')") // For multiple  role for one user
    public ResponseEntity forUser() {

        if (bucketForUser.tryConsume(1)) {
            return ResponseEntity.ok("This URL is accessible for User");
        }
        return ResponseEntity.ok(HttpStatus.TOO_MANY_REQUESTS);

    }
}
