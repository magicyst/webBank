package xyz.magiclu.bank.dao.filestore;

import xyz.magiclu.bank.dao.dbstore.DBConnectManager;
import xyz.magiclu.bank.model.UserBean;
import xyz.magiclu.bank.util.BankSystemLoginException;
import xyz.magiclu.bank.util.BankSystemRegisterException;
import xyz.magiclu.bank.util.MD5Util;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @version bank1.5
 * Created by Administrator on 2018/6/20.
 *
 * 文件存储类:
 *      1、一个用户文件，所有的信息都存储在一个文件夹里面
 *      2、用户的文件标识为:用户名.properties
 */
public class FileDaoImpl extends AbstractFileDao {
    /**
     * 文件dao:
     *      1.注册
     *      2.登录
     */

    //该文件dao维持的一个用户Properties文件对象
    private UserFilePo current_user = null;

    //文件存储路径
    public  static final String FILE_URL = FileDaoImpl.class.getResource("/").getPath()+"filedata\\register\\";

    /**
     * 1.注册
     *      1.1 判断注册用户是否存在
     * @param user 注册用户bean
     * @exception BankSystemRegisterException 注册异常:如果为找到该用户文件,这注册异常
     * @exception IOException 文件读取异常
     */
    @Override
    public void register(UserBean user) throws BankSystemRegisterException, IOException, SQLException {

        //新建一个注册用户文件对象
        File file = new File(FILE_URL+user.getUsername()+".properties");

        //如果存在该用户文件，即该用户名已经被注册了，抛出注册异常对象(用户名存在)
        if(file.exists())
            throw new BankSystemRegisterException("the username exist");

        //创建一个默认Properties文件对象
        UserFilePo proread_register = proread_register = new UserFilePo();

        //为文件对象设置信息(用户名，和密码，账户余额默认为模板余额:0.0元)
        proread_register.setPassword(MD5Util.getMD5(user.getPassword()));
        proread_register.setUsername(user.getUsername());

        //如果注册在该语句之前没有抛出异常，这让改对象维持改注册用户的Properties文件对象
        current_user = proread_register;

        //该用户Properties对象持久化
        current_user.store();

        //同步到mysql
        String sql = "INSERT ignore INTO user(username) VALUES('"+current_user.getUsername()+"')";
        Connection conn = DBConnectManager.getInstance().getConnection();
        PreparedStatement state = conn.prepareStatement(sql);
        state.executeUpdate();

        state.close();
        DBConnectManager.getInstance().recycle(conn);
    }

    /**
     * 2.登录
     * @param user 登录UserBean
     * @exception BankSystemLoginException 登录异常
     */
    @Override
    public void login(UserBean user) throws BankSystemLoginException{

        //创建一个默认Properties文件对象
        UserFilePo proread_login = new UserFilePo();

        //通过该用户名加载该用户文件到对象
        proread_login.load(user.getUsername());

        //如果密码不正确，抛出登录异常对象
        if(!proread_login.getPassword().equals(MD5Util.getMD5(user.getPassword())))
            throw new BankSystemLoginException("密码不正确!");

        //如果登录没有异常,这可以维持改用户Properties文件对象
        current_user = proread_login;

    }


    /**
     *获取登录用户
     * @return 已经登录的用户名,未登录返回null
     */
    @Override
    public String getLoginUserName() {

        if(current_user != null)
            return current_user.getUsername();

        return null;
    }



}
