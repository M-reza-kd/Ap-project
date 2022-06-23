import javax.swing.*;
import java.awt.*;

abstract public class CHEsMAN extends JComponent {
        
        Point loc = new Point();
        String color;
        JLabel image;
        String name = "";

        abstract public Boolean validMove(int x, int y, Map board);

        public void setLoc(int x, int y) {
                this.loc =new Point(x, y);
        }

        public void setColor(String color) {
                this.color = color;
        }
        
        public String getColor() {
                return color;
        }
}
