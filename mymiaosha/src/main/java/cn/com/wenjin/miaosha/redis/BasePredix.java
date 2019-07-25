package cn.com.wenjin.miaosha.redis;

/**
 * @program: mymiaosha
 * @description:
 * @author: wen jin
 * @create: 2019-07-25 12:41
 */
public class BasePredix implements KeyPredix {
    private int expireSeconds;
    private String prefix;


    public BasePredix(int expireSeconds, String prefix) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    public BasePredix(String prefix){
        this.expireSeconds = 0;
        this.prefix = prefix;
    }
    @Override
    public int expireSeconds() {
        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        //为了对不同的订单进行区分，采用类名进行区分
        String className = getClass().getSimpleName();
        return  className+":"+prefix;
    }
}