package cn.com.wenjin.miaosha.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @program: mymiaosha
 * @description: redis的实现
 * @author: wen jin
 * @create: 2019-07-24 20:50
 */
@Service
public class RedisService {
    /**
     *
     * 得到jedis的方法。首先利用配置得到jedispool
     * 然后利用jedispool得到jedis的对象
     */

    @Autowired
    JedisPool jedisPool;


    /**
     * 获取单个对象
     * @param predix
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T get(KeyPredix predix, String key,Class<T> clazz){
        Jedis jedis = null;
        //无论是什么连接池，用了之后都需要释放掉
        try{
            jedis = jedisPool.getResource();

            //这里要通过真正的key去获取
            String realKey = predix.getPrefix()+key;
            String str = jedis.get(realKey);

            T t = stringToBean(str,clazz);
            return t;
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 将key中存储的数字值加1，原子操作
     * @param predix
     * @param key
     * @param <T>
     * @return
     */
    public <T> Long inc(KeyPredix predix, String key){
        Jedis jedis = null;
        //无论是什么连接池，用了之后都需要释放掉
        try{
            jedis = jedisPool.getResource();

            //这里要通过真正的key去获取
            String realKey = predix.getPrefix()+key;

            return jedis.incr(realKey);
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 将key中存储的数字值减1，原子操作
     * @param predix
     * @param key
     * @param <T>
     * @return
     */
    public <T> Long decr(KeyPredix predix, String key){
        Jedis jedis = null;
        //无论是什么连接池，用了之后都需要释放掉
        try{
            jedis = jedisPool.getResource();

            //这里要通过真正的key去获取
            String realKey = predix.getPrefix()+key;

            return jedis.decr(realKey);
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 通过前缀可key将对象存储到redis缓存中
     * @param predix
     * @param key
     * @param t
     * @param <T>
     * @return
     */
    public <T> boolean set(KeyPredix predix,String key,T t){
        Jedis jedis = null;
        //无论是什么连接池，用了之后都需要释放掉
        try{
            jedis = jedisPool.getResource();
            String value = beanToString(t);
            if(value==null||value.length()<=0){
                return false;
            }
            //生成真正的key去进行存放，在存放的时候还需要看有效期
            int second = predix.expireSeconds();
            String realKey = predix.getPrefix()+key;
            if(second<=0){
                //永久的，那么直接存放就可
                jedis.set(realKey,value);
            }else {
                //如果是有有效期的，存放的过程中是需要加上有效期这个变量的
                jedis.setex(realKey,second,value);
            }

            return true;
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 判断redis中是否存在key对象
     * @param predix
     * @param key
     * @param t
     * @param <T>
     * @return
     */
    public <T> boolean exist(KeyPredix predix,String key,T t){
        Jedis jedis = null;
        //无论是什么连接池，用了之后都需要释放掉
        try{
            jedis = jedisPool.getResource();

            //生成真正的key去进行存放
            String realKey = predix.getPrefix()+key;
            return jedis.exists(realKey);

        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 提供了bean对象到String类型的转换，利用fashJSON去进行实现
     * @param t
     * @param <T>
     * @return
     */
    private <T> String beanToString(T t) {
        //在进行return的时候需要进行参数校验，主要的擦书校验过程如下

        if(t==null){
            return null;

        }
        Class<?> clazz = t.getClass();
        if(clazz==int.class||clazz==Integer.class){
            return ""+t;
        }else if(clazz == long.class ||clazz==Long.class){
            return ""+t;

        }else if(clazz ==String.class){
            return  (String) t;
        }
        else {
            return JSON.toJSONString(t);
        }

    }

    /**
     * 提供了String类型到bean对象的一个转化，利用FashJOSN实现
     * @param str
     * @param clazz
     * @param <T>
     * @return
     */
    private <T> T stringToBean(String str,Class<T> clazz) {
        //首先进行参数校验
        if(str==null||str.length()<=0||clazz==null){
            return null;
        }
        if(clazz==int.class||clazz==Integer.class){
            return (T)Integer.valueOf(str);
        }else if(clazz == long.class ||clazz==Long.class){
            return (T)Long.valueOf(str);

        }else if(clazz ==String.class){
            return  (T) str;
        }
        else {
            return JSON.toJavaObject(JSON.parseObject(str),clazz);
        }
        //fastjson,将字符串转化为bean对象

    }

    /**
     * 将jedis链接返回到连接池中
     * @param jedis
     */
    private void returnToPool(Jedis jedis){
        if(jedis!=null){
           jedis.close();

        }
    }

}