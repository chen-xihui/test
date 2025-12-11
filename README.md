# Spring Cloud Eureka 微服务Demo工程

这是一个基于Spring Cloud和Eureka服务注册中心的完整微服务架构示例。

## 项目结构

```
eureka-demo/
├── eureka-server/          # Eureka服务注册中心
├── gateway-service/        # Spring Cloud Gateway网关服务
├── user-service/           # 用户微服务
└── order-service/          # 订单微服务（含Feign调用）
```

## 技术栈

- Spring Boot 2.7.15
- Spring Cloud 2021.0.8
- Netflix Eureka（服务注册与发现）
- Spring Cloud Gateway（API网关）
- Spring Cloud OpenFeign（服务调用）

## 服务说明

### 1. Eureka Server
- **端口**: 8761
- **功能**: 服务注册中心，管理所有微服务
- **访问地址**: http://localhost:8761

### 2. User Service
- **端口**: 8001
- **功能**: 用户服务，提供用户信息查询
- **API端点**:
  - GET /user/{id} - 获取指定用户信息
  - GET /user/list - 获取所有用户列表
  - GET /info - 获取服务信息

### 3. Order Service
- **端口**: 8002
- **功能**: 订单服务，提供订单管理和服务调用
- **API端点**:
  - GET /order/{id} - 获取指定订单信息（含用户详情）
  - GET /order/list - 获取所有订单列表
  - GET /info - 获取服务信息
  - GET /test-service-call - 测试服务间调用

### 4. Gateway Service
- **端口**: 9000
- **功能**: API网关，路由请求到各微服务
- **路由规则**:
  - /user/** → user-service
  - /order/** → order-service

## 启动顺序

1. 首先启动 Eureka Server
2. 然后启动 User Service
3. 再启动 Order Service
4. 最后启动 Gateway Service

## 运行方法

使用Maven命令启动各服务：

```bash
# 启动Eureka Server
cd eureka-server
mvn spring-boot:run

# 启动User Service
cd ../user-service
mvn spring-boot:run

# 启动Order Service
cd ../order-service
mvn spring-boot:run

# 启动Gateway Service
cd ../gateway-service
mvn spring-boot:run
```

## 测试方法

1. 访问Eureka管理界面：http://localhost:8761
2. 查看服务是否成功注册
3. 通过网关测试API：
   - 获取用户信息：http://localhost:9000/user/1
   - 获取订单信息：http://localhost:9000/order/1
   - 测试服务调用：http://localhost:9000/test-service-call

## 重要特性

1. **服务注册与发现**: 所有微服务自动注册到Eureka Server
2. **负载均衡**: 通过Ribbon实现客户端负载均衡
3. **声明式服务调用**: 使用Feign简化服务间通信
4. **统一网关**: 通过Gateway提供统一的API入口
5. **高可用**: 支持服务集群部署

## 注意事项

1. 确保JDK 1.8或更高版本
2. 确保Maven 3.6+环境
3. 各服务间通过服务名而非固定IP进行通信
4. 服务下线后会自动从注册中心移除