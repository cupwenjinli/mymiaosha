package cn.com.wenjin.miaosha.redis;

public interface KeyPredix {
    public int expireSeconds();
    public String getPrefix();
}
