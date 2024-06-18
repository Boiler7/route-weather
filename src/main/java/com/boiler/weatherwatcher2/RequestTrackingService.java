package com.boiler.weatherwatcher2;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class RequestTrackingService {
    private static final int MAX_REQUESTS_PER_DAY = 5;
    private final ConcurrentHashMap<String, AtomicInteger> requestCounts = new ConcurrentHashMap<>();

    public boolean isRequestAllowed(String ipAddress) {
        requestCounts.putIfAbsent(ipAddress, new AtomicInteger(0));
        return requestCounts.get(ipAddress).incrementAndGet() <= MAX_REQUESTS_PER_DAY;
    }

    @Scheduled(cron = "0 0 0 * * ?") // Reset counts at midnight every day
    public void resetRequestCounts() {
        requestCounts.clear();
    }

}
