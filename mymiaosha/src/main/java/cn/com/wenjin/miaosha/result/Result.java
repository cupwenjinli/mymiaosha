package cn.com.wenjin.miaosha.result;

/**
 * @program: mymiaosha
 * @description:
 * @author: wen jin
 * @create: 2019-07-24 14:48
 */
public class Result<T> {
    private int code;
    private String msg;
    private T data;

    private Result(T data) {
        CodeMsg cm = CodeMsg.SUCCESS;
        this.code = cm.getCode();
        this.msg = cm.getMsg();
        this.data = data;
    }

    private Result(CodeMsg cm) {
        //在这里需要记得增加一个判断，
        if (cm == null) {
            return;
        }
        this.code = cm.getCode();
        this.msg = cm.getMsg();
    }

    public static <T> Result<T> success(T data) {
        /**
         *@Description: 服务器请求成功的方法
         *@Param: [data]
         *@return: cn.com.wenjin.miaosha.result.Result<T>
         *@Author: wen jin
         *@date: 2019/7/24
         */
        return new Result<T>(data);
    }

    public static <T> Result<T> error(CodeMsg cm) {
        return new Result<T>(cm);
    }

    public int getCode() {
        return code;
    }


    public String getMsg() {
        return msg;
    }


    public T getData() {
        return data;
    }


}