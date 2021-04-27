package com.inflearn.demo.helloworld;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // @Getter, @Setter, @RequiredArgsConstructor, @ToString, @EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class HelloWorldDto {
    // IntelliJ 좌측 메뉴에서 Structure 탭을 선택하면 해당 클래스의 구조를 확인할 수 있다.
    private String message;
}