package flappyBird;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Bird extends Rectangle {
    private static final long serialVersionUID = 1L;
    private double gravity = 0.3;
    private double velocity = 0;
    private double lift = -8.5;
    private ArrayList<Rectangle> blocks;
    private BufferedImage img;
    public static boolean started, gameOver;


    public Bird(int x, int y, ArrayList<Rectangle> blocks){
        setBounds(x,y, 30,30);
        this.blocks = blocks;


        img = null;
        try {
            img = ImageIO.read(new File("res/bird.png"));
        } catch (IOException e) {
        }
    }

    public void jump(){
        started = true;
        velocity += lift;
    }

    public void update(){
        if(started){
            velocity += gravity;
            y += velocity;

            if(y >= Flappy.HEIGHT-130){
                y = Flappy.HEIGHT-125;
                velocity = 0;
            }
            if(y <= 0){
                y = 0;
                velocity += gravity+2;
                y += velocity;

            }
            for (int i = 0; i < blocks.size(); i++) {
                if(this.intersects(blocks.get(i))) {
                    gameOver = true;
                    JOptionPane.showMessageDialog(null,"Game Over! Your score is: " + (int) Flappy.score );
                    System.exit(1);

                }
            }
        }
    }
    public void render(Graphics g){
       g.drawImage(img,x,y,  null);

       g.setColor(Color.white);
       g.setFont(new Font("Arial", 1, 50));

       if (!started) {
           g.drawString("Click to start!", 150, Flappy.HEIGHT/ 2 - 50);
       }
       if(gameOver == true){
           g.drawString("Game Over!", 150, Flappy.HEIGHT/ 2 - 50);
       }

    }

}

