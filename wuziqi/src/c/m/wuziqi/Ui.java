package c.m.wuziqi;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//用Jframe制作窗体
//Jpanel和Graphics画棋盘
//用鼠标监听实现落子并判断是否合法
//判断输赢
public class Ui {
    //窗体类
    private final JFrame frame = new JFrame();

    //棋盘对象
    private QiPan qipan = new QiPan();
    //制作五子棋窗体
    public void init()
    {
        //设置标题
        frame.setTitle("五子棋人机对战");
        //设置窗体大小
         frame.setSize(516,540);
         //位置居中
        frame.setLocationRelativeTo(null);
        //默认关闭
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(qipan);
        //显示窗体
        frame.setVisible(true);
       /*实现落子功能
        1.添加鼠标监听
            */
        qipan.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //调用画棋子的方法(封装方法play
                play(e);
            }
        });
    }

    //处理鼠标点击事件的方法
    private void play(MouseEvent e) {
        //将鼠标的位置转成棋盘坐标的位置，需要格子的大小
        int cellSize = qipan.getCellsize();
        //保证棋子落在正中间
        int x = (e.getX()-5)/cellSize;
        int y = (e.getY()-5)/cellSize;

        //添加棋子前考虑是否具备落子条件
        //1.落子位置不能有其他棋子
        //2.不能超过棋盘边界
        if (qipan.isLegal(x,y))
        {
            // 添加棋子
            qipan.addchess(new Location(x,y,1));
            //添加棋子后需要占用棋盘上的位置
            qipan.addLocation(x,y,1);
            //每次落子后进行输赢判断
            if (qipan.iswin(x,y,1)){
                //弹出消息提示框
                JOptionPane.showMessageDialog(frame,"人类获胜","游戏结束",
                        JOptionPane.PLAIN_MESSAGE);
                qipan = new QiPan();
            }
            //机器落子
            Location location = qipan.searchlocation();
            if (location.getOwner()==15)
            {
                JOptionPane.showMessageDialog(frame,"平局","游戏结束",JOptionPane.PLAIN_MESSAGE);
                qipan = new QiPan();
            }
            qipan.addchess(location);
            qipan.addLocation(location.getX(),location.getY(),location.getOwner());
            //判断输赢
            if (qipan.iswin(location.getX(),location.getY(),location.getOwner()))
            {
                JOptionPane.showMessageDialog(frame,"机器获胜","游戏结束",
                        JOptionPane.PLAIN_MESSAGE);
                qipan = new QiPan();
            }
        }

    }
}

