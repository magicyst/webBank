package xyz.magiclu.bank.dao.filestore;

import xyz.magiclu.bank.dao.BankDaoInterface;
import xyz.magiclu.bank.model.UserBean;
import xyz.magiclu.bank.util.*;

import java.io.IOException;
import java.sql.SQLException;

/**
 * 接口适配(为了主要实现自己的功能)
 * Created by Administrator on 2018/8/16.
 */
public abstract class AbstractFileDao implements BankDaoInterface {

    /*
    fileDao实现
        1.登录
        2.注册
        3.注销
        4.获取登录名的功能
     */
    @Override
    public abstract void register(UserBean user) throws IOException, BankSystemRegisterException, SQLException;

    @Override
    public abstract void login(UserBean user) throws BankSystemLoginException;

    @Override
    public abstract String getLoginUserName();

    @Override
    public double inquire() throws BankSystemLoginException {
        return 0;
    }

    @Override
    public void withdrawals(double money) throws AccountOverDrawnException, BankSystemLoginException {

    }

    @Override
    public void dePosit(double money) throws InvalidDepositException, BankSystemLoginException {

    }

    @Override
    public void transfer(String username, double money) throws BankSystemLoginException, TransferException {

    }
}
