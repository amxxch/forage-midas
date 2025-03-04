package com.jpmc.midascore;

import com.jpmc.midascore.foundation.Incentive;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BalanceQuerier {
    private final RestTemplate restTemplate;

    public BalanceQuerier(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public Incentive query(Long userId) {
        String url = "http://localhost:33400/balance?userId=" + userId;
        return restTemplate.getForObject(url, Incentive.class);
    }
}
