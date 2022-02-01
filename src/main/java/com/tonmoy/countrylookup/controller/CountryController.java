package com.tonmoy.countrylookup.controller;

import com.tonmoy.countrylookup.entity.Country;
import com.tonmoy.countrylookup.service.CountryService;
import com.tonmoy.countrylookup.service.RequestLimit;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.Duration;

@RestController
@RequestMapping("/countryController")
public class CountryController {
    @Autowired
    private CountryService countryService;

    @Autowired
    private RequestLimit requestLimit;
    //private final Bucket bucket;

    /**
     * Set bucket limit for getCountryInformation API
     */
    Bucket bucketGetCountryInformation = Bucket4j.builder()
            .addLimit(requestLimit.getBandwidth())
            .build();

    @RequestMapping(value = "/getCountryInformation/{countryName}", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('Admin','User')")
    public ResponseEntity getCountryInformation(@PathVariable("countryName") String countryName) throws JSONException, IOException, NoSuchFieldException {
        if (bucketGetCountryInformation.tryConsume(1)) {
            return ResponseEntity.ok(countryService.getCountryInformation(countryName));
        }
        return ResponseEntity.ok(HttpStatus.TOO_MANY_REQUESTS);
    }

}
