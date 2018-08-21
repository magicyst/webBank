package xyz.magiclu.bank.dao.dbstore;

import xyz.magiclu.bank.dao.BankDaoInterface;
import xyz.magiclu.bank.model.UserBean;
import xyz.magiclu.bank.util.*;

import java.io.IOException;
import java.sql.SQLException;

/**
 * 数据库存适配
 *  1.账户信息
 *  2.交易信息
 * Created by Administrator on 2018/8/15.
 */
public abstract class AbstractDBDao implements BankDaoInterface{

    /*
    dbDao实现：
        1.查询余额
        2.取款
        3.存款
        4.转账
     */

    @Override
    public abstract double inquire() throws BankSystemLoginException;

    @Override
    public abstract void withdrawals(double money) throws AccountOverDrawnException, BankSystemLoginException, SQLException;

    @Override
    public abstract void dePosit(double money) throws InvalidDepositException, BankSystemLoginException, SQLException;

    @Override
    public abstract void transfer(String username, double money) throws BankSystemLoginException, TransferException, SQLException;

    @Override
    public void register(UserBean user) throws IOException, BankSystemRegisterException {

    }

    @Override
    public void login(UserBean user) throws BankSystemLoginException {

    }

    @Override
    public String getLoginUserName() {
        return null;
    }

}
