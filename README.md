## 记录

### 踩坑记录
1. springcloud引入nacos步骤： 需要先在子模块引入 spring-cloud-alibaba-dependencies 再去子模块引入对应的 spring-cloud-starter-alibaba-nacos-discovery 和 spring-cloud-starter-alibaba-nacos-config
2. 配置集群前缀是discovery，注意不要误用config，全称：spring.cloud.nacos.discovery.cluster-name

### 总结
- Nacos的服务实例分为两种类型：
  - 临时实例：如果实例宕机超过一定时间，会从服务列表剔除，默认的类型。
  - 非临时实例：如果实例宕机，不会从服务列表剔除，也可以叫永久实例。
  
- Nacos与eureka的共同点
    - 都支持服务注册和服务拉取
    - 都支持服务提供者心跳方式做健康检测

- Nacos与Eureka的区别
    - Nacos支持服务端主动检测提供者状态：临时实例采用心跳模式，非临时实例采用主动检测模式
    - 临时实例心跳不正常会被剔除，非临时实例则不会被剔除
    - Nacos支持服务列表变更的消息推送模式，服务列表更新更及时
    - Nacos集群默认采用AP方式，当集群中存在非临时实例时，采用CP模式；Eureka采用AP方式