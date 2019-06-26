package text.naspay.cli;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import text.naspay.client.NaspayApiClient;

@SpringBootApplication
public class Application {

    public static final String API_URL = "https://demo.naspay.com";

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class)
                .web(WebApplicationType.NONE)
                .run(args)
                .getBean(NaspayApiClient.class);
    }
}
