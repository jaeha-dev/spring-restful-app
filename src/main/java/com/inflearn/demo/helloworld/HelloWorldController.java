package com.inflearn.demo.helloworld;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import java.util.Locale;

@RestController
public class HelloWorldController {

    @Autowired
    private MessageSource messageSource;

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

    // Headers 탭에서 Accept-Language 키와 en, fr 등의 값을 입력한다.
    // 입력하지 않으면 기본 값인 한국어로 응답이 출력된다.
    @GetMapping(path = "/hello-world-i18n")
    public String helloWorldI18n(@RequestHeader(name = "Accept-Language", required = false) Locale locale) {
        return messageSource.getMessage("greeting.message", null, locale);
    }
}