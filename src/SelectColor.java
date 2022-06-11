import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class SelectColor {
        public static void run() throws IOException {
        
                JFrame frame = new JFrame();
                JPanel panel = new JPanel();
                frame.setLayout(null);
                frame.setVisible(true);
                JLabel text = new JLabel("SELECT COLOR");
                frame.setBounds(200,200,300,400);
                text.setBounds(70,10,180, 80);
                text.setFont(new Font(Font.SANS_SERIF, 1, 20));
                panel.setBackground(new Color(164, 104, 104));
                panel.setLayout(null);
                panel.setBounds(0,0,300,400);
                JButton white = new JButton("WHITE");
                JButton black = new JButton("BLACK");
                white.setBounds(300,20,40,20);
                panel.add(white);
                panel.add(text);
                frame.add(panel);
                frame.revalidate();
                String whosHuman = "";
                HvsC.Start(whosHuman);
        }
}
