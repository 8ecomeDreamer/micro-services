## 记录

### 踩坑记录
1. springCloud引入nacos步骤： 需要先在子模块引入 spring-cloud-alibaba-dependencies 再去子模块引入对应的 spring-cloud-starter-alibaba-nacos-discovery 和 spring-cloud-starter-alibaba-nacos-config。
2. 配置集群前缀是discovery，注意不要误用config，全称：spring.cloud.nacos.discovery.cluster-name。
3. 配置nacos集群的时候application.properties中定义的port需要对应上cluster.conf的内容。集群启动命令: startup.cmd -m cluster。
4. 在Nacos2.x版本中，除了主端口（如 8845），还需要额外的 gRPC 端口配置集群, gRPC SDK 端口 = 主端口 + 1000（用于客户端通信）, gRPC Cluster 端口 = 主端口 + 1001（用于集群内部通信）。如nacos1: 8845 (HTTP) + 9845 (gRPC SDK) + 9846 (gRPC Cluster)，nacos2: 8846 (HTTP) + 9846 (gRPC SDK) + 9847 (gRPC Cluster)。所以配置端口号需要隔开几个，避免gRPC端口号冲突。
5. 引入feign时会因为找不到nacos负载均衡组件ribbon报错，解决办法1：feignClient使用本地地址而不使用线上地址，这属于临时解决办法。解决办法2：由于SpringBoot是2.3.9.RELEASE，而openfeign是2.2.7.RELEASE，属于 Spring Cloud Hoxton 2.2.x 系列（老版本），所以需要引入spring-cloud-starter-netflix-ribbon依赖。
6. springcloud与springcloudAlibaba分别有不同的组件，在引入这两个依赖时要注意版本问题，否则后面引入对应的组件使用会报错。

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