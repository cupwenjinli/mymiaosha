package cn.com.wenjin.miaosha.result;

/**
 * @program: mymiaosha
 * @description:
 * @author: wen jin
 * @create: 2019-07-24 15:09
 */
public class CodeMsg {
    private int code;
    private String msg;
    public static CodeMsg SUCCESS = new CodeMsg(0,"success");
    public static CodeMsg SERVER_ERROR =  new CodeMsg(500100,"服务器状态异常");

    public CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }


    public String getMsg() {
        return msg;
    }

}