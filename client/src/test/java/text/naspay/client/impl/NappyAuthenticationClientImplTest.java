package text.naspay.client.impl;

import org.junit.jupiter.api.Test;
import text.naspay.client.NaspayAuthentificationClient;
import text.naspay.client.exception.AuthentificationException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

class NappyAuthenticationClientImplTest extends SetupTest {

    @Test
    void getBearerToken() {
        stubFor(get(urlEqualTo("/auth/token")).willReturn(aResponse().withStatus(401)));
        stubFor(get(urlEqualTo("/auth/token"))
                .withHeader("Authorization", equalTo("Basic YXBpLWRlbW86dGVzdDEyMw=="))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\n" +
                                "    \"access_token\": \"466be7f8b80b3bf8bf20147e76c9ae5d\",\n" +
                                "    \"token_type\": \"bearer\"\n" +
                                "}")));
        NaspayAuthentificationClient authentificationClient = new NappyAuthenticationClientImpl(getApiUrl());
        assertNotNull(authentificationClient.getBearerToken("api-demo", "test123"));
        assertThrows(AuthentificationException.class, () -> {
            assertNotNull(authentificationClient.getBearerToken("api-fail", "test123"));
        });
    }

}