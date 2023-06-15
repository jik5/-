package c.m.wuziqi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class pk {
    //窗体类
    private final JFrame frame = new JFrame();

    //棋盘对象
    private QiPan qipan = new QiPan();
    //制作五子棋窗体
    public void init()
    {
        //设置标题
        frame.setTitle("五子棋双人模式");
        //设置窗体大小
        frame.setSize(516,540);
        //位置居中
        frame.setLocationRelativeTo(null);
        //默认关闭
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(qipan);
        //显示窗体
        frame.setVisible(true);
        frame.setBackground(Color.cyan);
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
    public static int i=1;
    //处理鼠标点击事件的方法
    private void play(MouseEvent e) {
        //将鼠标的位置转成棋盘坐标的位置，需要格子的大小
        int CellSize = qipan.getCellsize();
        //保证棋子落在正中间
        int x = (e.getX()-5)/CellSize;
        int y = (e.getY()-5)/CellSize;

        //添加棋子前考虑是否具备落子条件
        //1.落子位置不能有其他棋子
        //2.不能超过棋盘边界
        if (qipan.isLegal(x,y))
        {

            // 添加棋子*****
            Location location = new Location(x,y,i);
            qipan.addchess(location);
            //添加棋子后需要占用棋盘上的位置
            qipan.addLocation(x,y,i);
            //落子后判断是否平局
            if (qipan.ping())
            {
                JOptionPane.showMessageDialog(frame,"平局","游戏结束",JOptionPane.PLAIN_MESSAGE);
                qipan = new QiPan();
            }
            //每次落子后进行输赢判断
            if (qipan.iswin(x,y,1)){
                //弹出消息提示框
                JOptionPane.showMessageDialog(frame,"黑子获胜","游戏结束",
                        JOptionPane.PLAIN_MESSAGE);
                qipan = new QiPan();
            }

            //每次落子后进行输赢判断
            if (qipan.iswin(x,y,-1)){
                //弹出消息提示框
                JOptionPane.showMessageDialog(frame,"白子获胜","游戏结束",
                        JOptionPane.PLAIN_MESSAGE);
                qipan = new QiPan();
            }
            i=-i;
        }

    }
}

