package xyz.magiclu.bank.dao;

import xyz.magiclu.bank.dao.dbstore.AbstractDBDao;
import xyz.magiclu.bank.dao.dbstore.MySqlDaoImpl;
import xyz.magiclu.bank.dao.filestore.AbstractFileDao;
import xyz.magiclu.bank.dao.filestore.FileDaoImpl;
import xyz.magiclu.bank.model.UserBean;
import xyz.magiclu.bank.util.*;

import java.io.IOException;
import java.sql.SQLException;

/**
 * 本类为dao层的适配类
 * 由于要实现
 *      1.用户名密码文件存储
 *      2.账户其他信息数据库存储
 * 在dao同一接口的情况下,完成这种需求，则通过face模式适配一下fileDao,和dbDao的功能
 * Created by Administrator on 2018/8/16.
 */
public class BankDaoImplFace implements BankDaoInterface{


    private AbstractFileDao fileDao = new FileDaoImpl(); //文件dao
    private AbstractDBDao dbDao;    //数据库dao


    /*
    适配
        mysqlDaoImpl(查询，存款，取款，转账)
        fileDaoImpl(登录，注册，注销，已经获取登录用户名)
     */

    /**
     * 1.注册
     * @param user 用户bean
     * @throws IOException 文件读取异常
     * @throws BankSystemRegisterException 注册异常
     */
    @Override
    public void register(UserBean user) throws IOException, BankSystemRegisterException, SQLException {

        fileDao.register(user);

        dbDao = new MySqlDaoImpl(user);
    }

    /**
     * 2.登录
     * @param user 用户bean
     * @throws BankSystemLoginException 登录异常
     */
    @Override
    public void login(UserBean user) throws BankSystemLoginException, SQLException {

        fileDao.login(user);

        dbDao = new MySqlDaoImpl(user);
    }

    /**
     * 7.获取登录用户名
     * @return 返回用户名
     */
    @Override
    public String getLoginUserName() {
        return fileDao.getLoginUserName();
    }

    /**
     * 3.查询余额
     * @return 返回余额 double类型
     * @throws BankSystemLoginException 当为登录查询显示登录异常
     */
    @Override
    public double inquire() throws BankSystemLoginException {
        return dbDao.inquire();
    }

    /**
     * 4.取款
     * @param money 取款金额
     * @throws AccountOverDrawnException 取款money非法异常
     * @throws BankSystemLoginException 未登录取款
     */
    @Override
    public void withdrawals(double money) throws AccountOverDrawnException, SQLException, BankSystemLoginException {

        dbDao.withdrawals(money);
    }

    /**
     * 5.存款
     * @param money 存款金额
     * @throws InvalidDepositException 存款money非法异常
     * @throws BankSystemLoginException 未登录存款
     */
    @Override
    public void dePosit(double money) throws InvalidDepositException, BankSystemLoginException, SQLException {

        dbDao.dePosit(money);
    }

    /**
     * 6.转账
     * @param username 转账用户名
     * @param money    转账金额
     * @throws BankSystemLoginException
     * @throws TransferException 转账异常,money非法
     */
    @Override
    public void transfer(String username, double money) throws BankSystemLoginException, TransferException, SQLException {

        dbDao.transfer(username,money);
    }


}
