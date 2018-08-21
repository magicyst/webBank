package xyz.magiclu.bank.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 所有页面是禁止自动创建session的
 * 拦截登录页面
 * Created by Administrator on 2018/8/8.
 */
@WebFilter(
        filterName = "LoginFilter",
        urlPatterns = {
                "/login"
        }
)
public class LoginFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        System.out.println("LoginFilter init");
    }

    /**
     * 查看用户是否重复登录，提掉前一次登录
     *      1.如果
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if(username == null || username.equals("") || password == null || password.equals(""))
            request.getRequestDispatcher("/index.jsp").forward(request,response);
        else{
            ServletContext app = request.getServletContext();
            List<String> list = (List<String>) app.getAttribute("online_user");
            if(list.contains(username)){
                request.setAttribute("message","已异地登录");
                request.getRequestDispatcher("/prompt.jsp").forward(request,response);
            }
        }
        filterChain.doFilter(servletRequest,servletResponse);

    }

    @Override
    public void destroy() {}
}
