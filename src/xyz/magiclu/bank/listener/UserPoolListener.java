package xyz.magiclu.bank.listener; /**
 * Created by Administrator on 2018/8/18.
 */

import xyz.magiclu.bank.model.UserBean;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

@WebListener()
public class UserPoolListener implements ServletContextListener{

    private int maxUser;
    @Override
    public void contextInitialized(ServletContextEvent sce) {

        ServletContext app = sce.getServletContext();
        maxUser = Integer.valueOf(app.getInitParameter("maxUser"));
        System.out.println("最大在线人数:"+maxUser);

        Hashtable<String,UserBean> userPool = new Hashtable<>();
        List<String> list = new ArrayList<>();
        app.setAttribute("online_user",list);
        app.setAttribute("userPool",userPool);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
