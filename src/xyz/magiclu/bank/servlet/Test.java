package xyz.magiclu.bank.servlet;

import xyz.magiclu.bank.model.UserBean;
import xyz.magiclu.bank.service.BankService;
import xyz.magiclu.bank.service.ManagerImpl;

/**
 * Created by Administrator on 2018/8/19.
 */
public class Test {

    public static void main(String[] args) throws Exception {
        UserBean user = new UserBean("李盈琪","123");

        BankService service = new ManagerImpl();

        service.register(user);

        service.dePosit(100);
        System.out.println(service.inquiry());

    }
}
