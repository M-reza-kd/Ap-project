import javax.swing.*;
import java.awt.*;

abstract public class CHEsMAN extends JComponent {
        
        Point loc = new Point();
        String color;
        
        String name = "";
        
        public void remove(Map board){
                board.map[(int)this.loc.getX()][(int)this.loc.getY()] = null;
        }
        
        abstract public Boolean validMove(int x, int y, Map board);
        
        
        public void setLoc(int x, int y) {
                this.loc =new Point(x, y);
        }
        
        public Point getLoc(){
                return loc;
        }
        
        public void setColor(String color) {
                this.color = color;
        }
        
        public String getColor() {
                return color;
        }
}
