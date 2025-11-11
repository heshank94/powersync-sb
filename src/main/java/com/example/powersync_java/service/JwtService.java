package com.example.powersync_java.service;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    private final PowerSyncKeyService keyService;
    private final String issuer;
    private final String audience;

    public JwtService(
            PowerSyncKeyService keyService,
            @Value("${powersync.issuer}") String issuer,
            @Value("${powersync.instance-url}") String audience
    ) {
        this.keyService = keyService;
        this.issuer = issuer;
        this.audience = audience;
    }

    public String createJwtToken(Long userId) {
        try {
            RSAKey rsaKey = keyService.getRsaJwk();
            JWSSigner signer = new RSASSASigner(rsaKey.toPrivateKey());

            long now = System.currentTimeMillis() / 1000;

            JWTClaimsSet claims = new JWTClaimsSet.Builder()
                    .subject(String.valueOf(userId))
                    .issueTime(new Date(now * 1000))
                    .issuer(issuer)
                    .audience(audience)
                    .expirationTime(new Date((now + 3600) * 1000))
                    .build();

            SignedJWT signedJWT = new SignedJWT(
                    new JWSHeader.Builder(JWSAlgorithm.RS256)
                            .keyID(rsaKey.getKeyID())
                            .build(),
                    claims
            );

            signedJWT.sign(signer);

            return signedJWT.serialize();

        } catch (Exception e) {
            throw new RuntimeException("JWT creation failed", e);
        }
    }
}
