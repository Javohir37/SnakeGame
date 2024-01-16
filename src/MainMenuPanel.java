import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;


class ImageButton extends JButton {

    //File file = new File("");
    public static GamePanel gamePanelObj;
    private BufferedImage backgroundImage;
    private JTextField input;
    public static String difficulty;
    public ImageButton(String text, String imagePath, JTextField inputField, JComboBox<String> select) {
        super(text);
        this.input = inputField;

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
                String file1 = String.valueOf(MainMenu.class.getResource("/assets/menu_select.wav"));
                file1 = file1.replace("file:","");
                GamePanel.music(file1, 5000);
                String playerName = input.getText();
                difficulty = (String) select.getSelectedItem();
                    gamePanelObj = new GamePanel(difficulty, playerName);
                    MainMenu.mainMenuFrame.add(gamePanelObj);//MainMenu class has a mainMenuFrame static frame, we add to it our GamePanel panel
                    gamePanelObj.requestFocus();//we give the focus to GamePanel object gm,
                    MainMenu.mainMenuFrame.pack();//fits the frame in panel's size
                    MainMenu.mm.setVisible(false);

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


public class MainMenuPanel extends JPanel {
    String fontAdrs = String.valueOf(MainMenu.class.getResource("/assets/ARCADECLASSIC.TTF"));
    public static String bgMusicAdrs;

    public static JPanel MainMenuPanel;
    public static JLabel imageHolder;

    private ImageIcon backIcon;
    private JTextField input;
    private JComboBox<String> select;
    private JButton button;
    private static class MyListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            //setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
            component.setComponentOrientation(index == -1? ComponentOrientation.LEFT_TO_RIGHT:(isSelected ? ComponentOrientation.RIGHT_TO_LEFT : ComponentOrientation.LEFT_TO_RIGHT));
            component.setBackground(index == -1? new Color(177,76,48):(isSelected ? new Color(255,252,224): new Color(177,76,48)));
            //component.setComponentOrientation(isSelected ? ComponentOrientation.RIGHT_TO_LEFT : ComponentOrientation.LEFT_TO_RIGHT);
            //component.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

            component.setForeground(isSelected ? new Color(177,76,48): new Color(255,252,224));
            return component;
        }
    }

    MainMenuPanel(String playerName) {
        //

        //
            String file1 = String.valueOf(MainMenu.class.getResource("/assets/bgMusic.wav"));
            file1 = file1.replace("file:","");
            bgMusicAdrs = file1;
            GamePanel.music(bgMusicAdrs, 100000);
            MainMenuPanel = new JPanel();
            this.setPreferredSize(new Dimension(900, 600));
            // Set focus on another component (panel) to avoid initial focus on the input field



            backIcon = new ImageIcon(getClass().getResource("/assets/back.jpg"));
            imageHolder = new JLabel(backIcon);
            imageHolder.setSize(900, 600);

            // input
            input = new JTextField(playerName);
            // Add focus listener to handle placeholder behavior
        //creating custom font

            //lol
            fontAdrs = fontAdrs.replace("file:", "");
            Font customFont = null;
            try {
                    //create the font to use. Specify the size!
                    customFont = Font.createFont(Font.PLAIN, new File(fontAdrs)).deriveFont(30f);
                    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                    //register the font
                    ge.registerFont(customFont);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch(FontFormatException e) {
                    e.printStackTrace();
                }
        //
            // Select

            select = new JComboBox<>(new String[]{"Easy", "Medium", "Hard"});
            select.setFont(customFont);
            select.setRenderer(new MyListCellRenderer());
            // Set white background and black foreground for JComboBox
            select.setOpaque(true);
            select.setBackground(new Color(255,252,224));
            select.setForeground(new Color(177,76,48));
            select.setBounds(410, 200, 200, 40);
            select.setToolTipText("Select the difficulty");
        //
            // Create new button with image background and click event listener
            button = new ImageButton("", "/assets/buttonBg.png", input, select);
            button.setBounds(410, 260, 200, 60);
            // panel
            //imageHolder.add(input);
            imageHolder.add(select);
            imageHolder.add(button);
            MainMenuPanel.add(imageHolder);
            // Add the image label to the layered pane at the bottom layer
            add(MainMenuPanel, BorderLayout.CENTER);

            // Add the layered pane to the center of the frame


    }

}
