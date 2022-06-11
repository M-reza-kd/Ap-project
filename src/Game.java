import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Game {

        JFrame frame;
        Map Board;

        String typeOfGame;
        String robotColor;
        Point src, des;
        static ActionListener mainActionListener;

        public Game(String type) throws IOException {
                typeOfGame = type;
                Board = new Map();
                addActionListenerToChessman();
        }

        public Game(Map m, String type){
                typeOfGame = type;
                Board = m;
                addActionListenerToChessman();
        }
        public void addActionListenerToChessman(){
                mainActionListener = e -> {
                        JButton b = (JButton) e.getSource();
                        Point X = new Point();
                        for(int i = 0; i < 8; i++)
                                for(int j = 0; j < 8; j++)
                                        if(Board.mapButton[i][j] == b){
                                                X.setLocation(i,j);
                                                break;
                                        }
                        if(src == null && !Board.turn.getText().equals(Board.map[X.x][X.y].getColor()))
                                return;
                        if(src == null  && (typeOfGame.equals("Black") && Board.map[X.x][X.y].getColor().equals("White")))
                                return;
                        if(src == null  && (typeOfGame.equals("White") && Board.map[X.x][X.y].getColor().equals("Black")))
                                return;
                        b.setBackground(new Color(72, 83, 155));
                        if(src != null && src.equals(X)) {
                                src = null;
                                Board.mainColor();
                                return;
                        }
                        if(src == null) {
                                src = new Point(X);
                                for(int i = 0; i < 8 ; i++)
                                        for(int j = 0; j < 8 ; j++)
                                                if(!src.equals(new Point(i,j)) && Board.map[src.x][src.y].validMove(i , j, Board))
                                                        Board.mapButton[i][j].setBackground(new Color(13, 136, 73, 169));
                        }
                        else if(des != null){
                                if(des.x % 2 == des.y % 2)
                                        Board.mapButton[des.x][des.y].setBackground(Map.WHITE);
                                else
                                        Board.mapButton[des.x][des.y].setBackground(Map.BLACK);
                                des.setLocation(X);
                        }
                        else
                                des = new Point(X);
                        if(des != null && src != null){
                                if(des.equals(src) || !Board.map[src.x][src.y].validMove(des.x, des.y, Board))
                                        throw new IllegalArgumentException("Destination is not valid");
                                else
                                        HMPlayer.nextMove(this);
                        }

                };
                for(int i = 0 ; i < 8; i++)
                        for(int j = 0 ; j < 8; j++)
                                Board.mapButton[i][j].addActionListener(mainActionListener);

                JButton exit = new JButton("Exit");
                JButton save = new JButton("Save");
                ActionListener exitActionListener = e -> System.exit(0);
                exit.addActionListener(exitActionListener);
                ActionListener saveActionListener = e -> {
                        try {
                                Load.save(Board.board, Board.turn.getText(), typeOfGame);
                        } catch (FileNotFoundException ex) {
                                throw new RuntimeException(ex);
                        }
                };
                save.addActionListener(saveActionListener);
                exit.setBounds(175,30,75,40);
                save.setBounds(350,30,75,40);
                Board.buttonPanel.add(save);
                Board.buttonPanel.add(exit);
        }

        public void move(){
                System.out.println(src + " " + des);
                if(Board.map[des.x][des.y] != null) {
                        if (Board.map[des.x][des.y].getColor().equals("Black"))
                                Board.blackOut.add(Board.map[des.x][des.y]);
                        else
                                Board.whiteOut.add(Board.map[des.x][des.y]);
                        this.Board.revalidate();
                        if(Board.map[des.x][des.y] != null && Board.map[des.x][des.y].name.equals("King")){
                                JLabel jt = new JLabel();
                                jt.setText(Board.map[des.x][des.y].getColor() + " LOSE...");
                                jt.setFont(Board.font);
                                jt.setBounds(140,80,600,600);
                                Board.panel.setVisible(false);
                                MainPage.mainFrame.setVisible(true);
                                this.Board.add(jt);
                        }
                        Board.map[des.x][des.y] = null;
                }
                Board.map[des.x][des.y] = Board.map[src.x][src.y];
                Board.map[src.x][src.y] = null;
                Board.mapButton[des.x][des.y].add(Board.map[des.x][des.y]);
                Board.map[des.x][des.y].setLoc(des.x, des.y);
                Board.board[des.x][des.y] = Board.board[src.x][src.y];
                Board.board[src.x][src.y] = 0;
                Board.mainColor();
                des = src = null;
                changeTurn();
                if(!typeOfGame.equals("Both") && Board.turn.getText().equals(typeOfGame))
                        AIPlayer.nextMove(this, Board.getBoard(), robotColor.equals("White"));
        }

        public void changeTurn(){
                if(Board.turn.getText().equals("Black"))
                        Board.turn.setText("White");
                else
                        Board.turn.setText("Black");
        }

        public void run(){
                frame.setVisible(true);
                frame.setBounds(100,0,900,800);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(Board);
                frame.repaint();
                frame.revalidate();
                if(typeOfGame.equals("White"))
                        AIPlayer.nextMove(this, Board.getBoard(), true);
        }


}
