package com.example.powersync_java.controller;

import com.example.powersync_java.service.PowerSyncKeyService;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TokenController {
    private final PowerSyncKeyService keyService;
    private static final String POWER_SYNC_INSTANCE_URL = "powersync-dev";
    @Value("${powersync.issuer}")
    private String issuer;

    public TokenController(PowerSyncKeyService keyService) {
        this.keyService = keyService;
    }

    @GetMapping("/get_powersync_token/")
    public Map<String, String> getToken(@RequestParam(value = "user_id", required = false) String userId) throws Exception {
        System.out.println("Calling get_powersync_token api");
        long now = System.currentTimeMillis() / 1000;
        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject("1")
                .issuer(issuer)
                .audience(POWER_SYNC_INSTANCE_URL)
                .issueTime(new Date(now * 1000))
                .expirationTime(new Date((now + 3600) * 1000))
                .build();

        SignedJWT signedJWT = new SignedJWT(
                new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(keyService.getRsaJwk().getKeyID()).build(),
                claims);

        RSASSASigner signer = new RSASSASigner(keyService.getRsaJwk().toPrivateKey());
        signedJWT.sign(signer);

        String token = signedJWT.serialize();
        Map<String, String> response = new LinkedHashMap<>();
        response.put("token", token);
        response.put("powersync_url", POWER_SYNC_INSTANCE_URL);
        return response;
    }
}