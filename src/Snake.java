import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Snake implements ImageObserver {
    public int bodyPartToShow = 1;
    Image snakeBodyImg;
    String vertBodyAdrs1 = String.valueOf(MainMenu.class.getResource("assets/verticalMidBody1.png"));
    File vertBody1;
    String vertBodyAdrs5 = String.valueOf(MainMenu.class.getResource("assets/verticalMidBody5.png"));
    File vertBody5;
    String horizBodyAdrs1 = String.valueOf(MainMenu.class.getResource("assets/horizontalMidBody1.png"));
    File horizBody1;
    String horizBodyAdrs5 = String.valueOf(MainMenu.class.getResource("assets/horizontalMidBody2.png"));
    File horizBody5;
    String uTailAdrs = String.valueOf(MainMenu.class.getResource("assets/uTail.png"));
    File uTail;
    String dTailAdrs = String.valueOf(MainMenu.class.getResource("assets/dTail.png"));
    File dTail;
    String rTailAdrs = String.valueOf(MainMenu.class.getResource("assets/rTail.png"));
    File rTail;
    String lTailAdrs = String.valueOf(MainMenu.class.getResource("assets/lTail.png"));
    File lTail;

    String uHeadAdrs = String.valueOf(MainMenu.class.getResource("assets/uHead.png"));
    File uHead;
    String dHeadAdrs = String.valueOf(MainMenu.class.getResource("assets/dHead.png"));
    File dHead;
    String rHeadAdrs = String.valueOf(MainMenu.class.getResource("assets/rHead.png"));
    File rHead;
    String lHeadAdrs = String.valueOf(MainMenu.class.getResource("assets/lHead.png"));
    File lHead;

    String turn_dr_lu_adrs = String.valueOf(MainMenu.class.getResource("assets/turn_dr_lu.png"));
    File turn_dr_lu;
    String turn_ru_dl_adrs = String.valueOf(MainMenu.class.getResource("assets/turn_ru_dl.png"));
    File turn_ru_dl;
    String turn_ur_ld_adrs = String.valueOf(MainMenu.class.getResource("assets/turn_ur_ld.png"));
    File turn_ur_ld;
    String turn_ul_rd_adrs = String.valueOf(MainMenu.class.getResource("assets/turn_ul_rd.png"));
    File turn_ul_rd;
    public Snake() {
        vertBodyAdrs1 = vertBodyAdrs1.replace("file:", "");
        vertBody1 = new File(vertBodyAdrs1);
        vertBodyAdrs5 = vertBodyAdrs5.replace("file:", "");
        vertBody5 = new File(vertBodyAdrs5);
        horizBodyAdrs1 = horizBodyAdrs1.replace("file:", "");
        horizBody1 = new File(horizBodyAdrs1);
        horizBodyAdrs5 = horizBodyAdrs5.replace("file:", "");
        horizBody5 = new File(horizBodyAdrs5);
        uTailAdrs = uTailAdrs.replace("file:", "");
        uTail = new File(uTailAdrs);
        dTailAdrs = dTailAdrs.replace("file:", "");
        dTail = new File(dTailAdrs);
        rTailAdrs = rTailAdrs.replace("file:", "");
        rTail = new File(rTailAdrs);
        lTailAdrs = lTailAdrs.replace("file:", "");
        lTail = new File(lTailAdrs);

        uHeadAdrs = uHeadAdrs.replace("file:", "");
        uHead = new File(uHeadAdrs);
        dHeadAdrs = dHeadAdrs.replace("file:", "");
        dHead = new File(dHeadAdrs);
        rHeadAdrs = rHeadAdrs.replace("file:", "");
        rHead = new File(rHeadAdrs);
        lHeadAdrs = lHeadAdrs.replace("file:", "");
        lHead = new File(lHeadAdrs);

        turn_dr_lu_adrs = turn_dr_lu_adrs.replace("file:", "");
        turn_dr_lu = new File(turn_dr_lu_adrs);
        turn_ru_dl_adrs = turn_ru_dl_adrs.replace("file:", "");
        turn_ru_dl = new File(turn_ru_dl_adrs);
        turn_ur_ld_adrs = turn_ur_ld_adrs.replace("file:", "");
        turn_ur_ld = new File(turn_ur_ld_adrs);
        turn_ul_rd_adrs = turn_ul_rd_adrs.replace("file:", "");
        turn_ul_rd = new File(turn_ul_rd_adrs);
    }

    public void bodyPartInc(){
        if(bodyPartToShow == 2){
        bodyPartToShow=1;}
        //else if(bodyPartToShow == 8){
        //    bodyPartToShow = 1;
        //}
        else{
            bodyPartToShow++;
        }
    }
    public void uHandler(Graphics g, char prevDir, int i, bodyPart bp[],int UNIT_SIZE) throws IOException {
        switch (prevDir){
            case 'U':
                    switch (bodyPartToShow){
                        case 1:
                            snakeBodyImg = ImageIO.read(vertBody1);
                            break;
                        case 2:
                            snakeBodyImg = ImageIO.read(vertBody5);
                            break;
                    }
                g.drawImage(snakeBodyImg, bp[i].x - 2, bp[i].y, UNIT_SIZE+4, UNIT_SIZE, this);
                break;
            case 'R':
                snakeBodyImg = ImageIO.read(turn_ru_dl);
                g.drawImage(snakeBodyImg, bp[i].x, bp[i].y, UNIT_SIZE, UNIT_SIZE, this);
                break;
            case 'L':
                snakeBodyImg = ImageIO.read(turn_dr_lu);
                g.drawImage(snakeBodyImg, bp[i].x, bp[i].y, UNIT_SIZE, UNIT_SIZE, this);
                break;
        }
        //1
        if(bp[i].x == bp[i + 1].x){
            switch (bodyPartToShow){
                case 1:
                    snakeBodyImg = ImageIO.read(vertBody1);
                    break;
                case 2:
                    snakeBodyImg = ImageIO.read(vertBody5);
                    break;
            }
            g.drawImage(snakeBodyImg, bp[i].x - 2, bp[i].y, UNIT_SIZE+4, UNIT_SIZE, this);
        }
        else if(bp[i].x > bp[i + 1].x){
            snakeBodyImg = ImageIO.read(turn_ru_dl);
            g.drawImage(snakeBodyImg, bp[i].x, bp[i].y, UNIT_SIZE, UNIT_SIZE, this);
        }
        else{
            snakeBodyImg = ImageIO.read(turn_dr_lu);
            g.drawImage(snakeBodyImg, bp[i].x, bp[i].y, UNIT_SIZE, UNIT_SIZE, this);
        }
        //2
    }
    public void dHandler(Graphics g, char prevDir, int i, bodyPart bp[],int UNIT_SIZE) throws IOException {
        switch (prevDir){
            case 'D':
                    switch (bodyPartToShow){
                        case 1:
                            snakeBodyImg = ImageIO.read(vertBody1);
                            break;
                        case 2:
                            snakeBodyImg = ImageIO.read(vertBody5);
                            break;
                    }
                g.drawImage(snakeBodyImg, bp[i].x - 2, bp[i].y, UNIT_SIZE+4, UNIT_SIZE, this);
                break;
            case 'R':
                snakeBodyImg = ImageIO.read(turn_ul_rd);
                g.drawImage(snakeBodyImg, bp[i].x, bp[i].y, UNIT_SIZE, UNIT_SIZE, this);
                break;
            case 'L':
                snakeBodyImg = ImageIO.read(turn_ur_ld);
                g.drawImage(snakeBodyImg, bp[i].x, bp[i].y, UNIT_SIZE, UNIT_SIZE, this);
                break;
        }
        //1
        if(bp[i].x == bp[i +1].x){
            switch (bodyPartToShow){
                case 1:
                    snakeBodyImg = ImageIO.read(vertBody1);
                    break;
                case 2:
                    snakeBodyImg = ImageIO.read(vertBody5);
                    break;
            }
            g.drawImage(snakeBodyImg, bp[i].x - 2, bp[i].y, UNIT_SIZE+4, UNIT_SIZE, this);
        }
        else if(bp[i].x > bp[i + 1].x){
            snakeBodyImg = ImageIO.read(turn_ul_rd);
            g.drawImage(snakeBodyImg, bp[i].x, bp[i].y, UNIT_SIZE, UNIT_SIZE, this);
        }
        else{
            snakeBodyImg = ImageIO.read(turn_ur_ld);
            g.drawImage(snakeBodyImg, bp[i].x, bp[i].y, UNIT_SIZE, UNIT_SIZE, this);
        }
        //2
    }
    public void rHandler(Graphics g, char prevDir, int i, bodyPart bp[],int UNIT_SIZE) throws IOException{
        switch (prevDir){
            case 'U':
                snakeBodyImg = ImageIO.read(turn_ur_ld);
                g.drawImage(snakeBodyImg, bp[i].x, bp[i].y, UNIT_SIZE, UNIT_SIZE, this);
                break;
            case 'D':
                snakeBodyImg = ImageIO.read(turn_dr_lu);
                g.drawImage(snakeBodyImg, bp[i].x, bp[i].y, UNIT_SIZE, UNIT_SIZE, this);
                break;
            case 'R':
                    switch (bodyPartToShow){
                        case 1:
                            snakeBodyImg = ImageIO.read(horizBody1);
                            break;
                        case 2:
                            snakeBodyImg = ImageIO.read(horizBody5);
                            break;
                    }
                g.drawImage(snakeBodyImg, bp[i].x, bp[i].y-2, UNIT_SIZE, UNIT_SIZE+4, this);
                break;
        }
        //1
        if(bp[i].y == bp[i + 1].y){
            switch (bodyPartToShow){
                case 1:
                    snakeBodyImg = ImageIO.read(horizBody1);
                    break;
                case 2:
                    snakeBodyImg = ImageIO.read(horizBody5);
                    break;
            }
            g.drawImage(snakeBodyImg, bp[i].x, bp[i].y-2, UNIT_SIZE, UNIT_SIZE+4, this);
        }
        else if(bp[i].y > bp[i + 1].y){
            snakeBodyImg = ImageIO.read(turn_dr_lu);
            g.drawImage(snakeBodyImg, bp[i].x, bp[i].y, UNIT_SIZE, UNIT_SIZE, this);
        }
        else{
            snakeBodyImg = ImageIO.read(turn_ur_ld);
            g.drawImage(snakeBodyImg, bp[i].x, bp[i].y, UNIT_SIZE, UNIT_SIZE, this);

        }
        //2
    }
    public void lHandler(Graphics g, char prevDir, int i, bodyPart bp[],int UNIT_SIZE) throws IOException{
        switch (prevDir){
            case 'U':
                snakeBodyImg = ImageIO.read(turn_ul_rd);
                g.drawImage(snakeBodyImg, bp[i].x, bp[i].y, UNIT_SIZE, UNIT_SIZE, this);
                break;
            case 'D':
                snakeBodyImg = ImageIO.read(turn_ru_dl);
                g.drawImage(snakeBodyImg, bp[i].x, bp[i].y, UNIT_SIZE, UNIT_SIZE, this);
                break;
            case 'L':
                switch (bodyPartToShow){
                    case 1:
                        snakeBodyImg = ImageIO.read(horizBody1);
                        break;
                    case 2:
                        snakeBodyImg = ImageIO.read(horizBody5);
                        break;
                }
            g.drawImage(snakeBodyImg, bp[i].x, bp[i].y-2, UNIT_SIZE, UNIT_SIZE+4, this);
                break;
        }
        //1
        if(bp[i].y == bp[i + 1].y){
            switch (bodyPartToShow){
                case 1:
                    snakeBodyImg = ImageIO.read(horizBody1);
                    break;
                case 2:
                    snakeBodyImg = ImageIO.read(horizBody5);
                    break;
            }
            g.drawImage(snakeBodyImg, bp[i].x, bp[i].y-2, UNIT_SIZE, UNIT_SIZE+4, this);
        }
        else if(bp[i].y > bp[i + 1].y){
            snakeBodyImg = ImageIO.read(turn_ru_dl);
            g.drawImage(snakeBodyImg, bp[i].x, bp[i].y, UNIT_SIZE, UNIT_SIZE, this);
        }
        else{
            snakeBodyImg = ImageIO.read(turn_ul_rd);
            g.drawImage(snakeBodyImg, bp[i].x, bp[i].y, UNIT_SIZE, UNIT_SIZE, this);

        }
        //2
    }
public void headHandler(Graphics g, char prevDir, char curDir, bodyPart bp[],int UNIT_SIZE) throws IOException {
    switch (curDir){
        case 'U':
            try {
                snakeBodyImg = ImageIO.read(uHead);
                g.drawImage(snakeBodyImg, bp[0].x - 2, bp[0].y, UNIT_SIZE+4, UNIT_SIZE, this);
            } catch (IOException e) {
                e.printStackTrace();
            }
            break;
        case 'D':
            snakeBodyImg = ImageIO.read(dHead);
            g.drawImage(snakeBodyImg, bp[0].x - 2, bp[0].y, UNIT_SIZE+4, UNIT_SIZE, this);
            break;
        case 'R':
            snakeBodyImg = ImageIO.read(rHead);
            g.drawImage(snakeBodyImg, bp[0].x, bp[0].y-2, UNIT_SIZE, UNIT_SIZE+4, this);
            break;
        case 'L':
            snakeBodyImg = ImageIO.read(lHead);
            g.drawImage(snakeBodyImg, bp[0].x, bp[0].y-2, UNIT_SIZE, UNIT_SIZE+4, this);
            break;
    }
}
public void tailHandler(Graphics g, char prevDir, char curDir, bodyPart bp[],int UNIT_SIZE) throws IOException {
    switch (curDir){
        case 'U':
            snakeBodyImg = ImageIO.read(uTail);
            g.drawImage(snakeBodyImg, bp[GamePanel.bodyParts - 1].x - 2, bp[GamePanel.bodyParts - 1].y, UNIT_SIZE+4, UNIT_SIZE, this);
            break;
        case 'D':
            snakeBodyImg = ImageIO.read(dTail);
            g.drawImage(snakeBodyImg, bp[GamePanel.bodyParts - 1].x - 2, bp[GamePanel.bodyParts - 1].y, UNIT_SIZE+4, UNIT_SIZE, this);
            break;
        case 'R':
            snakeBodyImg = ImageIO.read(rTail);
            g.drawImage(snakeBodyImg, bp[GamePanel.bodyParts - 1].x, bp[GamePanel.bodyParts - 1].y-2, UNIT_SIZE, UNIT_SIZE+4, this);
            break;
        case 'L':
            snakeBodyImg = ImageIO.read(lTail);
            g.drawImage(snakeBodyImg, bp[GamePanel.bodyParts - 1].x, bp[GamePanel.bodyParts - 1].y-2, UNIT_SIZE, UNIT_SIZE+4, this);
            break;
    }
}
    @Override
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
        return false;
    }
}
