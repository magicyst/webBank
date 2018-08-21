package xyz.magiclu.bank.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

/**
 * 编码拦截器
 * Created by Administrator on 2018/8/12.
 */
@WebFilter(
        filterName = "EncodingFilter",
        urlPatterns = {
            "/*"
        },
        initParams = {
            @WebInitParam(name = "encoding", value = "utf-8")
        }
)
public class EncodingFilter implements Filter {

    String encoding;

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        req.setCharacterEncoding(encoding);
        resp.setCharacterEncoding(encoding);
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

        encoding = config.getInitParameter("encoding");
    }

}
