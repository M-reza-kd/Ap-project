import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class Load {
        
        public static void loadLastGame() throws IOException {
                int[][] board = new int[8][8];
                File file = new File("source.txt");
                Scanner input = new Scanner(file);
                String type = input.next();
                String whosTurn = input.next();
                int[] whiteChessman = new int[7], blackChessman = new int[7];
        
                for(int i = 1 ; i <= 6; i++){
                        whiteChessman[i] = input.nextInt();
                        for(int j = 0; j < whiteChessman[i]; j++) {
                                int x = input.nextInt(), y = input.nextInt();
                                board[x][y] = i;
                        }
                }
                for(int i = 1 ; i <= 6; i++){
                        blackChessman[i] = input.nextInt();
                        for(int j = 0; j < blackChessman[i]; j++) {
                                int x = input.nextInt(), y = input.nextInt();
                                board[x][y] = -i;
                        }
                }
                
                for(int i = 0 ; i < 8; i ++) {
                        for (int j = 0; j < 8; j++)
                                System.out.printf("%d " , board[i][j]);
                        System.out.println();
                }

                Game game = new Game(new Map(board, whiteChessman, blackChessman, whosTurn), type);
                game.run();
        }
        
        public static void save(int[][] board, String whosTurn, String type) throws FileNotFoundException {
                File file = new File("source.txt");
                PrintStream output = new PrintStream(file);
                int[] whiteChessman = new int[7], blackChessman = new int[7];
                Point[][] whiteChessmanArrayList = new Point[7][10];
                Point[][] blackChessmanArrayList = new Point[7][10];
                int[] whiteLastIndex = new int[7];
                int[] blackLastIndex = new int[7];
                for(int i = 0; i < 7 ; i++)
                        whiteLastIndex[i] = blackLastIndex[i] = 0;
                for(int i = 0; i < 8; i++)
                        for(int j = 0; j < 8; j++)
                                if(board[i][j] > 0) {
                                        whiteChessmanArrayList[board[i][j]][whiteLastIndex[board[i][j]]] = (new Point(i , j));
                                        whiteLastIndex[board[i][j]]++;
                                        whiteChessman[board[i][j]]++;
                                }
                                else if(board[i][j] < 0){
                                        blackChessmanArrayList[-board[i][j]][blackLastIndex[-board[i][j]]] = (new Point(i , j));
                                        blackLastIndex[-board[i][j]]++;
                                        blackChessman[-board[i][j]]++;
                                }
                output.println(type);
                output.println(whosTurn);
                for(int i = 1 ; i <= 6; i++){
                        output.println(whiteChessman[i]);
                        for(Point p : whiteChessmanArrayList[i])
                                if(p != null)
                                        output.println(p.x + " " + p.y);
                }
                for(int i = 1 ; i <= 6; i++){
                        output.println(blackChessman[i]);
                        for(Point p : blackChessmanArrayList[i])
                                if(p != null)
                                        output.println(p.x + " " + p.y);
                }
                output.close();
        }
}
