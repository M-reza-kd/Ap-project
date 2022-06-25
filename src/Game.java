import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Game {
        JFrame frame;
        Map Board;
        String typeOfGame;
        Point src, des;
        static ActionListener mainActionListener;

        /**
         * constructor of the new game
         * @param type type of game
         */
        public Game(String type) throws IOException {
                typeOfGame = type;
                Board = new Map();
                addActionListenerToChessman();
        }

        /**
         * constructor of the saved game
         * @param m map of the game that we are making its game
         * @param type type of the game
         */
        public Game(Map m, String type){
                typeOfGame = type;
                Board = m;
                addActionListenerToChessman();
        }

        /**
         * adding action listener to the chessman
         */
        public void addActionListenerToChessman(){
                mainActionListener = e -> {
                        JButton b = (JButton) e.getSource();
                        Point X = new Point();
                        for(int i = 0; i < 8; i++)
                                for(int j = 0; j < 8; j++)
                                        if(Board.mapButton[i][j].equals(b)){
                                                X.setLocation(i,j);
                                                break;
                                        }
                        if(src == null && !Board.turn.getText().equals(Board.map[X.x][X.y].getColor())) {
                                System.out.println("First if");
                                return;
                        }
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
                                else {
                                        try {
                                                HMPlayer.nextMove(this);
                                        } catch (InterruptedException ex) {
                                                throw new RuntimeException(ex);
                                        }
                                }
                        }

                };
                for(int i = 0 ; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                                Board.mapButton[i][j].addActionListener(mainActionListener);
                        }
                }

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

        /**
         * moving the pieces and check if someone won or not
         */
        public void move() throws InterruptedException {
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
                CHEsMAN temp = Board.map[src.x][src.y];
                Board.map[des.x][des.y] = null;
                Animation.play(Board, src, des, Board.map[src.x][src.y]);
                Board.map[des.x][des.y] = temp;
                Board.mapButton[des.x][des.y].add(Board.map[des.x][des.y]);
                Board.mapButton[des.x][des.y].repaint();
                Board.mapButton[src.x][src.y].repaint();
                Board.map[des.x][des.y].setLoc(des.x, des.y);
                Board.board[des.x][des.y] = Board.board[src.x][src.y];
                Board.board[src.x][src.y] = 0;
                Board.mainColor();
                des = src = null;
                changeTurn();
                frame.revalidate();
        }

        /**
         * changing the player turn and make the move if is robot turn
         */
        public void changeTurn() throws InterruptedException {
                if(Board.turn.getText().equals("Black"))
                        Board.turn.setText("White");
                else
                        Board.turn.setText("Black");
                if(Board.turn.getText().equals(typeOfGame)) {
                        AIPlayer.nextMove(this, Board.getBoard(), typeOfGame.equals("White"));
                }
                frame.revalidate();
                Board.revalidate();
                Board.panel.revalidate();
        }

        /**
         * running the created game
         */
        public void run() throws InterruptedException {
                frame = new JFrame();
                frame.setVisible(true);
                frame.setBounds(100,0,900,800);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(Board);
                frame.repaint();
                frame.revalidate();
                if(Board.turn.getText().equals(typeOfGame)) {
                        AIPlayer.nextMove(this, Board.getBoard(), typeOfGame.equals("White"));
                }
        }


}
