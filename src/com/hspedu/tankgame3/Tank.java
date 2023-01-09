package com.hspedu.tankgame3;

public class Tank {
    private int x;
    private int y;
    private int direct;
    public void moveUp(){
        y-=2;
    }
    public void moveRight(){
        x+=2;
    }
    public void moveDown(){
        y+=2;
    }
    public void moveLeft(){
        x-=2;
    }
    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public Tank(int x, int y) {
        this.y = y;
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
