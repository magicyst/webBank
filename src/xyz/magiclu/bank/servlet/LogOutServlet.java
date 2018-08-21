package xyz.magiclu.bank.servlet;

import xyz.magiclu.bank.model.UserBean;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

/**
 * 处理注销请求
 * Created by Administrator on 2018/8/18.
 */
@WebServlet(name = "LogOutServlet",
    urlPatterns = {
            "/logout"
    }
)
public class LogOutServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String userId = request.getParameter("userId");
        String username = request.getParameter("username");
        ServletContext app = request.getServletContext();

        //获取在线用户表，然后获取在线用户对象
        Hashtable<String,UserBean> userPool = (Hashtable<String, UserBean>) app.getAttribute("userPool");
        List<String> online_user = (List<String>) app.getAttribute("online_user");

        userPool.remove(userId);
        online_user.remove(username);


        request.setAttribute("message","注销成功");
        request.getRequestDispatcher("/prompt.jsp").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);
    }
}
