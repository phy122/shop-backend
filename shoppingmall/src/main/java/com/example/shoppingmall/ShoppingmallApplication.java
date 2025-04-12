package com.example.shoppingmall;

import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class ShoppingmallApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoppingmallApplication.class, args);
	}

}

@RestController
class Helloworld {
        @GetMapping("/")
        public String greet() {
                return "Hello!";
        }
}
