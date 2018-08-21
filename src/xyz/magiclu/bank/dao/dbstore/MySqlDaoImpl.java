package xyz.magiclu.bank.dao.dbstore;

import xyz.magiclu.bank.model.UserBean;
import xyz.magiclu.bank.util.AccountOverDrawnException;
import xyz.magiclu.bank.util.BankSystemLoginException;
import xyz.magiclu.bank.util.InvalidDepositException;
import xyz.magiclu.bank.util.TransferException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Administrator on 2018/8/15.
 */
public class MySqlDaoImpl extends AbstractDBDao {

    private UserInfoPo userInfo;

    public MySqlDaoImpl(){}

    public MySqlDaoImpl(UserBean user) throws SQLException {
        //拿到用户数据
        userInfo = new UserInfoPo(user.getUsername());
    }

    @Override
    public double inquire() throws BankSystemLoginException {
        //先更新数据在查询
        userInfo.reRead();
        return userInfo.getMoney();
    }

    /**
     * 取款
     * @param money
     * @throws AccountOverDrawnException
     * @throws BankSystemLoginException
     */
    @Override
    public void withdrawals(double money) throws AccountOverDrawnException, SQLException {

        //先更新数据
        userInfo.reRead();

        double current = userInfo.getMoney();

        if(money > current)
            throw new AccountOverDrawnException("余额不足");

        //取款
        userInfo.setMoney(current-money);

        //写入数据库
        userInfo.store();

    }

    /**
     * 存款
     * @param money
     * @throws InvalidDepositException
     * @throws BankSystemLoginException
     */
    @Override
    public void dePosit(double money) throws InvalidDepositException, SQLException {

        //先更新数据
        userInfo.reRead();

        userInfo.setMoney(userInfo.getMoney()+money);

        //写入数据库
        userInfo.store();
    }

    /**
     * 会产生并发问题
     *      1.多人同时向同一用户转账的时候。对产生读写安全问题
     *      2.读写锁，数据库
     * @param username
     * @param money
     * @throws TransferException
     */
    @Override
    public void transfer(String username, double money) throws TransferException, SQLException {

        if(userInfo.getMoney() < money)
            throw new TransferException("余额不足");

        //放在并发下向同一个人转账
        synchronized (this.getClass()) {
            String sql;
            PreparedStatement state = null;
            Connection conn = null;
            try {
                conn = DBConnectManager.getInstance().getConnection();

                //开启事务
                conn.setAutoCommit(false);

                //目标用户money增加
                sql = "UPDATE user SET money=money+"+money+" WHERE username='"+username+"'";
                state = conn.prepareStatement(sql);
                state.executeUpdate();

                //当前用户money减少
                sql = "UPDATE user SET money=money-"+money+" WHERE username='"+userInfo.getUsername()+"'";
                state = conn.prepareStatement(sql);
                state.executeUpdate();

                //事务提交
                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                throw new TransferException("转账用户不存在");
            }finally {
                try {
                    state.close();
                    DBConnectManager.getInstance().recycle(conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
