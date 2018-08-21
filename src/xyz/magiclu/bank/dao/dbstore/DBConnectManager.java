package xyz.magiclu.bank.dao.dbstore;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.Queue;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Created by Administrator on 2018/8/15.
 */
public class DBConnectManager {

    //配置文件的位置
    private static final String config_file_path = DBConnectManager.class
            .getResource("/").getPath()+"/filedata/dataBaseConfig.properties";

    private ConnectionConfig config;                                //数据库配置

    private Queue<Connection> pool = new ConcurrentLinkedDeque<>(); //连接池

    /**
     * 私有构造器
     */
    private DBConnectManager(){};

    /**
     * 内部类里拿到单例，确保延迟加载和线程安全
     * @return 返回连接池单例对象
     */
    public static DBConnectManager getInstance(){

        return DataBaseConnectionsHolder.getInstance();
    }

    /*
    连接数据库,并初始化池
     */
    {
        config = new ConnectionConfig();
        try {
            //加载驱动类
            Class.forName(config.getClass_path());

            //获取连接,初始化连接池
            for(int i = 0; i < config.getMaxActive(); i++){
                pool.add(DriverManager.getConnection(config.getUrl(),"root","123"));
            }
        } catch (ClassNotFoundException e) {
            //驱动未找到
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取连接
     * @return 连接
     * @throws SQLException 连接池为空时
     */
    public Connection getConnection() throws SQLException {


        //弹出连接对象
        Connection con = pool.poll();

        if(con == null)
            throw new SQLException("连接池暂时无连接!");

        return con;
    }

    /**
     * 回收连接
     */
    public void recycle(Connection conn) throws IllegalStateException,NullPointerException {

        //如果连接为空
        if(conn == null)
            throw new NullPointerException();

        //如果pool为满,无法添加
        if(!pool.offer(conn))
            throw new IllegalStateException();
    }

    /**
     * 连接超时任务
     */
    private class TimeOutTask extends TimerTask {

        @Override
        public void run() {

        }
    }

    /**
     * 实例持有类（内部类实现单例）
     */
    private static final class DataBaseConnectionsHolder{

        //数据库连接池对象
        private static DBConnectManager instance = new DBConnectManager();

        //对位方法
        public static DBConnectManager getInstance(){

            return instance;
        }
    }

    /**
     * 获取数据库配置文件信息的类
     */
    private final class ConnectionConfig{

        private Properties config;  //获取配置文件
        private String url;         //连接字符串
        private String class_path;  //jdbc驱动类
        private int maxActive;      //最大连接数
        private long maxWait;       //最大等待时间

        {
            config = new Properties();

            //读取配置文件
            try {
                config.load(new FileReader(DBConnectManager.config_file_path));
                //初始化参数
                this.url = config.getProperty("url");
                this.class_path = config.getProperty("class_path");
                this.maxActive = Integer.valueOf(config.getProperty("maxActive"));
                this.maxWait = Integer.valueOf(config.getProperty("maxWait"));
            } catch (IOException e) {
                //读取文件失败
                e.printStackTrace();
            }
        }

        //对外接口

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getClass_path() {
            return class_path;
        }

        public void setClass_path(String class_path) {
            this.class_path = class_path;
        }

        public int getMaxActive() {
            return maxActive;
        }

        public void setMaxActive(int maxActive) {
            this.maxActive = maxActive;
        }

        public long getMaxWait() {
            return maxWait;
        }

        public void setMaxWait(long maxWait) {
            this.maxWait = maxWait;
        }
    }

    public static void main(String[] args) throws SQLException {


        new Thread(new Runnable() {
            @Override
            public void run() {
                DBConnectManager data = DBConnectManager.getInstance();

                Connection conn = null;
                try {
                    conn = data.getConnection();

                    Statement sta = conn.createStatement();

                    DatabaseMetaData dbm = conn.getMetaData();

                    String sql = "select * from emp";

                    ResultSet rs = sta.executeQuery(sql);

                    while (rs.next()) {
                        System.out.println(rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4));
                    }
                    System.out.println("---------------------");
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    DBConnectManager.getInstance().recycle(conn);
                }
            }
        }).start();


    }
}
