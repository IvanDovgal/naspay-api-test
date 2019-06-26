package text.naspay.client.impl;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;

public class SetupTest {

    private static WireMockServer mockServer;

    public static String getApiUrl() {
        return mockServer.baseUrl();
    }

    @BeforeAll
    public static void init() {
        mockServer = new WireMockServer(
                WireMockConfiguration
                        .wireMockConfig()
                        .port(9000)
        );
        mockServer.start();
        configureFor("localhost", mockServer.port());
    }

    @BeforeEach
    public void reset() {
        mockServer.resetAll();
    }

    @AfterAll
    public static void stop() {
        mockServer.stop();
    }
}
