import com.sun.tools.javac.Main;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Objects;
import javax.imageio.ImageIO;



public class MainMenu {
    public static MainMenuPanel mm;


   // panel of main menu, also static and can be accessed from any point of program
    public static JFrame mainMenuFrame = new JFrame();// we use the static frame of mainMenu which can be aaccessed from anywhere


    public MainMenu() {
        mainMenuFrame.setSize(900, 600);
        mainMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainMenuFrame.setResizable(false);
        mainMenuFrame.setLocationRelativeTo(null);
        mainMenuFrame.setVisible(true);
        Image icon = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE);
        mainMenuFrame.setIconImage(null);
        mainMenuFrame.setIconImage(icon);

        mm = new MainMenuPanel("Your Name");
        MainMenu.mainMenuFrame.add(mm, BorderLayout.CENTER);
        mm.requestFocus();
        MainMenu.mainMenuFrame.pack();

        // Remove the default Java icon from the title bar

    }



    public static void main(String[] args) {
        // Assuming you are in a class or method


// Use inputStream as needed, e.g., for reading images or other resources

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new MainMenu());

    }

    // Custom renderer for JComboBox

}
