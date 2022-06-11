import java.awt.*;

public class AIPlayer{

        public static void nextMove(Map map, int[][] chess, boolean white) {
                MiniMax.MiniMaxState miniMaxState = MiniMax.miniMax(chess, white, 4);
                int[][] newChess = miniMaxState.getChess();
                for(int i = 0 ; i < 8; i++){
                        for(int j = 0 ; j < 8; j++){
                                if(chess[i][j] != 0 && newChess[i][j] == 0)
                                        map.src = new Point(i , j);
                                if(newChess[i][j] != 0 && newChess[i][j] != chess[i][j])
                                        map.des = new Point(i , j);
                        }
                }
                map.move();
        }

}