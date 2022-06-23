import javax.swing.*;
import java.awt.*;

import static java.lang.Math.abs;

public class King extends CHEsMAN{
        public King(String color, int x, int y){
                this.color = color;
                this.setLoc(x, y);
                name = "King";
                image = new JLabel(new ImageIcon("KingWhite.png"));
                if(color.equals("Black"))
                        image = new JLabel(new ImageIcon("KingBlack.png"));
                this.setLayout(new BorderLayout());
                this.add(image);
        }
        
        @Override
        public Boolean validMove(int x, int y, Map board) {
                int dx = (int) abs(x - this.loc.getX()), dy = (int) abs(y - this.loc.getY());
                if(board.map[x][y] != null && board.getChessmanColor(x, y).equals(this.getColor()))
                        return false;
                return (dx <= 1 && dy <= 1 &&
                       (board.map[x][y] == null || !board.getChessmanColor(x, y).equals(this.getColor())));
        }
}
