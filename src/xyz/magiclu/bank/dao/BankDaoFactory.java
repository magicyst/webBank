package xyz.magiclu.bank.dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * @version bank2.0
 * @author Administrator yst
 * 实现dao层的装配工厂
 * Created by Administrator on 2018/7/14.
 */
public class BankDaoFactory {

    private static final String configPath = BankDaoFactory.class.getResource("/")
            .getPath()+"filedata\\dao_config.properties";
    /**
     * 私有构造方法
     */
    private BankDaoFactory(){}

    /**
     * 实现dao的自动装配
     * @return 返回dao实现
     */
    public synchronized BankDaoInterface getDao(){

        BankDaoInterface daoImpl = null;
        //读取装配文件template.properties
        Properties profile = new Properties();

        FileReader in = null;
        try {
            in = new FileReader(BankDaoFactory.configPath);
            profile.load(in);

            String bankdao = profile.getProperty("daoImpl");

            daoImpl = (BankDaoInterface)Class.forName(bankdao).newInstance();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return daoImpl;

    }

    /**
     * 单例对外接口，静态内部类
     * @return 返回dao工厂单例
     */
    public static final BankDaoFactory getInstance(){

        return BankDaoFactoryHolder.bankDaoFactory_instance;
    }

    /**
     * 工厂单例持有者
     * 解决多线程环境下的单例
     */
    private static class BankDaoFactoryHolder{

        public static BankDaoFactory bankDaoFactory_instance = new BankDaoFactory();

    }

    public static void main(String[] args) {

        BankDaoFactory factory = BankDaoFactory.getInstance();

        BankDaoInterface dao = factory.getDao();

        System.out.println(dao);
    }
}
