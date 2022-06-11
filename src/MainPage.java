import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainPage {
        static JFrame mainFrame;
        public static void main(String[] args) {
                mainFrame = new JFrame();
                JPanel mainPanel = new JPanel();
                mainFrame.setBounds(100,100,500,600);
                mainFrame.setVisible(true);
                
                JButton resume = new JButton("Resume");
                JButton HumanVsHuman = new JButton("Human");
                JButton HumanVsComputer = new JButton("Computer");
                JButton exit = new JButton("EXIT");
        
                ActionListener HumanVsHumanActionListener = new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                try {
                                        HvsH.Start();
                                        mainFrame.setVisible(false);
                                } catch (IOException ex) {
                                        throw new RuntimeException(ex);
                                }
                        }
                };
                
                ActionListener HumanVsComputerActionListener = new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                try {
                                        SelectColor.run();
                                } catch (IOException ex) {
                                        throw new RuntimeException(ex);
                                }
                                mainFrame.setVisible(false);
                        }
                };
                
                ActionListener load = new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                try {
                                        Load.loadLastGame();
                                } catch (IOException ex) {
                                        throw new RuntimeException(ex);
                                }
                                mainFrame.setVisible(false);
                        }
                };
                
                ActionListener exitActionListener = new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                System.exit(0);
                        }
                } ;
        
                JLabel mainPic = new JLabel(new ImageIcon("mainPic.png"));
                mainPanel.add(mainPic);
                mainPic.setOpaque(true);
                mainPic.setBounds(37,0,426,270);
        
                exit.addActionListener(exitActionListener);
                exit.setBounds(170,450,160,50);
                exit.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 20));
                exit.setFocusable(false);
        
                HumanVsHuman.addActionListener(HumanVsHumanActionListener);
                HumanVsHuman.setBounds(150,390,200,50);
                HumanVsHuman.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 20));
                HumanVsHuman.setFocusable(false);
        
                HumanVsComputer.addActionListener(HumanVsComputerActionListener);
                HumanVsComputer.setBounds(130,330,240,50);
                HumanVsComputer.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 20));
                HumanVsComputer.setFocusable(false);
        
                resume.addActionListener(load);
                resume.setBounds(110,270,280,50);
                resume.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 20));
                resume.setFocusable(false);
                
                
                mainPanel.setLayout(null);
                mainPanel.setBackground(Color.black);
                mainFrame.setLayout(new GridLayout(1,1));
                
                mainPanel.add(exit);
                mainPanel.add(HumanVsHuman);
                mainPanel.add(HumanVsComputer);
                mainPanel.add(resume);
        
                mainFrame.add(mainPanel);
                mainFrame.revalidate();
                
                
        }
}
