package io.kip.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.kip.model.Transaction;
import io.kip.model.WebhookPayload;
import io.kip.service.WebhookService;


@RestController
@RequestMapping("/webhooks")
public class WebhookController{

    private final WebhookService webhookService;

    public WebhookController(WebhookService webhookService){
        this.webhookService = webhookService;
    }

    @PostMapping("/mockbank")
    public Transaction receiveWebhook(@RequestBody WebhookPayload webhookPayload){
        return webhookService.processWebhook(webhookPayload);
    }   

}

