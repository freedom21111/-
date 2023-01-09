package com.hspedu.tankgame3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;
//由于子弹是线程，希望把Mypanel也变成线程
public class MyPanel extends JPanel implements KeyListener,Runnable {
    Hero hero=null;
    Vector<EnemyTank> enemyTanks=new Vector<>();//定义敌人的坦克
    int enemyTankSize=3;
    public MyPanel(){
        hero =new Hero(100,100);//初始化
        for(int i=0;i<enemyTankSize;i++){
            enemyTanks.add(new EnemyTank(100*(i+1),10));
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0,0,1000,750);//默认黑色
        drawTank(hero.getX(),hero.getY(),g,hero.getDirect(),0);
        for(int i=0;i<enemyTanks.size();i++){
            EnemyTank enemyTank= enemyTanks.get(i);
            drawTank(enemyTank.getX(),enemyTank.getY(),g,0,1);
        }
        if(hero.shot!=null&&hero.shot.isLive==true){//画出射击的子弹
            g.fill3DRect(hero.shot.x,hero.shot.y,4,4,false);
        }

    }
    public void drawTank(int x,int y,Graphics g,int direct,int type){
        switch (type){//不同类型，坦克设置颜色
            case 0://我的坦克
                g.setColor(Color.cyan);
                break;
            case 1://敌人坦克
                g.setColor(Color.yellow);
                break;
        }
        switch (direct){//不同方向，绘制坦克
            case 0://表示向上
                g.fill3DRect(x,y,10,60,false);
                g.fill3DRect(x+30,y,10,60,false);
                g.fill3DRect(x+10,y+10,20,40,false);
                g.fillOval(x+10,y+20,20,20);
                g.drawLine(x+20,y+30,x+20,y);
                break;
            case 1://向右
                g.fill3DRect(x,y,60,10,false);
                g.fill3DRect(x,y+30,60,10,false);
                g.fill3DRect(x+10,y+10,40,20,false);
                g.fillOval(x+20,y+10,20,20);
                g.drawLine(x+30,y+20,x+60,y+20);
                break;
            case 2://表示向上
                g.fill3DRect(x,y,10,60,false);
                g.fill3DRect(x+30,y,10,60,false);
                g.fill3DRect(x+10,y+10,20,40,false);
                g.fillOval(x+10,y+20,20,20);
                g.drawLine(x+20,y+30,x+20,y+60);
                break;
            case 3://向左
                g.fill3DRect(x,y,60,10,false);
                g.fill3DRect(x,y+30,60,10,false);
                g.fill3DRect(x+10,y+10,40,20,false);
                g.fillOval(x+20,y+10,20,20);
                g.drawLine(x+30,y+20,x,y+20);
                break;
            default:
                System.out.println("暂时没有处理");
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_W){
            hero.setDirect(0);//改变方向
            hero.moveUp();
        }else if (e.getKeyCode()==KeyEvent.VK_A){
            hero.setDirect(3);
            hero.moveLeft();
        }else if(e.getKeyCode()==KeyEvent.VK_D){
            hero.setDirect(1);
            hero.moveRight();
        }else if(e.getKeyCode()==KeyEvent.VK_S){
            hero.setDirect(2);
            hero.moveDown();
        }
        if(e.getKeyCode()==KeyEvent.VK_J){
            hero.shotEnemyTank();
        }
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.repaint();
        }
    }
}
