import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class SelectColor {
        public static void run() throws IOException {

                JFrame frame = new JFrame();
                frame.setLayout(null);
                frame.setVisible(true);
                frame.setBounds(200,200,300,400);

                JPanel panel = new JPanel();
                panel.setLayout(null);
                panel.setBackground(new Color(253, 211, 0));
                panel.setBounds(0,0,300,400);

                JLabel text = new JLabel("SELECT COLOR");
                text.setBounds(70,10,180, 80);
                text.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));

                JButton white = new JButton("White");
                JButton black = new JButton("Black");

                ActionListener selectColorButtonActionlistener = e -> {
                        try {
                                System.out.println(e.getActionCommand());
                                Game game = new Game(e.getActionCommand());
                                game.run();
                        } catch (IOException | InterruptedException ex) {
                                throw new RuntimeException(ex);
                        }
                };

                white.addActionListener(selectColorButtonActionlistener);
                black.addActionListener(selectColorButtonActionlistener);

                white.setBounds(40,250,100,40);
                black.setBounds(170,250,100,40);
                white.setFocusable(false);
                black.setFocusable(false);

                panel.add(white);
                panel.add(black);
                panel.add(text);
                frame.add(panel);
                frame.revalidate();
        }
}
