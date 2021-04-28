package com.inflearn.demo.user;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminUserController {
    private final UserDaoService userDaoService;

    public AdminUserController(UserDaoService userDaoService) {
        this.userDaoService = userDaoService;
    }

    // @GetMapping(path = "/v1/admin/users/{id}") // URI를 사용한 API 버전 관리
    // @GetMapping(value = "/admin/users/{id}/", params = "version=1") // Request Parameter를 사용한 버전 관리 (/admin/users/1/?version=1)
    // @GetMapping(value = "/admin/users/{id}", headers = "X-API-VERSION=1") // HEADER를 사용한 API 버전 관리(헤더 값은 임의 지정)
    @GetMapping(value = "/admin/users/{id}", produces = "application/v1+json")  // MIME 타입을 사용한 API 버전 관리
    public MappingJacksonValue retrieveUserV1(@PathVariable Integer id) {
        User user = userDaoService.findById(id);

        if (user == null) {
            throw new UserNotFoundException(String.format("User ID[%s] not found", id));
        }

        // User 도메인에서 4개의 필드를 필터링한다.
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(
                "id", "name", "password", "createdAt"
        );
        // User 도메인에서 지정한 필터 이름을 ID 값으로 지정한다.
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserDetails", filter);
        MappingJacksonValue mapping = new MappingJacksonValue(user);
        mapping.setFilters(filters);

        return mapping;
    }

    // @GetMapping(path = "/v2/admin/users/{id}") // URI를 사용한 API 버전 관리
    // @GetMapping(value = "/admin/users/{id}/", params = "version=2") // Request Parameter를 사용한 버전 관리 (/admin/users/1/?version=2)
    // @GetMapping(value = "/admin/users/{id}", headers = "X-API-VERSION=2") // HEADER를 사용한 API 버전 관리(헤더 값은 임의 지정)
    @GetMapping(value = "/admin/users/{id}", produces = "application/v2+json") // MIME 타입을 사용한 API 버전 관리
    public MappingJacksonValue retrieveUserV2(@PathVariable Integer id) {
        User user = userDaoService.findById(id);

        if (user == null) {
            throw new UserNotFoundException(String.format("User ID[%s] not found", id));
        }

        UserV2 userV2 = new UserV2();
        BeanUtils.copyProperties(user, userV2);
        userV2.setGrade("VIP");

        // User 도메인에서 4개의 필드와 UserV2 도메인의 1개의 필드를 필터링한다.
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(
                "id", "name", "password", "grade", "createdAt"
        );
        // UserV2 도메인에서 지정한 필터 이름을 ID 값으로 지정한다.
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserDetailsV2", filter);
        MappingJacksonValue mapping = new MappingJacksonValue(userV2);
        mapping.setFilters(filters);

        return mapping;
    }
}