package cn.com.wenjin.miaosha.redis;

/**
 * @program: mymiaosha
 * @description:
 * @author: wen jin
 * @create: 2019-07-25 14:21
 */
public class OrderKey extends BasePredix {

    public OrderKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
}