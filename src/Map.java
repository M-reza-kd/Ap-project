import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import static java.lang.Math.abs;

public class Map extends JComponent {
        CHEsMAN[][] map = new CHEsMAN[8][8];
        int[][] board = new int[8][8];
        JPanel blackOut = new JPanel(new GridLayout(8, 2));
        JPanel whiteOut = new JPanel(new GridLayout(8, 2));
        JPanel buttonPanel = new JPanel();
        JPanel WHOSTURNE = new JPanel(new BorderLayout());
        JLabel turn, wl, bl;
        JButton[][] mapButton;
        JPanel panel = new JPanel(new GridLayout(8, 8));
        Font font;
        static final Color WHITE = new Color(243, 198, 89);
        static final Color BLACK = new Color(114, 67, 56);
        static final int[] cntChessman = {0, 1, 1, 2, 2, 2, 8};

        /**
         * setting the text, bounds and other relevant thing to the graphic of game
         */
        private void preProcessOfMap() {

                this.setBounds(100, 0, 900, 800);
                WHOSTURNE.setBounds(0, 0, 900, 80);
                WHOSTURNE.setBackground(new Color(248, 218, 159));
                WHOSTURNE.setLayout(null);
                bl = new JLabel("BLACK OUT");
                wl = new JLabel("WHITE OUT");
                turn = new JLabel("White");
                turn.setBounds(375, 0, 200, 100);
                wl.setBounds(10, 0, 150, 100);
                bl.setBounds(760, 0, 150, 100);
                WHOSTURNE.add(turn);
                WHOSTURNE.add(bl);
                WHOSTURNE.add(wl);
                WHOSTURNE.revalidate();
                bl.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
                wl.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
                font = new Font("SansSerif", Font.BOLD | Font.ITALIC, 50);
                turn.setFont(font);
                this.setLayout(null);
                this.add(whiteOut);
                whiteOut.setBounds(0, 80, 150, 700);
                blackOut.setBounds(750, 80, 150, 700);
                whiteOut.setBackground(new Color(248, 218, 159));
                blackOut.setBackground(new Color(248, 218, 159));
                this.add(blackOut);
                this.add(WHOSTURNE);
                mapButton = new JButton[8][8];
                buttonPanel = new JPanel();
                buttonPanel.setLayout(null);
                buttonPanel.setBounds(150, 680, 600, 100);
                buttonPanel.setBackground(new Color(248, 218, 159));
                this.add(buttonPanel);
                for (int i = 0; i < 8; i++)
                        for (int j = 0; j < 8; j++) {
                                mapButton[i][j] = new JButton();
                                if (i % 2 == j % 2)
                                        mapButton[i][j].setBackground(WHITE);
                                else
                                        mapButton[i][j].setBackground(BLACK);
                                panel.add(mapButton[i][j]);
                        }

                panel.setBounds(150, 80, 600, 600);
                this.add(panel);
                buttonPanel.revalidate();
                this.revalidate();
        }

        /**
         * constructor of making the graphic of the game from specific status
         * @param board detail of pieces location
         * @param whiteChessman the number of pieces of each type of white piece
         * @param blackChessman the number of pieces of each type of black piece
         * @param whosTurn showing who` turn to make the move
         */
        public Map(int[][] board, int[] whiteChessman, int[] blackChessman, String whosTurn) throws IOException {
                turn = new JLabel(whosTurn);
                this.board = board;
                preProcessOfMap();
                for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                                String color = "White";
                                if (board[i][j] < 0)
                                        color = "Black";
                                switch (abs(board[i][j])) {
                                        case 1 -> this.map[i][j] = new King(color, i, j);
                                        case 2 -> this.map[i][j] = new Queen(color, i, j);
                                        case 3 -> this.map[i][j] = new Rook(color, i, j);
                                        case 4 -> this.map[i][j] = new Bishop(color, i, j);
                                        case 5 -> this.map[i][j] = new Knight(color, i, j);
                                        case 6 -> this.map[i][j] = new Pawn(color, i, j);
                                }
                                if (this.map[i][j] != null)
                                        this.mapButton[i][j].add(this.map[i][j]);
                        }
                }

                for (int i = 1; i <= 6; i++) {
                        CHEsMAN temp = new Pawn("White", 0, 0);
                        switch (i) {
                                case 1 -> temp = new King("White", 0, 0);
                                case 2 -> temp = new Queen("White", 0, 0);
                                case 3 -> temp = new Rook("White", 0, 0);
                                case 4 -> temp = new Bishop("White", 0, 0);
                                case 5 -> temp = new Knight("White", 0, 0);
                                case 6 -> temp = new Pawn("White", 0, 0);
                        }
                        for (int j = 0; j < cntChessman[i] - whiteChessman[i]; j++)
                                whiteOut.add(temp);
                }
                for (int i = 1; i <= 6; i++) {
                        CHEsMAN temp = new Pawn("Black", 0, 0);
                        switch (i) {
                                case 1 -> temp = new King("Black", 0, 0);
                                case 2 -> temp = new Queen("Black", 0, 0);
                                case 3 -> temp = new Rook("Black", 0, 0);
                                case 4 -> temp = new Bishop("Black", 0, 0);
                                case 5 -> temp = new Knight("Black", 0, 0);
                                case 6 -> temp = new Pawn("Black", 0, 0);
                        }
                        for (int j = 0; j < cntChessman[i] - blackChessman[i]; j++)
                                blackOut.add(temp);
                }
        }

        /**
         * constructor of making the graphic of the game from the beginning
         */
        public Map() throws IOException {
                preProcessOfMap();
                for (int j = 0; j < 8; j++) {
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

                for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                                if (map[i][j] == null)
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
        public String getChessmanColor(int x, int y) {
                return map[x][y].getColor();
        }

        /**
         * painting the board
         */
        public void mainColor() {
                for (int i = 0; i < 8; i++)
                        for (int j = 0; j < 8; j++)
                                if (i % 2 == j % 2)
                                        mapButton[i][j].setBackground(WHITE);
                                else
                                        mapButton[i][j].setBackground(BLACK);
        }
        public int[][] getBoard() {
                return board;
        }
}
