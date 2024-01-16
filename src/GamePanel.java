import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.io.File;
import javax.sound.sampled.*;
class bodyPart{
public int x;
public int y;
public char curDir;
public char prevDir;
}
public class GamePanel extends JPanel implements ActionListener{

Snake snake = new Snake();
public static gameOverPanel gameOverObj;
String roundStomachVerAdrs = String.valueOf(MainMenu.class.getResource("assets/roundStomachVer.png"));
File roundStomachVer;
String roundStomachHorizAdrs = String.valueOf(MainMenu.class.getResource("assets/roundStomachHoriz.png"));
File roundStomachHoriz;
static final int SCREEN_WIDTH = 900;
static final int SCREEN_HEIGHT = 600;
static final int UNIT_SIZE = 30;
static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/(UNIT_SIZE*UNIT_SIZE);
int DELAY = 75;
static bodyPart[] bp = new bodyPart[GAME_UNITS];
public final int[] x = new int[GAME_UNITS];
public final int[] y = new int[GAME_UNITS];
final Snake[] snakeArr = new Snake[GAME_UNITS];
Image roundStomachImg;
static int bodyParts = 7;
int applesEaten;
int appleInStomachX = -89;//-89 so that the stomach won't appear on the screen before the snake eats any
int appleInStomachY = -89;
int appleX;
int appleY;
public static char direction = 'R';
public static char prevDirection = 'R';
synchronized public void setDirection(char direction){
    GamePanel.direction = direction;
}
synchronized public char getDirection(){
    return direction;
}
synchronized public void setPrevDirection(char direction){
        GamePanel.direction = direction;
    }
synchronized public char  getPrevDirection(){
        return direction;
    }
boolean running = false;
    String str;
Timer timer;
    Image bgImage;
Random random;
static java.util.List<Thread> threads = new ArrayList<>();
String difficulty;
String playerName;
GamePanel(String difficulty, String playerName){

    roundStomachVerAdrs = roundStomachVerAdrs.replace("file:", "");
    roundStomachVer = new File(roundStomachVerAdrs);
    roundStomachHorizAdrs = roundStomachHorizAdrs.replace("file:", "");
    roundStomachHoriz = new File(roundStomachHorizAdrs);
    for(int i = 0; i < GAME_UNITS; i++){
        snakeArr[i] = new Snake();
    }
    int temp = 2;
    for(int i = 0; i < GAME_UNITS; i++){
        snakeArr[i].bodyPartToShow = temp;
        temp--;
        if(temp == 0){
            temp = 2;
        }
    }
    for(int i = 0; i < GAME_UNITS; i++){
        bp[i] = new bodyPart();
    }
    this.playerName = playerName;
    this.difficulty = difficulty;

    if(difficulty == "Easy"){
        DELAY = 70;
    }else if(difficulty == "Medium"){
        DELAY = 60;
    }else{
        DELAY = 35;
    }

    random = new Random();
    this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
    this.setBackground(Color.black);
    this.setFocusable(true);
    this.addKeyListener(new MyKeyAdapter());
    startGame();
    //textures here

    //
}
public void startGame() {
    bp[0].curDir = direction;
    bp[0].prevDir = prevDirection;
    //music("C:\\Users\\DellG3\\Desktop\\game\\src\\assets\\game_start.wav"); //game start music
    newApple();
    running = true;
    timer = new Timer(DELAY,this);
    timer.start();
}

public static void music(String filePath, int time){
    Thread thread = new Thread(() -> {
        Clip clip = null;
        try {
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            clip = AudioSystem.getClip();
            clip.open(audioStream);
            // Start playing
            if (filePath.contains("menu")) {
                FloatControl gainControl =
                        (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(-20.0f); // Reduce volume by 10 decibels.
            }
            clip.start();

            // Sleep to allow the music to play for some time
            Thread.sleep(time); // Adjust the sleep time as needed

            // Stop the music
            clip.stop();

        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException | InterruptedException e) {
            clip.stop();
        }
    });
    thread.start();
    threads.add(thread);
}
@Override
public void paintComponent(Graphics g) {
    super.paintComponent(g);
    try {
        draw(g);
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
}

public void draw(Graphics g) throws IOException {
        if(running) {
        str = String.valueOf(MainMenu.class.getResource("assets/gameBg.png"));
        str = str.replace("file:","");
        File gameBackground = new File(str);
        bgImage = ImageIO.read(gameBackground);
        g.drawImage(bgImage,0,0,SCREEN_WIDTH,SCREEN_HEIGHT,this);
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream("assets/gameBg.png");
            byte[] imageBytes = inputStream.readAllBytes();
            if(inputStream == null){
                System.out.println(inputStream.readAllBytes());
            }
            //bgImage = Toolkit.getDefaultToolkit().createImage(imageBytes);
            //g.drawImage(bgImage,0,0,SCREEN_WIDTH,SCREEN_HEIGHT,this);
        //
            str = String.valueOf(MainMenu.class.getResource("assets/brick.png"));
            str = str.replace("file:","");
            File brick = new File(str);
            Image brickImage = ImageIO.read(brick);
            for(int i = 0; i < SCREEN_WIDTH; i+=UNIT_SIZE*2) {
                g.drawImage(brickImage, i, SCREEN_HEIGHT - 2* UNIT_SIZE, 2*UNIT_SIZE, 2*UNIT_SIZE, this);
            }
            //
            for(int i = 0; i < SCREEN_WIDTH; i+=UNIT_SIZE*2) {
                g.drawImage(brickImage, i, 0, UNIT_SIZE*2, UNIT_SIZE*2, this);
            }
            //
            for(int i = 0; i < SCREEN_HEIGHT; i+=UNIT_SIZE*2) {
                g.drawImage(brickImage, 0, i, UNIT_SIZE*2, UNIT_SIZE*2, this);
            }
            //
            for(int i = 0; i < SCREEN_HEIGHT; i+=UNIT_SIZE*2) {
                g.drawImage(brickImage, SCREEN_WIDTH - 2*UNIT_SIZE, i, UNIT_SIZE*2, 2*UNIT_SIZE, this);
            }
        //
        //tunnels up
        str = String.valueOf(MainMenu.class.getResource("assets/mushroom.png"));
        str = str.replace("file:","");
        File apple = new File(str);
        Image appleImage = ImageIO.read(apple);
        g.drawImage(appleImage,appleX,appleY,UNIT_SIZE+10,UNIT_SIZE+10,this);
        //
        for(int i = 0; i< bodyParts;i++) {
            // in this part of if code we check if the body of the snake is in the same coordinate snake ate the apple
            //if true, snake's that part of the body becomes oval

            if((bp[i].x == appleInStomachX) && (bp[i].y == appleInStomachY) && (bp[i].curDir == bp[i].prevDir)){
                if((bp[i].curDir == 'U') || (bp[i].curDir == 'D')){
                roundStomachImg = ImageIO.read(roundStomachVer);
                g.drawImage(roundStomachImg, bp[i].x - 4, bp[i].y, UNIT_SIZE+8, UNIT_SIZE, this);
                }
                else{
                    roundStomachImg = ImageIO.read(roundStomachHoriz);
                    g.drawImage(roundStomachImg, bp[i].x, bp[i].y  - 4, UNIT_SIZE, UNIT_SIZE + 8, this);
                }
            }
            else if(i == 0) {
                /*bp[0].curDir = direction;
                bp[0].prevDir = prevDirection;*/
                snake.headHandler(g, bp[0].prevDir, bp[0].curDir, bp, UNIT_SIZE);
                prevDirection = direction;

            }
            else if(i == bodyParts - 1){
                snake.tailHandler(g, bp[bodyParts - 1].prevDir, bp[bodyParts - 1].curDir, bp, UNIT_SIZE);
                bp[bodyParts - 1].prevDir = bp[bodyParts - 1].curDir;
                prevDirection = direction;
            }
            else {
                switch(bp[i].curDir){
                    case 'U':
                        snakeArr[i].uHandler(g, bp[i].prevDir, i, bp, UNIT_SIZE);
                        prevDirection = direction;
                        break;
                    case 'R':
                        snakeArr[i].rHandler(g, bp[i].prevDir, i, bp, UNIT_SIZE);
                        prevDirection = direction;
                        break;
                    case 'L':
                        snakeArr[i].lHandler(g, bp[i].prevDir, i, bp, UNIT_SIZE);
                        prevDirection = direction;
                        break;
                    case 'D':
                        snakeArr[i].dHandler(g, bp[i].prevDir, i, bp, UNIT_SIZE);
                        prevDirection = direction;
                        break;
                }
            }
            if((bp[bodyParts - 1].x == appleInStomachX) && (bp[bodyParts - 1].y == appleInStomachY)){
                appleInStomachX = -1;
                appleInStomachY = -1;
                //skewing coordinates so that after the snake completely passess through a coordinate of an eaten apple
                //it doesn't show the animation for the apple eaten stomach again
            }
        }
            bp[0].prevDir = bp[0].curDir;
        g.setColor(Color.red);
        g.setFont( new Font("Monospaced",Font.BOLD, 40));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString(""+applesEaten, (SCREEN_WIDTH - metrics.stringWidth(""+applesEaten))/2, g.getFont().getSize());

    }
    else {
        gameOver(g);
    }
}
public void gameOver(Graphics g) throws IOException {
    String str = String.valueOf(MainMenu.class.getResource("assets/gameover.wav"));
    str = str.replace("file:","");
    music(str, 5000);
    ImageButton.gamePanelObj.setVisible(false);
    GamePanel.gameOverObj = new gameOverPanel(difficulty, playerName, applesEaten);
    MainMenu.mainMenuFrame.add(GamePanel.gameOverObj, BorderLayout.CENTER);//MainMenu class has a mainMenuFrame static frame, we add to it our GamePanel panel
    GamePanel.gameOverObj.setVisible(true);
    GamePanel.gameOverObj.requestFocus();//we give the focus to GamePanel object gm,
    MainMenu.mainMenuFrame.pack();//fits the frame in panel's size
    GamePanel.bodyParts = 7;

}
public void newApple(){
    appleInStomachX = appleX;
    appleInStomachY = appleY;
    int tempAppleX, tempAppleY;
    boolean goodAppleSpawn = true;
    do {
        goodAppleSpawn = true;
        tempAppleX = random.nextInt((SCREEN_WIDTH - UNIT_SIZE) / UNIT_SIZE) * UNIT_SIZE;
        tempAppleY = random.nextInt((SCREEN_HEIGHT - UNIT_SIZE)/UNIT_SIZE)*UNIT_SIZE;
        for(int i = 0 ; i < GAME_UNITS; i++){
            if((tempAppleX==bp[i].x)&&(tempAppleY == bp[i].y)){
                goodAppleSpawn = false;
                break;
            }
        }

    }while(!goodAppleSpawn);
    appleX = random.nextInt(SCREEN_WIDTH/UNIT_SIZE)*UNIT_SIZE;
    appleY = random.nextInt(SCREEN_HEIGHT/UNIT_SIZE)*UNIT_SIZE;
}
public void move(){
    for(int j = GAME_UNITS - 1; j > 0; j--){
        snakeArr[j].bodyPartInc();
    }
    for(int i = bodyParts;i>0;i--) {
        bp[i].x = bp[i-1].x;
        bp[i].y = bp[i-1].y;
        bp[i].curDir = bp[i-1].curDir;
        bp[i].prevDir = bp[i-1].prevDir;
    }
    switch(direction) {
        case 'U':
            bp[0].y = bp[0].y - UNIT_SIZE;
            break;
        case 'D':
            bp[0].y = bp[0].y + UNIT_SIZE;
            break;
        case 'L':
            bp[0].x = bp[0].x - UNIT_SIZE;
            break;
        case 'R':
            bp[0].x = bp[0].x + UNIT_SIZE;
            break;
    }
}
public void checkApple() {
    if((bp[0].x == appleX) && (bp[0].y == appleY)) {
        String file1 = String.valueOf(MainMenu.class.getResource("assets/eat_apple.wav"));
        file1 = file1.replace("file:","");
        music(file1, 2000);
        bodyParts++;
        applesEaten++;
        newApple();

    }
}
public void checkCollisions() {
    //checks if head collides with body
    for(int i = bodyParts;i>0;i--) {
        if ((bp[0].x == bp[i].x) && (bp[0].y == bp[i].y)) {
            running = false;
            break;
        }
    }
    //check if head touches left border
    if(bp[0].x <0) {
        bp[0].x = SCREEN_WIDTH  - 30;
    }
    //check if head touches right border
    if(bp[0].x > SCREEN_WIDTH - 30) {
        bp[0].x = 0;
    }
    //check if head touches top border
    if(bp[0].y < 0) {
        bp[0].y = SCREEN_HEIGHT  - 30;
    }
    //check if head touches bottom border
    if(bp[0].y > SCREEN_HEIGHT - 30) {
        bp[0].y = 0;
    }

    if(!running) {
        timer.stop();
    }
}
@Override
public void actionPerformed(ActionEvent e) {
    if(running) {
        move();
        checkApple();
        checkCollisions();
    }
    repaint();
}
public class MyKeyAdapter extends KeyAdapter implements Runnable{
    @Override
    public void keyPressed(KeyEvent e) {

        new Thread(() -> {

           synchronized (bp[0]){
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if ((direction != 'R') && (bp[1].x + UNIT_SIZE != bp[0].x)) {
                    setPrevDirection(direction);
                    setDirection('L');
                    bp[0].curDir = direction;
                    bp[0].prevDir = prevDirection;
                }
                break;
            case KeyEvent.VK_RIGHT:
                if ((direction != 'L') && (bp[1].x - UNIT_SIZE != bp[0].x)) {
                    setPrevDirection(direction);
                    setDirection('R');
                    bp[0].curDir = direction;
                    bp[0].prevDir = prevDirection;
                }
                break;
            case KeyEvent.VK_UP:
                if ((direction != 'D') && (bp[1].y + UNIT_SIZE != bp[0].y)) {
                    setPrevDirection(direction);
                    setDirection('U');
                    bp[0].curDir = direction;
                    bp[0].prevDir = prevDirection;
                }
                break;
            case KeyEvent.VK_DOWN:
                if ((direction != 'U') && (bp[1].y - UNIT_SIZE != bp[0].y)) {
                    setPrevDirection(direction);
                    setDirection('D');
                    bp[0].curDir = direction;
                    bp[0].prevDir = prevDirection;
                }
                break;
        }
        try{
           Thread.sleep(DELAY);}
        catch(InterruptedException s){
            s.printStackTrace();
        }
           }
        }).start();
    }
    @Override
    public void run(){

    }
}
public class fuckingKeyBuffer extends Thread{
    fuckingKeyBuffer thread1 = new fuckingKeyBuffer();
    public void executer(){
        thread1.start();
    }
    public void run(){

    }
}
}