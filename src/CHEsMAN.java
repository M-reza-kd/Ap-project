import javax.swing.*;
import java.awt.*;
abstract public class CHEsMAN extends JComponent {
        Point loc = new Point();
        String color;
        JLabel image;
        String name = "";

        /**
         * this function check if this move is valid or not
         * @param x x of destination
         * @param y y of destination
         * @param board board of the game that we are running it
         * @return if we can or can not move to the pointed location
         */
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
