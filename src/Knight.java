import javax.swing.*;

import java.awt.*;

import static java.lang.Math.abs;

public class Knight extends CHEsMAN{
        
        public Knight(String color, int x, int y){
                this.setLoc(x, y);
                this.color = color;
                name = "Knight";
                JLabel j = new JLabel(new ImageIcon("KnightWhite.png"));
                if(color.equals("Black"))
                        j = new JLabel(new ImageIcon("KnightBlack.png"));
                this.setLayout(new BorderLayout());
                this.add(j);
        }
        
        @Override
        public Boolean validMove(int x, int y, Map board) {
                int dx = (int) abs(x - this.loc.getX()), dy = (int) abs(y - this.loc.getY());
                
                return ((dx == 1 && dy == 2) || (dy == 1 && dx == 2)) &&
                        (board.map[x][y] == null || !board.getChessmanColor(x, y).equals(this.getColor()));
        }
}
