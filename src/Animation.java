import java.awt.*;

import static java.lang.Math.abs;

public class Animation {

        /**
         * at first the selected piece move horizontal then vertical step by step
         * @param Board map of the running game
         * @param src source of movement
         * @param des destination of movement
         * @param srcChessMan the pieces that we are moving it
         */
        public static void play(Map Board, Point src, Point des, CHEsMAN srcChessMan) throws InterruptedException {
                Board.map[src.x][src.y] = null;
                int x1 = src.x, y1 = src.y;
                while (abs(des.x - x1) > 0){
                        if(x1 > des.x) x1--;
                        else x1++;
                        CHEsMAN temp = Board.map[x1][y1];
                        Board.map[x1][y1] = srcChessMan;
                        Board.mapButton[x1][y1].repaint();
                        Board.mapButton[x1][y1].revalidate();
                        Thread.sleep(10);
                        Board.map[x1][y1] = temp;
                        Board.mapButton[x1][y1].repaint();
                        Board.mapButton[x1][y1].revalidate();

                }
                while (abs(des.y - y1) > 0){
                        if(y1 > des.y) y1--;
                        else y1++;
                        CHEsMAN temp = Board.map[x1][y1];
                        Board.map[x1][y1] = srcChessMan;
                        Board.mapButton[x1][y1].repaint();
                        Board.mapButton[x1][y1].revalidate();
                        Thread.sleep(10);
                        Board.map[x1][y1] = temp;
                        Board.mapButton[x1][y1].repaint();
                        Board.mapButton[x1][y1].revalidate();
                }
        }
}
