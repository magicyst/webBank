package xyz.magiclu.bank.servlet;

import xyz.magiclu.bank.dao.dbstore.LogInfo;
import xyz.magiclu.bank.service.BankService;
import xyz.magiclu.bank.service.ManagerImpl;
import xyz.magiclu.bank.model.UserBean;
import xyz.magiclu.bank.util.MD5Util;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.List;

/**
 * 所有页面禁用session的自动创建
 * session的创建受LoginFilter的控制
 * 每个用户保证一个session对象及一个sessionId
 *      验证用户是否存在用户池
 *          存在:从用户池拿到用户名映射的sessionID,然后response.encodeRedirectURL("/Login")
 *          不存在:request.getSession(true)c串session ,然后url重写，重定向/Login
 * Created by Administrator on 2018/7/14.
 */
@WebServlet(
        name = "LoginServlet",
        urlPatterns = {
                "/login"
        }
)
public class LoginServlet extends HttpServlet {

    /**
     * 处理doPost
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //获取用户名密码
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        //放在userBean,登录
        UserBean user = new UserBean(username,password);
        BankService service = new ManagerImpl();

        //响应客户端
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        try {
            //登录成功，把用户处理器注入到userBean中
            service.login(user);
            user.setService(service);
            user.setLogs(new LogInfo(user.getUsername()));

            //创建用户id
            String sessionId = request.getSession().getId();
            String userId = MD5Util.getMD5(sessionId+user.getUsername());

            //加入用户池
            ServletContext app = request.getServletContext();
            Hashtable<String,UserBean> userPool = (Hashtable<String, UserBean>) app.getAttribute("userPool");
            List<String> list = (List<String>) app.getAttribute("online_user");
            list.add(user.getUsername());
            userPool.put(userId,user);

            //进入业务处理页面
            request.setAttribute("userId",userId);
            request.setAttribute("username",user.getUsername());
            request.getRequestDispatcher("/business.jsp").forward(request,response);
        } catch (Exception e) {
            request.setAttribute("message",e.getMessage());
            System.out.println(e.getMessage());
            request.getRequestDispatcher("/prompt.jsp").forward(request,response);
        }
    }

    /**
     * doGet
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doGet(request,response);
    }

}
