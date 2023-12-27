package dz.kyrios.erpapigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ErpApiGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ErpApiGatewayApplication.class, args);
    }
}
