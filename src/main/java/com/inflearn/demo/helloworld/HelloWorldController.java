package com.inflearn.demo.helloworld;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping(path = "/hello-world")
    public String helloWorld() {
        return "Hello, World!";
    }

    @GetMapping(path = "/hello-world-dto")
    public HelloWorldDto helloWorldDto() {
        // @RestController를 사용하므로,
        // ResponseEntity 클래스를 이용하지 않아도 자동으로 JSON 포맷으로 변환된다.
        return new HelloWorldDto("Hello, World!");
    }

    @GetMapping(path = "/hello-world-dto/{name}")
    public HelloWorldDto helloWorldDto(@PathVariable String name) {
        return new HelloWorldDto(String.format("Hello, World!, %s", name));
    }
}