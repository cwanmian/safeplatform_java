package com.ccwme.bugmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class BugmanagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BugmanagerApplication.class, args);
    }

}
