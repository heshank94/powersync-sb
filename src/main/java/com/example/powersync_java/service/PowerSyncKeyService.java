package com.example.powersync_java.service;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class PowerSyncKeyService {

    private final RSAKey rsaJwk;

    public PowerSyncKeyService(
            @Value("${powersync.private-key}") String privateKeyB64,
            @Value("${powersync.public-key}") String publicKeyB64,
            @Value("${powersync.jwks-kid}") String kid
    ) {
        try {
            String privateJson = new String(Base64.getDecoder().decode(privateKeyB64), StandardCharsets.UTF_8);
            String publicJson = new String(Base64.getDecoder().decode(publicKeyB64), StandardCharsets.UTF_8);


            RSAKey privateKey = RSAKey.parse(privateJson);
            RSAKey publicKey = RSAKey.parse(publicJson);

            this.rsaJwk = new RSAKey.Builder(publicKey.toRSAPublicKey())
                    .privateKey(privateKey.toRSAPrivateKey())
                    .keyID(kid)
                    .build();

        } catch (Exception e) {
            throw new RuntimeException("Failed to load RSA keys", e);
        }
    }

    public RSAKey getRsaJwk() {
        return rsaJwk;
    }

    public JWKSet getJwkSet() {
        return new JWKSet(rsaJwk.toPublicJWK());
    }


}
