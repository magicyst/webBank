package xyz.magiclu.bank.util;

import java.io.*;

/**
 * @version bank1.6
 * @author yst
 * 序列化工具类
 * Created by Administrator on 2018/7/24.
 * 把一个对象序列化为byte[]类型，和把一个byte[]类型的对象反序列化
 */
public class SerializeUtil {

    /**
     * object对象序列化工具
     * @param obj 需要序列化的对象
     * @return 返回obj对象序列化后的字节数组
     */
    public static byte[] serialize(Object obj){

        ByteArrayOutputStream baos = new ByteArrayOutputStream();//返回字节数组的输出流

        try {

            //对象流
            ObjectOutputStream oos = new ObjectOutputStream(baos);

            //把对象写入对象流里面
            oos.writeObject(obj);

            //如果对象写入过程无异常，把对象流用过滤流返回byte[]对象字节数组
            return baos.toByteArray();

        } catch (IOException e) {

            e.printStackTrace();
        }

        //异常返回null
        return null;

    }


    /**
     * 接受byte[]返回反序列化对象
     * @param bytes 需要反序列化的字节数组
     * @return 返回反序列化对象 Object类型
     */
    public static Object unSerialize(byte[] bytes){

        //把字节数组注入字节数组流缓冲区
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        try {

            //把缓存区放入对象流中读入内存
            ObjectInputStream ois = new ObjectInputStream(bais);

            //返回反序列化对象
            return ois.readObject();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //如果异常返回null
        return  null;
    }


}
