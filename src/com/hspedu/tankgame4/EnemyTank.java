package com.hspedu.tankgame4;

import java.util.Vector;

public class EnemyTank extends Tank implements Runnable {
    Vector<Shot>shots=new Vector<>();
    public EnemyTank(int x,int y){
        super(x,y);
    }
    boolean isLive=true;

    @Override
    public void run() {
        while(true){
            switch (getDirect()){
                case 0:
                    for (int i = 0; i < 20; i++) {
                        if (getY()>0){
                        moveUp();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 1:
                    for (int i = 0; i < 20; i++) {
                    if(getY()+60<1000){
                        moveRight();
                    }
                    try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 2:
                    for (int i = 0; i < 20; i++) {
                        if(getY()+60<750){
                        moveDown();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 3:
                    for (int i = 0; i < 20; i++) {
                        if(getX()>0){
                        moveLeft();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
            setDirect((int)(Math.random()*4));//random:????????????[0,1)____?????????????????????
            //????????????????????????????????????????????????????????????????????????
            if(!isLive){
                break;
            }
        }
    }
}
