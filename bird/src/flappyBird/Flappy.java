package flappyBird;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Flappy extends Canvas implements Runnable, KeyListener, MouseListener {
    public static final int WIDTH = 600, HEIGHT = 600;
    private Thread thread;
    private boolean running = false;
    private Background background;
    private Bird bird;
    public static double score = 0;

    public Flappy() {
        background = new Background(99);
        bird = new Bird(50, HEIGHT - 450, background.blocks);
        addKeyListener(this);
        addMouseListener(this);
    }

    public synchronized void start(){
        if(running)return;
        else{
            running = true;
            thread = new Thread(this);
            thread.start();
        }
    }
    public synchronized void stop(){
        if(running) return;
        else{
            running = false;
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
	    Flappy flappybird = new Flappy();
	    JFrame frame = new JFrame();
	    frame.add(flappybird);
	    frame.setResizable(false);
	    frame.setSize(WIDTH,HEIGHT);
	    frame.setLocationRelativeTo(null);
	    frame.setTitle("Flappy Bird");
	    frame.setVisible(true);
	    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        flappybird.start();
    }

    @Override
    public void run() { // Amikor elkezdődik a "Thread" automatikusan elindul a Run metódusunk
        int fps = 0;
        double delta = 0;
        double timer = System.currentTimeMillis();
        long lasttime = System.nanoTime();
        double ns = 1000000000 / 60;
        while(running){
            long now = System.nanoTime();
            delta += (now - lasttime) / ns;
            lasttime = now;

            while(delta >= 1){  //másodpercenként 60 szor renderel és updateli a frame-t
                update();
                render();
                fps++;
                delta--;
            }

            if(System.currentTimeMillis() - timer >= 1000){
                System.out.println("FPS: " + fps );
                fps = 0;
                timer += 1000;
            }
        }
        stop();
    }

    private void update() {
        background.update();
        bird.update();
    }

    private void render() {
        BufferStrategy bs = getBufferStrategy();
        if(bs == null){   //nem akarunk minden másodpercbe létrehozni bs-t ezért kell egy if statement, ha még nem hoztunk létre, akkor fusson le
            createBufferStrategy(3); //triple buffering -- következő két ablak kinézet már megvan a háttérben
            return;
        }
        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.cyan);  //Háttér színének beállítása kékre
        g.fillRect(0,0, WIDTH, HEIGHT); // feltöltünk egy "téglalapot" színnel.. most a hátteret


        g.setColor(Color.green.darker());
        g.fillRect(0,HEIGHT-100,WIDTH,HEIGHT);

        g.setColor(Color.white);
        g.setFont(new Font("Arial", 1, 50));

        background.render(g);
        bird.render(g);

        g.setFont(new Font("Arial",1,20));
        g.drawString("Score: " + (int)score, 0,15);

        g.setColor(Color.yellow);
        g.fillRect(0,HEIGHT-80,WIDTH,HEIGHT);

        bs.show();  // "Swap the buffers" --> megmutatja a következő képet
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP){
            bird.jump();
        }
    }
    @Override
    public void mousePressed(MouseEvent e) {
        bird.jump();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }
    @Override
    public void mouseReleased(MouseEvent e) {

    }
    @Override
    public void mouseEntered(MouseEvent e) {

    }
    @Override
    public void mouseExited(MouseEvent e) {

    }
}
