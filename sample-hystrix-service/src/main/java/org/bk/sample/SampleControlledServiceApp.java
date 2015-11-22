package org.bk.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SampleControlledServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(SampleControlledServiceApp.class, args);
    }
}
