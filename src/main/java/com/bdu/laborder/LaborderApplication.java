package com.bdu.laborder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
// 打开缓存功能
@EnableCaching
public class LaborderApplication {

    public static void main(String[] args) {
        SpringApplication.run(LaborderApplication.class, args);
    }

}
