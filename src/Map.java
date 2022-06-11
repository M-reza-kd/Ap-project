import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import static java.lang.Math.abs;

public class Map extends JComponent {
        CHEsMAN [][] map = new CHEsMAN[8][8];
        int cnt = 0;
        int[][] board = new int[8][8];
        JPanel blackOut = new JPanel(new GridLayout(8,2));
        JPanel whiteOut = new JPanel(new GridLayout(8,2));
        JPanel buttonPanel = new JPanel();
        JPanel WHOSTURNE = new JPanel(new BorderLayout());
        JLabel turn, wl, bl;
        Point src, des;
        JButton [][] mapButton;
        String typeOfGame;
        String robotColor;
        ActionListener mainActionListener;
        
        JPanel panel = new JPanel(new GridLayout(8,8));
        Font font;
        static final Color WHITE = new Color(243, 198, 89);
        static final Color BLACK = new Color(114, 67, 56);
        static final int[] cntChessman = {0, 1, 1, 2, 2, 2, 8};
        
        private void preProcessOfMap(){
                
                this.setBounds(100,0, 900,800);
                des = src = null;
                WHOSTURNE.setBounds(0,0,885,80);
                WHOSTURNE.setBackground(new Color(248, 218, 159));
                WHOSTURNE.setLayout(null);
                bl = new JLabel("BLACK OUT");
                wl = new JLabel("WHITE OUT");
                turn = new JLabel("White");
                turn.setBounds(375,0,200,100);
                wl.setBounds(10,0,150,100);
                bl.setBounds(760,0,150,100);
                WHOSTURNE.add(turn);
                WHOSTURNE.add(bl);
                WHOSTURNE.add(wl);
                WHOSTURNE.revalidate();
                bl.setFont(new Font(Font.SANS_SERIF, 1, 20));
                wl.setFont(new Font(Font.SANS_SERIF, 1, 20));
                font = new Font("SansSerif", 3, 50);
                turn.setFont(font);
                this.setLayout(null);
                this.add(whiteOut);
                whiteOut.setBounds(0,80,140,700);
                blackOut.setBounds(740,80,145,700);
                whiteOut.setBackground(new Color(248, 218, 159));
                blackOut.setBackground(new Color(248, 218, 159));
                this.add(blackOut);
                this.add(WHOSTURNE);
                mapButton = new JButton[8][8];
                JButton exit = new JButton("Exit");
                JButton save = new JButton("Save");
                JButton ok = new JButton("Ok");
                ActionListener exitActionListener = new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                System.exit(0);
                        }
                };
                exit.addActionListener(exitActionListener);
                ActionListener saveActionListener = new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                try {
                                        Load.save(board, turn.getText(), typeOfGame);
                                } catch (FileNotFoundException ex) {
                                        throw new RuntimeException(ex);
                                }
                        }
                };
                ActionListener okActionListener = new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                move();
                        }
                };
                buttonPanel = new JPanel();
                save.addActionListener(saveActionListener);
                exit.setBounds(175,30,75,40);
                ok.setBounds(200, 30, 25, 40);
                save.setBounds(350,30,75,40);
                buttonPanel.add(save);
                buttonPanel.add(exit);
                buttonPanel.add(ok);
                buttonPanel.setLayout(null);
                buttonPanel.setBounds(140,680,600,100);
                buttonPanel.setBackground(new Color(248, 218, 159));
                this.add(buttonPanel);
                for(int i = 0 ; i < 8; i++)
                        for(int j = 0 ; j < 8; j++) {
                                mapButton[i][j] = new JButton();
                                if (i % 2 == j % 2)
                                        mapButton[i][j].setBackground(WHITE);
                                else
                                        mapButton[i][j].setBackground(BLACK);
                                panel.add(mapButton[i][j]);
                        }
                
                panel.setBounds(140,80,600,600);
                this.add(panel);
                buttonPanel.revalidate();
                this.revalidate();
                mainActionListener = e -> {
                        JButton b = (JButton) e.getSource();
                        Point X = new Point();
                        for(int i = 0; i < 8; i++)
                                for(int j = 0; j < 8; j++)
                                        if(mapButton[i][j] == b){
                                                X.setLocation(i,j);
                                                break;
                                        }
                        if(src == null && !turn.getText().equals(map[X.x][X.y].getColor()))
                                return;
                        if(src == null  && (typeOfGame.equals("Black") && map[X.x][X.y].getColor().equals("White")))
                                return;
                        if(src == null  && (typeOfGame.equals("White") && map[X.x][X.y].getColor().equals("Black")))
                                return;
                        b.setBackground(new Color(72, 83, 155));
                        if(src != null && src.equals(X)) {
                                src = null;
                                mainColor();
                                return;
                        }
                        if(src == null) {
                                src = new Point(X);
                                for(int i = 0; i < 8 ; i++)
                                        for(int j = 0; j < 8 ; j++)
                                                if(!src.equals(new Point(i,j)) && map[src.x][src.y].validMove(i , j, Map.this))
                                                        mapButton[i][j].setBackground(new Color(13, 136, 73, 169));
                        }
                        else if(des != null){
                                if(des.x % 2 == des.y % 2)
                                        mapButton[des.x][des.y].setBackground(WHITE);
                                else
                                        mapButton[des.x][des.y].setBackground(BLACK);
                                des.setLocation(X);
                        }
                        else
                                des = new Point(X);
                        if(des != null && src != null){
                                if(des.equals(src) || !map[src.x][src.y].validMove(des.x, des.y, Map.this))
                                        throw new IllegalArgumentException("Destination is not valid");
                                else
                                        move();
                        }
                
                };
                addActionListenerToChessman();
        }
        
        public Map(int[][] board, int[] whiteChessman, int[] blackChessman, String whosTurn) throws IOException {
                turn = new JLabel(whosTurn);
                preProcessOfMap();
                for(int i = 1 ; i < 6; i++){
                        for(int j = 0 ; j < 8 ; j++){
                                String color = "White";
                                if(board[i][j] < 0)
                                        color = "Black";
                                switch (abs(board[i][j])) {
                                        case 1 -> this.map[i][j] = new King(color, i, j);
                                        case 2 -> this.map[i][j] = new Queen(color, i, j);
                                        case 3 -> this.map[i][j] = new Rook(color, i, j);
                                        case 4 -> this.map[i][j] = new Bishop(color, i, j);
                                        case 5 -> this.map[i][j] = new Knight(color, i, j);
                                        default -> this.map[i][j] = new Pawn(color, i, j);
                                }
                                this.mapButton[i][j].add(this.map[i][j]);
                        }
                }
                
                for(int i = 1; i <= 6; i++){
                        CHEsMAN temp = new Pawn("White", 0, 0);
                        switch (i) {
                                case 1 -> temp = new King("White" , 0, 0);
                                case 2 -> temp = new Queen("White" , 0, 0);
                                case 3 -> temp = new Rook("White" , 0, 0);
                                case 4 -> temp = new Bishop("White" , 0, 0);
                                case 5 -> temp = new Knight("White" , 0, 0);
                                case 6 -> temp = new Pawn("White" , 0, 0);
                        }
                        for(int j =0; j < cntChessman[i] - whiteChessman[i]; j++)
                                whiteOut.add(temp);
                }
                for(int i = 1; i <= 6; i++){
                        CHEsMAN temp = new Pawn("Black", 0, 0);
                        switch (i) {
                                case 1 -> temp = new King("Black" , 0, 0);
                                case 2 -> temp = new Queen("Black" , 0, 0);
                                case 3 -> temp = new Rook("Black" , 0, 0);
                                case 4 -> temp = new Bishop("Black" , 0, 0);
                                case 5 -> temp = new Knight("Black" , 0, 0);
                                case 6 -> temp = new Pawn("Black" , 0, 0);
                        }
                        for(int j =0; j < cntChessman[i] - blackChessman[i]; j++)
                                blackOut.add(temp);
                }
        }
        
        public Map() throws IOException {
                preProcessOfMap();
                typeOfGame = "Both";
                for(int j = 0 ; j < 8 ; j++){
                        this.map[1][j] = new Pawn("Black", 1, j);
                        this.mapButton[1][j].add(this.map[1][j]);
                        this.map[6][j] = new Pawn("White", 6, j);
                        this.mapButton[6][j].add(this.map[6][j]);
                }
                
                //ROOK
                this.map[0][0] = new Rook("Black", 0, 0);
                this.map[0][7] = new Rook("Black", 0, 7);
                this.mapButton[0][0].add(this.map[0][0]);
                this.mapButton[0][7].add(this.map[0][7]);
                
                this.map[7][0] = new Rook("White", 7, 0);
                this.map[7][7] = new Rook("White", 7, 7);
                this.mapButton[7][0].add(this.map[7][0]);
                this.mapButton[7][7].add(this.map[7][7]);
                
                //Knight
                this.map[0][1] = new Knight("Black", 0, 1);
                this.map[0][6] = new Knight("Black", 0, 6);
                this.mapButton[0][1].add(this.map[0][1]);
                this.mapButton[0][6].add(this.map[0][6]);
                
                this.map[7][1] = new Knight("White", 7, 1);
                this.map[7][6] = new Knight("White", 7, 6);
                this.mapButton[7][1].add(this.map[7][1]);
                this.mapButton[7][6].add(this.map[7][6]);
                
                //Bishop
                this.map[0][2] = new Bishop("Black", 0, 2);
                this.map[0][5] = new Bishop("Black", 0, 5);
                this.mapButton[0][2].add(this.map[0][2]);
                this.mapButton[0][5].add(this.map[0][5]);
                
                this.map[7][2] = new Bishop("White", 7, 2);
                this.map[7][5] = new Bishop("White", 7, 5);
                this.mapButton[7][2].add(this.map[7][2]);
                this.mapButton[7][5].add(this.map[7][5]);
                
                //King
                this.map[0][3] = new King("Black", 0, 3);
                this.mapButton[0][3].add(this.map[0][3]);
                
                this.map[7][3] = new King("White", 7, 3);
                this.mapButton[7][3].add(this.map[7][3]);
                
                //Queen
                this.map[0][4] = new Queen("Black", 0, 4);
                this.mapButton[0][4].add(this.map[0][4]);
                
                this.map[7][4] = new Queen("White", 7, 4);
                this.mapButton[7][4].add(this.map[7][4]);
        
                for(int i = 0 ; i < 8; i++){
                        for(int j = 0; j < 8; j++){
                                if(map[i][j] == null)
                                        continue;
                                switch (map[i][j].name) {
                                        case "Pawn" ->
                                                board[i][j] = (map[i][j].color.equals("Black") ? -1 * Chessman.PAWN : Chessman.PAWN);
                                        case "King" ->
                                                board[i][j] = (map[i][j].color.equals("Black") ? -1 * Chessman.KING : Chessman.KING);
                                        case "Queen" ->
                                                board[i][j] = (map[i][j].color.equals("Black") ? -1 * Chessman.QUEEN : Chessman.QUEEN);
                                        case "Bishop" ->
                                                board[i][j] = (map[i][j].color.equals("Black") ? -1 * Chessman.BISHOP : Chessman.BISHOP);
                                        case "Rook" ->
                                                board[i][j] = (map[i][j].color.equals("Black") ? -1 * Chessman.ROOK : Chessman.ROOK);
                                        case "Knight" ->
                                                board[i][j] = (map[i][j].color.equals("Black") ? -1 * Chessman.KNIGHT : Chessman.KNIGHT);
                                }
                        }
                }
                this.revalidate();
        }
        
        public void calRobot(){
                if(!typeOfGame.equals("Both"))
                        if(!typeOfGame.equals("White"))
                                robotColor = "White";
                        else
                                robotColor = "Black";
        }
        
        public void addActionListenerToChessman(){
                for(int i = 0 ; i < 8; i++)
                        for(int j = 0 ; j < 8; j++)
                                mapButton[i][j].addActionListener(mainActionListener);
        }
        
        public String getChessmanColor(int x, int y) {
                return map[x][y].getColor();
        }
        
        public void move(){
                System.out.println(src + " " + des);
                if(map[des.x][des.y] != null) {
                        if (map[des.x][des.y].getColor().equals("Black"))
                                blackOut.add(map[des.x][des.y]);
                        else
                                whiteOut.add(map[des.x][des.y]);
                        this.revalidate();
                        if(map[des.x][des.y] != null && map[des.x][des.y].name.equals("King")){
                                JLabel jt = new JLabel();
                                jt.setText(map[des.x][des.y].getColor() + " LOSE...");
                                jt.setFont(font);
                                jt.setBounds(140,80,600,600);
                                panel.setVisible(false);
                                MainPage.mainFrame.setVisible(true);
                                this.add(jt);
                        }
                        map[des.x][des.y] = null;
                }
                map[des.x][des.y] = map[src.x][src.y];
                map[src.x][src.y] = null;
                mapButton[des.x][des.y].add(map[des.x][des.y]);
                map[des.x][des.y].setLoc(des.x, des.y);
                board[des.x][des.y] = board[src.x][src.y];
                board[src.x][src.y] = 0;
                mainColor();
                des = src = null;
                changeTurn();
                if(!typeOfGame.equals("Both") && !turn.getText().equals(typeOfGame))
                        AIPlayer.nextMove(this, getBoard(), robotColor.equals("White"));
        }
        
        private void mainColor(){
                for(int i = 0; i < 8; i++)
                        for(int j = 0; j < 8; j++)
                                if(i % 2 == j % 2)
                                        mapButton[i][j].setBackground(WHITE);
                                else
                                        mapButton[i][j].setBackground(BLACK);
        }
        
        public void changeTurn(){
                if(turn.getText().equals("Black"))
                        turn.setText("White");
                else
                        turn.setText("Black");
        }
        
        public int[][] getBoard(){
                return board;
        }
}
