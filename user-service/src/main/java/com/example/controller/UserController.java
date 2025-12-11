package com.example.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    @Value("${server.port}")
    private String port;

    @Value("${spring.application.name}")
    private String serviceName;

    // 模拟用户数据
    private static final Map<Integer, String> userMap = new HashMap<>();

    static {
        userMap.put(1, "张三");
        userMap.put(2, "李四");
        userMap.put(3, "王五");
        userMap.put(4, "赵六");
        userMap.put(5, "钱七");
    }

    @GetMapping("/user/{id}")
    public Map<String, Object> getUser(@PathVariable Integer id) {
        Map<String, Object> result = new HashMap<>();
        String username = userMap.getOrDefault(id, "用户不存在");
        result.put("id", id);
        result.put("username", username);
        result.put("from", serviceName + " (端口: " + port + ")");
        return result;
    }

    @GetMapping("/user/list")
    public Map<String, Object> getUserList() {
        Map<String, Object> result = new HashMap<>();
        result.put("users", userMap);
        result.put("from", serviceName + " (端口: " + port + ")");
        return result;
    }

    @GetMapping("/info")
    public Map<String, String> getServiceInfo() {
        Map<String, String> info = new HashMap<>();
        info.put("serviceName", serviceName);
        info.put("port", port);
        info.put("description", "用户服务，提供用户信息查询功能");
        return info;
    }
}