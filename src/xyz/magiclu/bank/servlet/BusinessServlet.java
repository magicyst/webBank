package xyz.magiclu.bank.servlet;

import org.json.JSONObject;
import xyz.magiclu.bank.dao.dbstore.LogInfo;
import xyz.magiclu.bank.model.UserBean;
import xyz.magiclu.bank.service.BankService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Hashtable;

/**
 * Created by Administrator on 2018/8/18.
 */
@WebServlet(name = "BusinessServlet",
    urlPatterns = {
            "/business"
    }
)
public class BusinessServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //业务类型
        String type = request.getParameter("type");

        String userId = request.getParameter("userId");
        ServletContext app = request.getServletContext();

        //获取在线用户表，然后获取在线用户对象
        Hashtable<String,UserBean> userPool = (Hashtable<String, UserBean>) app.getAttribute("userPool");
        UserBean user = userPool.get(userId);

        //如果用户池没有该用户，则重定向主页
        System.out.println("business");
        if(user == null) {
            System.out.println(user==null);
            response.sendRedirect("/bank/index.jsp");
            return;
        }

        //获取该用户里面的业务处理器
        BankService service = user.getService();
        LogInfo logs = user.getLogs();

        //响应客户端的信息
        String msg = "";

        //响应响应客户端
        PrintWriter out = response.getWriter();

        //获取时间
        SimpleDateFormat timer = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //根据业务类型处理请求
        try {
            switch (type) {
                case "inquiry":     //查询
                    response.setContentType("text/html");
                    double money= service.inquiry();
                    out.write(""+money);
                    break;
                case "dePosit":     //存款
                    response.setContentType("text/html");
                    double dePosit_money = Double.valueOf(request.getParameter("dePosit_money"));
                    service.dePosit(dePosit_money);
                    logs.addLog(timer.format(System.currentTimeMillis())+"  存款"+dePosit_money+"元");
                    out.print(""+dePosit_money);
                    break;
                case "withdrawals": //取款
                    response.setContentType("text/html");
                    double withdrawals_money = Double.valueOf(request.getParameter("withdrawals_money"));
                    service.withdrawals(withdrawals_money);
                    logs.addLog(timer.format(System.currentTimeMillis())+"  取款"+withdrawals_money+"元");
                    out.print(""+withdrawals_money);
                    break;
                case "transfer":    //转账
                    response.setContentType("text/html");
                    double transfer_money = Double.valueOf(request.getParameter("transfer_money"));
                    String target = request.getParameter("target_name");
                    service.transfer(target,transfer_money);
                    logs.addLog(timer.format(System.currentTimeMillis())+"  给"+target+"转账"+transfer_money+"元");
                    out.print(""+transfer_money);
                    break;

                case "log":
                    JSONObject json = new JSONObject(user);
                    System.out.println(json);
                    response.setContentType("application/json");
                    out.print(json);
                    break;
            }
        }catch (Exception e){
            response.setStatus(404);
        }finally {
            out.close();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request,response);
    }
}
