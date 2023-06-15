package c.m.wuziqi;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

//绘制
public class QiPan extends JPanel {
    //15条横竖线
    private static final int QiPan_Size =15;
    //控制外边距
    private final int margin = 20;
    //创建一个容器来保存棋子对象
    private final List<Location> locationList = new ArrayList<>();
    //创建一个2维数组，用来表示棋盘上被占用的位置   1 :人类  -1:电脑  0:空
    private final int[][] location = new int[QiPan_Size][QiPan_Size];
    //创建一个二维数组表示棋盘位置的分数
    private final int[][] score = new int[QiPan_Size][QiPan_Size];
    //绘图工具
    @Override
    public void paint(Graphics g) {
        super.paint(g);
      //调用画线封装方法
        drawChessBOARD(g);
        drawChess(g);
    }
//判断是否平局
    public boolean ping()
    {
        for (int i = 0; i < QiPan_Size; i++) {
            for (int j = 0; j < QiPan_Size; j++) {
                if (location[i][j]==0)
                    return false;
            }
        }
        return true;
    }
    private void drawChessBOARD(Graphics g) {
//        确定格子的边长
//        getWidth()减去外边距后除QiPan_Size-1可得每个格子的边长
        int CellSize = (getWidth()-2*margin)/(QiPan_Size -1);
        //循环画线
       for (int i = 0; i< QiPan_Size; i++){
           //画横线
           g.drawLine(margin,margin+i*CellSize,
                   getWidth()-margin,margin+i*CellSize);

           //画竖线
           g.drawLine(margin+i*CellSize,margin,
                   margin+i*CellSize,getHeight()-margin);
       }
    }
    //画棋子的方法
    public void drawChess(Graphics g) {

        for (Location loc : locationList) {
            //辨别棋子颜色
            if (loc.getOwner() == 1) {//人类棋子默认黑子
                g.setColor(Color.BLACK);
            } else {
                g.setColor(Color.WHITE);
            }
            //画棋子之前先求出格子大小
            int CellSize = (getWidth() - 2 * margin) / (QiPan_Size - 1);
            g.fillOval(loc.getX() * CellSize + margin - CellSize / 2,
                    loc.getY() * CellSize + margin - CellSize / 2,
                    CellSize,
                    CellSize);
        }
    }
    //落子的方法
    public void addchess(Location location) {
        locationList.add(location);
        repaint();

    }
    //获取格子大小
    public int getCellsize()
    {
        return  (getWidth()-2*margin)/(QiPan_Size -1);
    }
//    判断是否可以落子
    public boolean isLegal(int x, int y)
    {
        //location[x][y]=1;
        return x >= 0 && x <= QiPan_Size && y >= 0 && y <= QiPan_Size && location[x][y] == 0;
    }
    //落子后占用棋盘位置
    public void addLocation(int x, int y, int owner){
        location[x][y]=owner;
    }
    //输赢判断
    public  boolean iswin(int x,int y, int owner) {
        //创建一个变量用来记录同一方向上的相同棋子个数
        int account = 0;
        //判断水平
        //1.水平左侧
        for (int i = x-1; i >=0; i--) {
            if (location[i][y]==owner){
                account++;
            }
            else {
                break;
            }
        }
        //2.水平右侧
        for (int i = x+1; i < QiPan_Size; i++) {
            if (location[i][y]==owner){
                account++;
            }
            else {
                break;
            }
        }
        if (account >=4)
        {
            return true;
        }
        account=0;
        //判断垂直
        //1.垂直上方
        for (int i = y-1; i >=0; i--) {
            if (location[x][i]==owner){
                account++;
            }
            else {
                break;
            }
        }
        //2.垂直下方
        for (int i = y+1; i < QiPan_Size; i++) {
            if (location[x][i]==owner){
                account++;
            }
            else {
                break;
            }
        }
        if (account >=4)
        {
            return true;
        }
        account=0;
        //判断左上和右下
        //1.左上方
        for (int i = y-1, j = x-1; i >=0&&j>=0; i--,j--) {
            if (location[j][i]==owner){
                account++;
            }
            else {
                break;
            }
        }
        //2.右下方
        for (int i = y+1, j = x+1; i < QiPan_Size &&j< QiPan_Size; i++,j++) {
            if (location[j][i]==owner){
                account++;
            }
            else {
                break;
            }
        }
        if (account >=4)
        {
            return true;
        }
        account = 0;
        //判断右上和左下
        //1.右上方
        for (int i = y-1, j = x+1; i >=0&&j< QiPan_Size; i--,j++) {
            if (location[j][i]==owner){
                account++;
            }
            else {
                break;
            }
        }
        //2.左下方
        for (int i = y+1, j = x-1; i < QiPan_Size &&j>=0; i++,j--) {
            if (location[j][i]==owner){
                account++;
            }
            else {
                break;
            }
        }
        return account >= 4;
    }

    //机器落子
    public  Location searchlocation() {
        //初始化score评分，遍历棋盘所有位置
        for (int i = 0; i < QiPan_Size; i++) {
            for (int j = 0; j < QiPan_Size; j++) {
                score[i][j] = 0;
            }
        }

        //每次机器落子都重新计算一次评分
        //寻找五元组
        int hunmanchessmannum = 0;//五元组中机器棋子数目
        int machinechessmannum = 0;//五元组中人类棋子数目
        int tuplescoretmp;//临时得分（临时变量
        //目标位置坐标
        int goalX = -1;
        int goalY = -1;
        //利用计分方法求最大分数
        int maxscore = -1;
        //1.纵向十五行的五元组
        // 每行的横坐标不变，纵坐标增加至10，用k来确定五元组并计分
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 11; j++) {
                int k=j;
                while (k<j+5){
                    //切换到下一个五元组
                    if (location[i][k]==-1)machinechessmannum++;
                    else if(location[i][k]==1)hunmanchessmannum++;
                    k++;
                }
                //求出五元组中人类和机器的棋子数后进行计分赋给当前位置
                tuplescoretmp=tuplescore(hunmanchessmannum,machinechessmannum);
                for (k = j;  k< j+5; k++) {
                    score[i][k]+=tuplescoretmp;
                }
                //参数置零
                hunmanchessmannum=0;
                machinechessmannum=0;
            }
        }
        //2.横向15行,遍历，类似
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 11; j++) {
                int k=j;
                while (k<j+5){
                    if (location[k][i]==-1)machinechessmannum++;
                    else if(location[k][i]==1)hunmanchessmannum++;
                    k++;
                }
                tuplescoretmp=tuplescore(hunmanchessmannum,machinechessmannum);
                for (k = j;  k< j+5; k++) {
                    score[k][i]+=tuplescoretmp;
                }
                hunmanchessmannum=0;
                machinechessmannum=0;
            }
        }
         //3.右上到左下上侧
        //i横坐标，j纵坐标，i--，j++，
        for (int i = 14; i >=4 ; i--) {
            for (int k=i,j = 0; j <15&&k>=0 ; k--,j++) {
                int m=k;
                int n=j;
                while (m>k-5&&k-5>=-1){
                    if(location[m][n]==-1) machinechessmannum++;
                    else if(location[m][n]==1)hunmanchessmannum++;
                    m--;
                    n++;
                }
                //角落附近可能构不成五元组，k的取值没有限制，排除无法构成五元组的位置
                if(m==k-5){
                    tuplescoretmp=tuplescore(hunmanchessmannum,machinechessmannum);
                    for (m=k,n=j;  m>k-5 ; m--,n++) {
                        score[m][n]+=tuplescoretmp;
                    }
                }
                hunmanchessmannum=0;
                machinechessmannum=0;
            }
        }
        //4.扫描右上到坐下的下侧
        for (int i = 1; i <15 ; i++) {
            for (int k=i,j = 14; j>=0&&k<15 ; j--,k++) {
                int m=k;
                int n=j;
                while (m<k+5&&k+5<=15){
                    if(location[n][m]==-1) machinechessmannum++;
                    else if(location[n][m]==1)hunmanchessmannum++;
                    m++;
                    n--;
                }
                //角落附近可能构不成五元组
                if(m==k+5){
                    tuplescoretmp=tuplescore(hunmanchessmannum,machinechessmannum);
                    for (m=k,n=j;  m<k+5 ; m++,n--) {
                        score[n][m]+=tuplescoretmp;
                    }
                }
                hunmanchessmannum=0;
                machinechessmannum=0;
            }
        }

//5.扫描左上到右下上侧
        for (int i = 0; i <11 ; i++) {
            for (int k=i,j = 0; j<15&&k<15 ; j++,k++) {
                int m=k;
                int n=j;
                while (m<k+5&&k+5<=15){
                    if(location[m][n]==-1) machinechessmannum++;
                    else if(location[m][n]==1)hunmanchessmannum++;
                    m++;
                    n++;
                }
                if(m==k+5){
                    tuplescoretmp=tuplescore(hunmanchessmannum,machinechessmannum);
                    for (m=k,n=j;  m<k+5 ; m++,n++) {
                        score[m][n]+=tuplescoretmp;
                    }
                }
                hunmanchessmannum=0;
                machinechessmannum=0;
            }
        }
        //6.扫描左上到右下下测
        for (int i = 1; i <11 ; i++) {
            for (int k=i,j = 0; j<15&&k<15 ; j++,k++) {
                int m=k;
                int n=j;
                while (m<k+5&&k+5<=15){
                    if(location[n][m]==-1) machinechessmannum++;
                    else if(location[n][m]==1)hunmanchessmannum++;
                    m++;
                    n++;
                }
                if(m==k+5){
                    tuplescoretmp=tuplescore(hunmanchessmannum,machinechessmannum);
                    for (m=k,n=j;  m<k+5 ; m++,n++) {
                        score[n][m]+=tuplescoretmp;
                    }
                }
                hunmanchessmannum=0;
                machinechessmannum=0;
            }
        }
        //从剩余位置中找得分最大点
        for (int i=0;i<15;i++){
            for (int  j=0;j<15;j++){
                if (location[i][j]==0&&score[i][j]>maxscore){
                    goalX = i;
                    goalY = j;
                    maxscore = score[i][j];
                }
            }
        }
        if (goalX != -1){
            return new Location(goalX,goalY,-1);
        }
        //无最大值则平局
        return new  Location(0,0,15);
    }
    /*用计分板给每一个五元组计分
    五元组的得分由每一个位置的得分构成
    每个位置的得分即是其八个方向的得分之和
    * */

    private int tuplescore(int hunmanchessmannum, int machinechessmannum) {
        //既有人类棋子又有机器棋子，判分0
        if (hunmanchessmannum>0&&machinechessmannum>0){
            return 0;
        }
        //全部为空，无落子，判分7
        if (hunmanchessmannum==0 && machinechessmannum == 0){
            return 7;
        }
        //机器1子，判分35
        if (machinechessmannum == 1){
            return 35;
        }
        //机器2子，判分800
        if (machinechessmannum == 2){
            return 800;
        }
        //机器3子，判分1500
        if (machinechessmannum == 3){
            return 15000;
        }
        //机器4子，判分800000
        if (machinechessmannum == 4){
            return 800000;
        }
        //人类1子，判分15
        if (hunmanchessmannum == 1){
            return 15;
        }
        //人类2子，判分400
        if (hunmanchessmannum == 2){
            return 400;
        }
        //人类3子，判分1800
        if (hunmanchessmannum == 3){
            return 1800;
        }
        //人类4子，判分100000
        if (hunmanchessmannum == 4){
            return 100000;
        }
        return -1;
    }
}

