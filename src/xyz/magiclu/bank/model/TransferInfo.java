package xyz.magiclu.bank.model;

import java.util.ArrayList;

/**
 * 存储用户的转账信息
 * Created by Administrator on 2018/8/16.
 */
public class TransferInfo {

    private ArrayList<String> news = new ArrayList<>();

    private ArrayList<String> older = new ArrayList<>();

    public String[] getNews(){

        return (String[]) older.toArray();
    }

    public void readed(){
        older.addAll(news);
        news.clear();
    }
}
