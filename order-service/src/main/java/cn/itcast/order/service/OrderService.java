package cn.itcast.order.service;

import cn.itcast.order.mapper.OrderMapper;
import cn.itcast.order.pojo.Order;
import cn.itcast.order.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

//    @Resource
//    private RestTemplate restTemplate;

    @Resource
    private UserClient userClient;

    public Order queryOrderById(Long orderId) {
        // 1.查询订单
        Order order = orderMapper.findById(orderId);
        // 2.远程查询user
//        // 2.1 url地址
//        String url = "http://localhost:8081/user/" + order.getUserId();
////        String url = "http://user-service/user/" + order.getUserId();
//        // 2.2 发起调用
//        User user = restTemplate.getForObject(url, User.class);

        //        2.利用feign发起http请求，查询用户
        User user = userClient.findById(order.getUserId());

        // 3. 存入order表
        order.setUser(user);

        // 4.返回
        return order;
    }
}
