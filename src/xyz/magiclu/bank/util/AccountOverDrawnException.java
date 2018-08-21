package xyz.magiclu.bank.util;

/**
 * Created by Administrator on 2018/6/19.
 */

/**
 * 取款异常类
 *      当取款金额为负数的时候抛异常
 */
public class AccountOverDrawnException extends Exception {


    /**
     * 默认错误信息
     */
    public AccountOverDrawnException(){

        super("取款超出余额异常");
    }

    /**
     * 错误信息注入
     * @param message 错误信息
     */
    public AccountOverDrawnException(String message){

        super(message);

    }

    /**
     * 重写父类方法
     *      获取错误信息
     *
     * @return
     */
    public String getMessage() {

        return super.getMessage();
    }

    /**
     * 重写父类方法
     *
     * @return
     */
    public String toString() {
        return super.toString();
    }

    /**
     * 重写父类方法
     */
    public void printStackTrace() {
        super.printStackTrace();
    }
}
