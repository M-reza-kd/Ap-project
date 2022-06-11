import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.lang.Math.abs;

public class Pawn extends CHEsMAN{
        
        public Pawn(String color, int x, int y) throws IOException {
                this.color = color;
                this.setLoc(x, y);
                name = "Pawn";
                JLabel j = new JLabel(new ImageIcon("PawnWhite.png"));
                if(color.equals("Black"))
                        j = new JLabel(new ImageIcon("PawnBlack.png"));
                this.setLayout(new BorderLayout());
                this.add(j);
        }
        
        @Override
        public Boolean validMove(int x, int y, Map board) {
                int dx = (int)(x - this.loc.getX()), dy = (int)(y - this.loc.getY());
                //System.out.println(loc.getX() + " " + loc.getY() + " " + board.getChessmanColor((int)this.loc.getX(), (int)this.loc.getY()));
                if(board.getChessmanColor(loc.x, loc.y).equals("White")) {
                        //System.out.println(x + " " + y + " " + dx + " " + dy + " " + board.map[x][y]);
                        if (dy == 0 && dx == -1 && board.map[x][y] == null)
                                return true;
                        if (dy == 0 && dx == -2 && board.map[x][y] == null && x == 4)
                                return true;
                        if (abs(dy) == 1 && dx == -1 && board.map[x][y] != null && board.map[x][y].getColor().equals("Black"))
                                return true;
                }
                if(board.getChessmanColor(loc.x, loc.y).equals("Black")) {
                        if (dy == 0 && dx == 1 && board.map[x][y] == null)
                                return true;
                        if (dy == 0 && dx == 2 && board.map[x][y] == null && x == 3)
                                return true;
                        if (abs(dy) == 1 && dx == 1 && board.map[x][y] != null && board.map[x][y].getColor().equals("White"))
                                return true;
                }
                return false;
        }
}
