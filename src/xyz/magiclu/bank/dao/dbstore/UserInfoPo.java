package xyz.magiclu.bank.dao.dbstore;

import java.sql.*;

/**
 * Created by Administrator on 2018/8/17.
 */
public class UserInfoPo {

    private Connection conn;
    private PreparedStatement preStatement;
    private ResultSet resultSet;

    private String username;
    private double money;

    private final String readSql = "select username,money from user WHERE username=?";
    private final String storeSql = "UPDATE USER set money=? where username=?";


    public UserInfoPo(){}

    /**
     * 从数据库获取数据，填充对象
     * @param name
     * @throws SQLException
     */
    public UserInfoPo(String name) throws SQLException {

        this.load(name);
    }

    /**
     * 从数据库载入对象
     * @param name 载入的名字
     * @throws SQLException
     */
    public void load(String name) throws SQLException {

        try {
            //获取连接
            conn = DBConnectManager.getInstance().getConnection();
            preStatement = conn.prepareStatement(readSql);
            preStatement.setString(1,name);
            resultSet = preStatement.executeQuery();
            if(resultSet.next()){
                //初始化属性
                this.username = resultSet.getString(1);
                this.money = resultSet.getDouble(2);
            }
        }finally {
            preStatement.close();
            DBConnectManager.getInstance().recycle(conn);
        }
    }

    /**
     * 持久化人数据库
     * @throws SQLException
     */
    public void store() throws SQLException {

        try {
            //获取连接，更新数据到数据库
            conn = DBConnectManager.getInstance().getConnection();
            preStatement = conn.prepareStatement(storeSql);
            preStatement.setDouble(1,this.money);
            preStatement.setString(2,this.username);
            if(preStatement.executeUpdate() < 0)
                throw new SQLException("存储异常");
        }finally {
            preStatement.close();
            DBConnectManager.getInstance().recycle(conn);
        }
    }


    /**
     * 重写读数据
     * @return
     */
    public void reRead(){
        try {
            this.load(this.username);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
