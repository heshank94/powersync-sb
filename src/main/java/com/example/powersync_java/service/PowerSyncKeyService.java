package com.example.powersync_java.service;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Service
public class PowerSyncKeyService {

    private final RSAKey rsaJwk;

    public PowerSyncKeyService(
            @Value("${powersync.private-key-pem}") Resource privatePemRes,
            @Value("${powersync.public-key-pem}") Resource publicPemRes,
            @Value("${powersync.jwks-kid}") String kid
    ) {
        try {
            String privatePem = readResource(privatePemRes);
            String publicPem = readResource(publicPemRes);

            RSAPrivateKey privateKey = loadPrivateKey(privatePem);
            RSAPublicKey publicKey = loadPublicKey(publicPem);

            this.rsaJwk = new RSAKey.Builder(publicKey)
                    .privateKey(privateKey)
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

    private String readResource(Resource res) throws Exception {
        try (InputStream is = res.getInputStream()) {
            return new String(is.readAllBytes(), StandardCharsets.UTF_8).trim();
        }
    }

    private RSAPrivateKey loadPrivateKey(String pem) throws Exception {
        String clean = pem
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s+", "");

        byte[] decoded = Base64.getDecoder().decode(clean);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return (RSAPrivateKey) kf.generatePrivate(spec);
    }

    private RSAPublicKey loadPublicKey(String pem) throws Exception {
        String clean = pem
                .replaceAll("-----BEGIN (RSA )?PUBLIC KEY-----", "")
                .replaceAll("-----END (RSA )?PUBLIC KEY-----", "")
                .replaceAll("\\s+", "");

        byte[] decoded = Base64.getDecoder().decode(clean);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return (RSAPublicKey) kf.generatePublic(spec);
    }
}
