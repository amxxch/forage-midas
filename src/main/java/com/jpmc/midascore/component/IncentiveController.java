package com.jpmc.midascore.component;

import com.jpmc.midascore.foundation.Incentive;
import com.jpmc.midascore.foundation.Transaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class IncentiveController {
    private final RestTemplate restTemplate;
    private final String incentiveUrl;

    public IncentiveController(RestTemplate restTemplate, @Value("${general.incentive-api-url}") String incentiveUrl) {
        this.restTemplate = restTemplate;
        this.incentiveUrl = incentiveUrl;
    }

    public Incentive getIncentive(Transaction transaction) {
        return restTemplate.postForObject(this.incentiveUrl, transaction, Incentive.class);
    }
}
