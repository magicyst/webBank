package xyz.magiclu.bank.dao.dbstore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/8/19.
 */
public class LogInfo {

    private List<String> loadlogs;
    private String username;

    Connection conn;
    PreparedStatement state;
    ResultSet res;

    public LogInfo(String username){
        this.username = username;
        loadlogs = new ArrayList<>();
        loadLog();
    }

    private void loadLog(){
        String sql = "SELECT message FROM log WHERE username='"+this.username+"'";

        try {
            conn = DBConnectManager.getInstance().getConnection();
            state = conn.prepareStatement(sql);
            res = state.executeQuery();
            while (res.next()){
                String log = "";
                log += res.getString(1);
                loadlogs.add(log);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                res.close();
                state.close();
                DBConnectManager.getInstance().recycle(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void store(String log){
        String sql = "INSERT ignore INTO log(username,message) VALUES(?,?)";
        try {
            conn = DBConnectManager.getInstance().getConnection();
            state = conn.prepareStatement(sql);
            state.setString(1,this.username);
            state.setString(2,log);
            state.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                res.close();
                state.close();
                DBConnectManager.getInstance().recycle(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void addLog(String log){
        loadlogs.add(log);
        store(log);
    }

    public List<String> getLoadlogs() {

        return loadlogs;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
