package tankgame5;

import java.util.Vector;

public class EnemyTank extends Tank implements Runnable {
    Vector<Shot>shots=new Vector<>();
    public EnemyTank(int x,int y){
        super(x,y);
    }
    boolean isLive=true;
    Shot shot=null;
    @Override
    public void run() {
        while(true){
            if(isLive&&shots.size()==0){
                switch (getDirect()){
                    case 0:
                         shot=new Shot(getX()+20,getY(),0);
                        break;
                    case 1:
                         shot=new Shot(getX()+60,getY()+20,1);
                        break;
                    case 2:
                         shot=new Shot(getX()+20,getY()+60,2);
                        break;
                    case 3:
                        shot=new Shot(getX(),getY()+20,3);
                        break;
                }
                new Thread(shot).start();
                shots.add(shot);
            }
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
            setDirect((int)(Math.random()*4));//random:取值范围[0,1)____其中包含小数；
            //写并发问题，线程问题一定需要考虑什么时候退出线程
            if(!isLive){
                break;
            }
        }
    }

}
