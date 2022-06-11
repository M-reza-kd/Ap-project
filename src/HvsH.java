import javax.swing.*;
import java.io.IOException;

public class HvsH {
        public static void Start() throws IOException {
                Map map = new Map();
                JFrame frame = new JFrame();
                frame.setVisible(true);
                frame.setBounds(100,0,900,800);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(map);
                map.typeOfGame = "Both";
                map.calRobot();
                frame.repaint();
                frame.revalidate();
        }
        
        public static void resume() throws IOException {
                Map map = Load.loadLastGame();
                JFrame frame = new JFrame();
                frame.setVisible(true);
                frame.setBounds(100,0,900,800);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(map);
                map.typeOfGame = "Both";
                map.calRobot();
                frame.repaint();
                frame.revalidate();
        }
}
