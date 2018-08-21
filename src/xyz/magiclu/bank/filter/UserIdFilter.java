package xyz.magiclu.bank.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2018/8/18.
 */
@WebFilter(filterName = "UserIdFilter",
        urlPatterns = {
                "/business"
        }
)
public class UserIdFilter implements Filter {
    public void destroy() {
    }

    /**
     * 如果没有UserId参数则拦截，返回登录页面
     * @param req
     * @param resp
     * @param chain
     * @throws ServletException
     * @throws IOException
     */
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        System.out.println("userId filter");
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String userId = request.getParameter("userId");
        if(userId == null || userId.equals("")) {
            response.sendRedirect("/bank/index.jsp");
            return;
        }else {
            System.out.println("userId filter");
            chain.doFilter(req, resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {}

}
