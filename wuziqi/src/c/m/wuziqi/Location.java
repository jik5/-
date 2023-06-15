package c.m.wuziqi;

//棋子对象
public class Location {
    //棋盘上x,y的坐标
    private int x;
    private int y;
    private int owner;//1:人类; -1 : 电脑; 0：空;

    public Location(int x, int y, int owner) {
        this.x = x;
        this.y = y;
        this.owner = owner;
    }

    public int getX() { return x; }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }
}
