package com.example.controller;

import com.example.client.UserServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class OrderController {

    @Autowired
    private UserServiceClient userServiceClient;

    @Value("${server.port}")
    private String port;

    @Value("${spring.application.name}")
    private String serviceName;

    // 模拟订单数据
    private static final Map<Integer, Map<String, Object>> orderMap = new HashMap<>();

    static {
        Map<String, Object> order1 = new HashMap<>();
        order1.put("orderId", 1);
        order1.put("userId", 1);
        order1.put("productName", "笔记本电脑");
        order1.put("price", 5999);
        order1.put("status", "已支付");
        orderMap.put(1, order1);

        Map<String, Object> order2 = new HashMap<>();
        order2.put("orderId", 2);
        order2.put("userId", 2);
        order2.put("productName", "智能手机");
        order2.put("price", 2999);
        order2.put("status", "待支付");
        orderMap.put(2, order2);

        Map<String, Object> order3 = new HashMap<>();
        order3.put("orderId", 3);
        order3.put("userId", 1);
        order3.put("productName", "无线耳机");
        order3.put("price", 999);
        order3.put("status", "已支付");
        orderMap.put(3, order3);
    }

    @GetMapping("/order/{id}")
    public Map<String, Object> getOrder(@PathVariable Integer id) {
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> order = orderMap.get(id);

        if (order != null) {
            // 调用用户服务获取用户信息
            Integer userId = (Integer) order.get("userId");
            Map<String, Object> userInfo = userServiceClient.getUser(userId);

            result.put("orderInfo", order);
            result.put("userInfo", userInfo);
            result.put("from", serviceName + " (端口: " + port + ")");
        } else {
            result.put("error", "订单不存在");
            result.put("from", serviceName + " (端口: " + port + ")");
        }

        return result;
    }

    @GetMapping("/order/list")
    public Map<String, Object> getOrderList() {
        Map<String, Object> result = new HashMap<>();
        result.put("orders", orderMap);
        result.put("from", serviceName + " (端口: " + port + ")");
        return result;
    }

    @GetMapping("/info")
    public Map<String, String> getServiceInfo() {
        Map<String, String> info = new HashMap<>();
        info.put("serviceName", serviceName);
        info.put("port", port);
        info.put("description", "订单服务，提供订单管理功能");
        return info;
    }

    // 测试服务调用
    @GetMapping("/test-service-call")
    public Map<String, Object> testServiceCall() {
        Map<String, Object> result = new HashMap<>();
        try {
            Map<String, String> userServiceInfo = userServiceClient.getServiceInfo();
            result.put("success", true);
            result.put("userServiceInfo", userServiceInfo);
            result.put("message", "服务调用成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "服务调用失败: " + e.getMessage());
        }
        return result;
    }
}