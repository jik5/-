package c.m.wuziqi;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game implements ActionListener {
    private final String Moshi;
    public Game(String s){
        Moshi= s;
    }
    public void actionPerformed(ActionEvent event){
        //ActionEvent对应按钮点击、菜单选择、选择列表项或在文本框中ENTER
        if (Moshi.equals("人机模式")){
            new Ui().init();
        }
        else if (Moshi.equals("双人对战")){
            new pk().init();
        }
    }

}
