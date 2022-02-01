package com.tonmoy.countrylookup.service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Refill;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RequestLimit {


    private static final Integer REQUEST_PER_MINUTE = 30;

    /**
     * This method will create the bandwidth limit for API request per minute
     * @return
     */
    public static Bandwidth getBandwidth() {
        Refill refill = Refill.intervally(REQUEST_PER_MINUTE, Duration.ofMinutes(1));
        Bandwidth limit = Bandwidth.classic(REQUEST_PER_MINUTE, refill);
        return limit;
    }
}
