
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
public final class GamePanel extends JPanel implements ActionListener{
    static final int SCREEN_WIDTH = 500; 
    static final int SCREEN_HEIGHT = 500;
    static final int UNIT_SIZE = 20;
    static final int DELAY = 60;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    final int x[] = new int[GAME_UNITS],y[] = new int[GAME_UNITS];
    int bodyparts = 3, appleseaten, applex,appley;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;
    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        StartGame();
    }
    public void StartGame(){
        newapple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponet(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        if (running){
            for (int i = 0; i<SCREEN_HEIGHT/UNIT_SIZE;i++){
                g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
                
            }
            g.setColor(Color.red);
            g.fillOval(applex, appley, UNIT_SIZE, UNIT_SIZE);
            for (int i = 0; i<bodyparts;i++){
                if(i==0){
                   g.setColor(Color.green);
                   g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
                else{
                    gameover(g);
                }
            }
        }
    }
    public void gameover(Graphics g){
        g.setColor(Color.red);
        g.setFont(new Font("ink free", Font.BOLD, 60));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", SCREEN_WIDTH - metrics.stringWidth("game over")/2, SCREEN_HEIGHT/2);
    }
    public void newapple(){
        applex= random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appley= random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }
    public void move(){
        for (int i = bodyparts;i>0;i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        switch(direction){
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }
    public void checkapple(){
        if((x[0]==applex)&&(y[0]==appley)){
            bodyparts++;
            appleseaten++;
            newapple();
        }
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running){
            move();
            checkapple();
            collisions();
        }
        repaint();
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public void collisions(){
        for(int i = bodyparts;i>0;i--){
            if((x[0]==x[i])&&(y[0]==y[i])){
                running = false;
            }
        }
        if (x[0]<0){
            running=false;
        }
         if (x[0]>SCREEN_WIDTH){
            running=false;
        }
          if (y[0]<0){
            running=false;
        }
           if (y[0]<SCREEN_HEIGHT){
            running=false;
        }
           if (!running){
               timer.stop();
           }
    }

    private class MyKeyAdapter extends KeyAdapter {
        @Override
     
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction != 'R'){
                        direction ='L';}
                    break;
                    case KeyEvent.VK_RIGHT:
                    if(direction != 'L'){
                        direction ='R';}
                    break;
                    case KeyEvent.VK_UP:
                    if(direction != 'D'){
                        direction ='U';}
                    break;
                    case KeyEvent.VK_DOWN:
                    if(direction != 'U'){
                        direction ='D';}
                    break;
            }
        }
    }

    
}
