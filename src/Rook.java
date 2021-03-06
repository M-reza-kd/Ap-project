import javax.swing.*;
import java.awt.*;

import static java.lang.Math.abs;

public class Rook extends CHEsMAN{
        /**
         * constructor of the bishop piece
         * @param color color of the piece
         * @param x x of the loc
         * @param y y of the loc
         */
        public Rook(String color, int x, int y){
                this.color = color;
                name = "Rook";
                this.setLoc(x, y);
                image = new JLabel(new ImageIcon("RookWhite.png"));
                if(color.equals("Black"))
                        image = new JLabel(new ImageIcon("RookBlack.png"));
                this.setLayout(new BorderLayout());
                this.add(image);
        }
        
        @Override
        public Boolean validMove(int x, int y, Map board) {
                int dx = (int) abs(x - this.loc.getX()), dy = (int) abs(y - this.loc.getY());
                if(dx * dy != 0)
                        return false;
        
                int x1 = (int)this.loc.getX(), y1 = (int)this.loc.getY();
                int Mx = 1, My = 1;
        
                if(x < x1) Mx *= -1;
                if(x == x1) Mx = 0;
                if(y < y1) My *= -1;
                if(y == y1) My = 0;
        
                while (x1 != x || y1 != y){
                        if (x1 > 8 || x1 < 0 || y1 > 8 || y1 < 0)
                                return false;
                        x1 += Mx;
                        y1 += My;
                        if(board.map[x1][y1] != null && (x1 != x || y1 != y))
                                return false;
                }
                return board.map[x][y] == null || !board.getChessmanColor(x, y).equals(this.getColor());
        }
}
