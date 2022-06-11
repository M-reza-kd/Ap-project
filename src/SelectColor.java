import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
                text.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
                panel.setBackground(new Color(164, 104, 104));
                panel.setLayout(null);
                panel.setBounds(0,0,300,400);
                JButton white = new JButton("White");
                JButton black = new JButton("Black");
                ActionListener selectColorButtonActionlistener = e -> {
                        try {
                                Game game = new Game(e.getActionCommand());
                                game.run();
                        } catch (IOException ex) {
                                throw new RuntimeException(ex);
                        }
                };

                white.addActionListener(selectColorButtonActionlistener);
                black.addActionListener(selectColorButtonActionlistener);

                white.setBounds(300,20,40,20);
                panel.add(white);
                panel.add(black);
                panel.add(text);
                frame.add(panel);
                frame.revalidate();
        }
}
