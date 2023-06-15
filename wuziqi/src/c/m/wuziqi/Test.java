package c.m.wuziqi;



import javax.swing.*;
import java.awt.*;
public class Test extends JFrame {
    public JButton button1,button2;
//    Game listener;

    public Test(){
        init();
        ImageIcon icon=new ImageIcon("imgs/2.png");//背景图
        JLabel label = new JLabel(icon);//往一个标签中加入图片
        label.setBounds(0, 0, icon.getIconWidth(),icon.getIconHeight());//设置标签位置大小为图片大小
        this.getLayeredPane().add(label, Integer.valueOf(Integer.MIN_VALUE));//标签添加到第二层面板
        //获取顶层容器设为透明
        JPanel imPanel = (JPanel)getContentPane();
        imPanel.setOpaque(false);
        imPanel.add(button1);
        imPanel.add(button2);
        setTitle("五子棋 ");
        setBounds(100,100,640,640);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }


    void init()
    {
        setLayout(new FlowLayout());
        JPanel jp = new JPanel();
        jp.setLayout(null);
        button1 = new JButton("人机模式");
        button2 = new JButton("双人对战");
        button1.setBackground(Color.cyan);
        button2.setBackground(Color.orange);
        Game u = new Game("人机模式");
        Game p = new Game("双人对战");
        button1.addActionListener(u);
        button2.addActionListener(p);

    }
}
