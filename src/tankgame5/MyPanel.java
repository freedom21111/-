package tankgame5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

//由于子弹是线程，希望把Mypanel也变成线程
public class MyPanel extends JPanel implements KeyListener,Runnable {
    Hero hero=null;
    Vector<EnemyTank> enemyTanks=new Vector<>();//定义敌人的坦克
    Vector<Bomb> bombs=new Vector<>();
    int enemyTankSize=3;
    //定义两张炸弹图片，用于显示炸弹
    Image image1=null;
    //Image image2=null;

    public MyPanel(){
        hero =new Hero(800,200);//初始化
        for(int i=0;i<enemyTankSize;i++){
            EnemyTank enemyTank=new EnemyTank(100*(i+1),0);
            enemyTank.setDirect(2);
            Shot shot=new Shot(enemyTank.getX()+20,enemyTank.getY()+60,enemyTank.getDirect());
            enemyTank.shots.add(shot);
            new Thread(shot).start();
            enemyTanks.add(enemyTank);
            new Thread(enemyTank).start();
        }
        //image1=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_1.jpg"));
       // image2=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_2.jfif"));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0,0,1000,750);//默认黑色
        if(hero.isLive){
        drawTank(hero.getX(),hero.getY(),g,hero.getDirect(),0);
        }
        for(int i=0;i<enemyTanks.size();i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            if (enemyTank.isLive) {//当敌人坦克为活才画
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirect(), 1);
                for (int j = 0; j < enemyTank.shots.size(); j++) {
                    Shot shot = enemyTank.shots.get(j);
                    if (shot.isLive) {
                        g.fill3DRect(shot.x, shot.y, 4, 4, false);
                    } else {
                        enemyTank.shots.remove(shot);
                    }
                }
            }
        }
        for(int i=0;i<hero.shots.size();i++){
            Shot shot=hero.shots.get(i);
            if (shot != null && shot.isLive) {//画出射击的子弹
                  g.fill3DRect(shot.x, shot.y, 4, 4, false);
            }else{
                hero.shots.remove(shot);
            }
        }
            for(int i=0;i<bombs.size();i++){
                Bomb bomb=bombs.get(i);
                if(bomb.life>0){
                    g.drawImage(image1,bomb.x,bomb.y,60,60,this);
                }//else {
                    //g.drawImage(image2,bomb.x,bomb.y,60,60,this);
                //}
                bomb.liveDown();
                if(bomb.life==0){
                    bombs.remove(bomb);
                }
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
    public void hitHero(){
        for(int i=0;i<enemyTanks.size();i++){
            EnemyTank enemyTank=enemyTanks.get(i);
            if(enemyTank!=null){
                for(int j=0;j<enemyTank.shots.size();j++){
                    Shot shot=enemyTank.shots.get(j);
                    hitHeroTank(shot,hero);
                }
            }
        }
    }
    public void hitHeroTank(Shot s,Hero hero){
        switch (hero.getDirect()){
            case 0:
            case 2:
                if(s.x>hero.getX()&&s.x<hero.getX()+40&&s.y<hero.getY()+60&&s.y>hero.getY()){
                    hero.isLive=false;
                    s.isLive=false;
                }
                break;
            case 1:
            case 3:
                if(s.x>hero.getX()&&s.x<hero.getX()+60&&s.y<hero.getY()+40&&s.y>hero.getY()){
                    hero.isLive=false;
                    s.isLive=false;
                }
                break;
        }


    }
    public void hitEnemyTank(){
        for(int j=0;j<hero.shots.size();j++){
            Shot shot=hero.shots.get(j);
            if(shot!=null&&shot.isLive){
                for(int i=0;i<enemyTanks.size();i++){
                    EnemyTank enemyTank=enemyTanks.get(i);
                    hitTank(shot, enemyTank);
                }
            }
        }
    }
    public void hitTank(Shot s, EnemyTank enemyTank){
        switch (enemyTank.getDirect()){
            case 0:
            case 2:
                if(s.x> enemyTank.getX()&&s.x< enemyTank.getX()+40&&s.y> enemyTank.getY()&&s.y< enemyTank.getY()+
                60){
                        s.isLive=false;
                        enemyTank.isLive=false;
                        //创建一个Bomb对象，加入到Bombs集合中
                 Bomb bomb=new Bomb(enemyTank.getX(), enemyTank.getY());
                 bombs.add(bomb);
                 enemyTanks.remove(enemyTank);
                }
                break;
            case 1:
            case 3:
                if(s.x>enemyTank.getX()&&s.x< enemyTank.getX()+60&&s.y> enemyTank.getY()&&s.y< enemyTank.getY()+
                40){
                    s.isLive=false;
                    enemyTank.isLive=false;
                    Bomb bomb=new Bomb(enemyTank.getX(), enemyTank.getY());
                    bombs.add(bomb);
                    enemyTanks.remove(enemyTank);
                }
                break;
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_W){
            hero.setDirect(0);//改变方向
            if(hero.getY()>0)
            hero.moveUp();
        }else if (e.getKeyCode()==KeyEvent.VK_A){
            hero.setDirect(3);
            if(hero.getX()>0)
            hero.moveLeft();
        }else if(e.getKeyCode()==KeyEvent.VK_D){
            hero.setDirect(1);
            if(hero.getX()+60<1000)
            hero.moveRight();
        }else if(e.getKeyCode()==KeyEvent.VK_S){
            hero.setDirect(2);
            if(hero.getY()+60<750)
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
            hitHero();
            hitEnemyTank();
            this.repaint();
        }
    }
}
