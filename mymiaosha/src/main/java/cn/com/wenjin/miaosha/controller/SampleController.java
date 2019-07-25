package cn.com.wenjin.miaosha.controller;


import cn.com.wenjin.miaosha.domain.User;
import cn.com.wenjin.miaosha.redis.RedisService;
import cn.com.wenjin.miaosha.redis.UserKey;
import cn.com.wenjin.miaosha.result.CodeMsg;
import cn.com.wenjin.miaosha.result.Result;
import cn.com.wenjin.miaosha.services.UserService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: mymiaosha
 * @description:
 * @author: wen jin
 * @create: 2019-07-24 09:46
 */
@Controller
@RequestMapping("/demo")
public class SampleController {
    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;


    @RequestMapping("/thymeleaf")
    public String thymeleaf(Model model) {
        model.addAttribute("name", "wenjin");
        return "hello";
    }

    //1.rest风格API json的输出方法 2，页面
    @RequestMapping("/json")
    @ResponseBody
    public Result<String> test() {
        return Result.success("myfirstjsondemo");
    }

    @RequestMapping("/jsonerror")
    @ResponseBody
    public Result<String> error() {
        return Result.error(CodeMsg.SERVER_ERROR);
    }

    @RequestMapping("/doget")
    @ResponseBody
    public Result<User> doGet() {
        /**
         *@Description:获取数据库user对象的测试
         *@Param: []
         *@return: cn.com.wenjin.miaosha.result.Result<cn.com.wenjin.miaosha.domain.User>
         *@Author: wen jin
         *@date: 2019/7/24
         */
        User user = userService.getByid(1);
        return Result.success(user);
    }

    @RequestMapping("/dbTx")
    @ResponseBody
    public Result<Boolean> dbTx() {
        /**
         *@Description:事务部分的测试工作
         *@Param: []
         *@return: cn.com.wenjin.miaosha.result.Result<java.lang.Boolean>
         *@Author: wen jin
         *@date: 2019/7/24
         */
        userService.tx();
        return Result.success(true);
    }
    @RequestMapping("/redis/get")
    @ResponseBody
    public Result<User> redisGet() {
        User u1 = redisService.get(UserKey.getById,""+1, User.class);

        return Result.success(u1);
    }

    @RequestMapping("/redis/set")
    @ResponseBody
    public Result<Boolean> redisSet() {
        User user = new User();
        user.setId(1);
        user.setName("wemkom");
        Boolean v1 = redisService.set(UserKey.getById,""+1, user);

        return Result.success(v1);
    }



}