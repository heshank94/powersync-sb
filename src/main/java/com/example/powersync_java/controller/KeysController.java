package com.example.powersync_java.controller;


import com.example.powersync_java.service.PowerSyncKeyService;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class KeysController {
    private final PowerSyncKeyService keyService;

    public KeysController(PowerSyncKeyService keyService) {
        this.keyService = keyService;
    }

    @GetMapping(value = "/get_keys/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getKeys() {
        System.out.println("Calling get_keys api");
        RSAKey rsaKey = keyService.getRsaJwk();

        Map<String, Object> key = new LinkedHashMap<>();
        key.put("alg", "RS256");
        key.put("kty", rsaKey.getKeyType().getValue());
        key.put("n", rsaKey.getModulus().toString());
        key.put("e", rsaKey.getPublicExponent().toString());
        key.put("kid", rsaKey.getKeyID());

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("keys", List.of(key));

        return response;
    }
}
