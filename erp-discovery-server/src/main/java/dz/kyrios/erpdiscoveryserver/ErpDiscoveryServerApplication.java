package dz.kyrios.erpdiscoveryserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ErpDiscoveryServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ErpDiscoveryServerApplication.class, args);
    }
}
