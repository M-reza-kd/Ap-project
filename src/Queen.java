import javax.swing.*;
import java.awt.*;

public class Queen extends CHEsMAN{
        public Queen(String color, int x, int y){
                this.setLoc(x, y);
                this.color = color;
                name = "Queen";
                image = new JLabel(new ImageIcon("QueenWhite.png"));
                if(color.equals("Black"))
                        image = new JLabel(new ImageIcon("QueenBlack.png"));
                this.setLayout(new BorderLayout());
                this.add(image);
        }
        
        @Override
        public Boolean validMove(int x, int y, Map board) {
                int x1 = (int)this.loc.getX(), y1 = (int)this.loc.getY();
                int Mx = 1, My = 1;
                
                if(x < x1) Mx *= -1;
                if(x == x1) Mx = 0;
                if(y < y1) My *= -1;
                if(y == y1) My = 0;
                
                while (x1 != x || y1 != y){
                        x1 += Mx;
                        y1 += My;
                        if (x1 >= 8 || x1 < 0 || y1 >= 8 || y1 < 0)
                                return false;
                        if(board.map[x1][y1] != null && (x1 != x || y1 != y))
                                return false;
                }
                return board.map[x][y] == null || !board.getChessmanColor(x, y).equals(this.getColor());
        }
}
