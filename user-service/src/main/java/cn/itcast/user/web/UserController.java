package cn.itcast.user.web;

import cn.itcast.user.pojo.NacosPatternConfigProperties;
import cn.itcast.user.pojo.User;
import cn.itcast.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RestController
@RequestMapping("/user")
@RefreshScope
public class UserController {

    @Resource
    private UserService userService;

//    @Value("${pattern.dateFormat}")
//    private String dateFormat;

    @Resource
    private NacosPatternConfigProperties nacosPatternConfigProperties;

    /**
     * 路径： /user/110
     *
     * @param id 用户id
     * @return 用户
     */
    @GetMapping("/{id}")
    public User queryById(@PathVariable("id") Long id) {
        return userService.queryById(id);
    }


    @GetMapping("now")
    public String now(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(nacosPatternConfigProperties.getDateformat()));
    }

    @GetMapping("prop")
    public NacosPatternConfigProperties prop(){
        return nacosPatternConfigProperties;
    }
}
