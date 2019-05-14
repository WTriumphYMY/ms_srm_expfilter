package cn.edu.nwpu.ms_srm_expfilter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class MsSrmExpfilterApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsSrmExpfilterApplication.class, args);
    }

}
