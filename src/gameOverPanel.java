import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class gameOverPanel extends JPanel{
    public String difficulty;
    public String playerName;
    private JButton restartButton;
    private JScrollPane scrollPane;
    public static JLabel imageHolder;
    private JButton mainMenuButton;

    private ImageIcon backIcon;

    class ImageButton1 extends JButton {
        private BufferedImage backgroundImage;
        private JTextField input;


        public ImageButton1(String text, String imagePath, String buttonType) {
            super(text);
            try {
                URL imageURL = getClass().getResource(imagePath);
                backgroundImage = ImageIO.read(imageURL);
            } catch (IOException e) {
                e.printStackTrace();
            }
            setContentAreaFilled(false);
            setFocusPainted(false);
            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String str = String.valueOf(MainMenu.class.getResource("assets/menu_select.wav"));
                    str = str.replace("file:","");
                    GamePanel.music(str, 5000);
                    if(buttonType == "restartButton"){
                            GamePanel.gameOverObj.setVisible(false);
                            gameOverPanel.imageHolder.setVisible(false);
                            restartButton.setVisible(false);
                            mainMenuButton.setVisible(false);
                            scrollPane.setVisible(false);
                            setVisible(false);
                            ImageButton.gamePanelObj = new GamePanel(difficulty, playerName);
                            MainMenu.mainMenuFrame.add(ImageButton.gamePanelObj);//MainMenu class has a mainMenuFrame static frame, we add to it our GamePanel panel
                            ImageButton.gamePanelObj.requestFocus();//we give the focus to GamePanel object gpObjInGop,
                            MainMenu.mainMenuFrame.pack();//fits the frame in panel's size
                        }else{
                        for (Thread thread : GamePanel.threads) {
                            thread.interrupt();
                        }
                            GamePanel.gameOverObj.setVisible(false);
                            gameOverPanel.imageHolder.setVisible(false);
                            restartButton.setVisible(false);
                            mainMenuButton.setVisible(false);
                            scrollPane.setVisible(false);
                            setVisible(false);
                            MainMenu.mm = new MainMenuPanel("Your Name");
                            MainMenu.mainMenuFrame.add(MainMenu.mm, BorderLayout.CENTER);//MainMenu class has a mainMenuFrame static frame, we add to it our GamePanel panel
                            MainMenu.mm.requestFocus();//we give the focus to GamePanel object gm,//the old panel which stored the background photo and other objects is now unvisible
                            MainMenu.mainMenuFrame.pack();//fits the frame in panel's size

                        }

                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Draw background image
            if (backgroundImage != null) {
                g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }

            // Draw button content
            super.paintComponent(g);
            g2d.dispose();
        }
    }

    public gameOverPanel(String difficulty, String playerName, Integer score){

        this.difficulty = difficulty;
        this.playerName = playerName;
        this.setPreferredSize(new Dimension(900, 600));
        this.setFocusable(true);

        backIcon = new ImageIcon(getClass().getResource("assets/back.jpg"));
        imageHolder = new JLabel(backIcon);
        imageHolder.setSize(900, 600);

        Object[][] data = {
                {playerName, score, difficulty},
        };

        // Column names
        String[] columnNames = {"Name", "Score", "Difficulty"};

        // Create the table
        JTable table = new JTable(data, columnNames);
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(360, 100, 300, 200);

        restartButton = new ImageButton1("", "assets/buttonRestartBg.jpg", "restartButton");
        restartButton.setFont(new Font("Calibri",Font.PLAIN, 34));
        restartButton.setForeground(new Color(212, 77, 77));
        restartButton.setBounds(410, 320, 200, 60);

        mainMenuButton = new ImageButton1("", "assets/buttonMainMenuBg.jpg", "mainMenuButton");
        mainMenuButton.setFont(new Font("Calibri",Font.PLAIN, 34));
        mainMenuButton.setForeground(new Color(212, 77, 77));
        mainMenuButton.setBounds(410, 400, 200, 60);


        imageHolder.add(restartButton);
        imageHolder.add(mainMenuButton);
        //imageHolder.add(scrollPane);
        this.add(imageHolder, BorderLayout.CENTER);
    }
}
