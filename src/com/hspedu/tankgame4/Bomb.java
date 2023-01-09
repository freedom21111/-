package com.hspedu.tankgame4;

public class Bomb {
    int x,y;
    int life=8;//判断爆炸的生命周期
    boolean isLive=true;

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void liveDown(){//减少生命值
        if(life>0){
            life--;
        }else{
            isLive=false;
        }
    }
}
