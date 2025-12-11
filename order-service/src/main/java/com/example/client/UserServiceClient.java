package com.example.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "user-service")  // 指定要调用的服务名称
public interface UserServiceClient {

    @GetMapping("/user/{id}")
    Map<String, Object> getUser(@PathVariable("id") Integer userId);

    @GetMapping("/info")
    Map<String, String> getServiceInfo();
}