package tankgame5;

import javax.swing.*;

public class HspTankGame05 extends JFrame {

    MyPanel mp=null;
    public static void main(String[] args) {
        new HspTankGame05();
    }
    public HspTankGame05(){
        mp=new MyPanel();
        Thread thread=new Thread(mp);
        thread.start();
        this.add(mp);
        this.addKeyListener(mp);
        this.setSize(1200,950);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

}
