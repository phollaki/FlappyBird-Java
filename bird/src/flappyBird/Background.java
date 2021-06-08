package flappyBird;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Background {
    public ArrayList<Rectangle> blocks; // akadályokat tartalmazó listánk
    private int time;
    private int currentTime = 0;
    private double speed = 3;
    private Random random;
    private int SPACE = 125;
    private final int blocksWidth = 50;
    public static double score = 0;

    public Background(int time){
        blocks = new ArrayList<>();
        this.time = time;
        random = new Random();
    }
    public void update(){
        currentTime++;
        if(currentTime == time){
            currentTime = 0;

            int height1 = random.nextInt(Flappy.HEIGHT / 2); //a felső akadály alsó pontjának meghatározása, random méret max az ablak méretének feléig

            int y2 = height1 + SPACE; //mi az alsó akadály y (tető)pontja
            int height2 = Flappy.HEIGHT - y2; //mi az alsó akadály alja

            blocks.add(new Rectangle(Flappy.WIDTH,0,blocksWidth,height1));
            blocks.add(new Rectangle(Flappy.WIDTH,y2,blocksWidth,height2-100));
        }

        for (int i = 0; i < blocks.size(); i++) {
            Rectangle rect = blocks.get(i);
            rect.x -= speed;

            if(rect.x+rect.width <= 0){
                if(Bird.started){
                    Flappy.score += 0.5;
                }
            }
            if(rect.x+rect.width <= 0){
                blocks.remove(i--);      //hogy ha kimegy az akadály a ablakból, azt a részét töröljük

            }
        }
    }

    public void render(Graphics g){
        g.setColor(Color.green.darker());

        for (int i = 0; i < blocks.size(); i++) {
            Rectangle rect = blocks.get(i);
            g.fillRect(rect.x,rect.y,rect.width,rect.height);
        }
    }
}

