package cn.com.wenjin.miaosha.redis;

/**
 * @program: mymiaosha
 * @description:
 * @author: wen jin
 * @create: 2019-07-25 14:20
 */
public class UserKey extends BasePredix {

    private UserKey(String prefix) {
        super( prefix);
    }

    public static UserKey getById = new UserKey("id");
    public static UserKey getByName = new UserKey("name");
}