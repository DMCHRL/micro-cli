# micro-cli



## 系统说明

- 基于Spring Cloud Alibaba、Nacos、Spring Boot、 OAuth2 的 RBAC **微服务后端轮子** 
- 基于Mybatis多租户实现SaaS

### 核心依赖

| 依赖                   | 版本          |
| ---------------------- | ------------- |
| Spring Boot            | 2.2.6.RELEASE |
| Spring Cloud           | Hoxton.SR3    |
| Spring Cloud Alibaba   | 2.2.5.RELEASE|
| Nacos                  | 2.0.2        |
| Mybatis Plus           | 3.3.1        |
| hutool                 | 5.6.7       |

### 模块说明

```lua


micro-cli
├── micro-auth -- 授权服务提供[7000] 可自定义授权模式，代码中添加了短信授权模式
└── micro-common -- 系统公共模块
     ├── micro-core -- 公共工具类核心包
     ├── micro-data -- 数据配置包 mybatis,redis
     ├── micro-security -- 安全工具类
     ├── micro-swagger -- 接口文档
├── micro-gateway -- Spring Cloud Gateway网关[9999]
└── micro-upms -- 通用用户权限管理模块
     └── upms-admin -- 业务接口模块
     └── upms-common
└── nacos -- 服务配置
     └── server.conf --  服务配置文件


