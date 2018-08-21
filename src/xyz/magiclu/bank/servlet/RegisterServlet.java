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

/**
 * Created by Administrator on 2018/7/14.
 */
@WebServlet(name = "/RegisterServlet",
        urlPatterns = {
                "/register"
        }
)
public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UserBean user = new UserBean(username,password);
        BankService service = new ManagerImpl();

        //响应字符串
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        //注册
        try {
            //注册成功,则加入用户池,处理器和日志记录注入用户
            service.register(user);
            user.setService(service);
            user.setLogs(new LogInfo(user.getUsername()));

            //创建用户id
            String sessionId = request.getSession().getId();
            String userId = MD5Util.getMD5(sessionId+user.getUsername());

            //加入用户池
            ServletContext app = request.getServletContext();
            Hashtable<String,UserBean> userPool = (Hashtable<String, UserBean>) app.getAttribute("userPool");
            userPool.put(userId,user);

            //进入业务处理页面
            request.setAttribute("userId",userId);
            request.setAttribute("username",user.getUsername());
            request.getRequestDispatcher("/business.jsp").forward(request,response);

        } catch (Exception e) {
            request.setAttribute("message",e.getMessage());
            request.getRequestDispatcher("/prompt.jsp").forward(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request,response);
    }
}
