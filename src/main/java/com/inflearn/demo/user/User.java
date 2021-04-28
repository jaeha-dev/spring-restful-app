package com.inflearn.demo.user;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
// @JsonIgnoreProperties(value = {"password"}) // JSON 응답 결과에서 노출하지 않도록 한다.
// @JsonFilter("UserDetails")
@ApiModel(description = "사용자 상세 정보를 위한 도메인 객체")
public class User {
    private Integer id;

    @ApiModelProperty(notes = "사용자 이름을 입력해주세요.")
    @Size(min = 2, max = 10, message = "2~10자 이내로 입력하세요.")
    private String name;

    @ApiModelProperty(notes = "사용자 비밀번호 입력해주세요.")
    @Size(min = 2, max = 10, message = "2~10자 이내로 입력하세요.")
    // @JsonIgnore // JSON 응답 결과에서 노출하지 않도록 한다.
    private String password;

    @ApiModelProperty(notes = "사용자 등록일을 입력해주세요.")
    @Past(message = "과거 날짜만 사용할 수 있습니다.") // 과거 날짜만 가능하도록 제약한다.
    private Date createdAt;
}