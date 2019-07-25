package cn.com.wenjin.miaosha;

import redis.clients.jedis.Jedis;

/**
 * @program: mymiaosha
 * @description:
 * @author: wen jin
 * @create: 2019-07-25 11:27
 */


    public class test {
    public static void main(String[] args) {
        Jedis edis = new Jedis("47.94.3.37", 6379);
        edis.auth("cupliwenjin123");
        System.out.println(edis.ping());
    }
}
