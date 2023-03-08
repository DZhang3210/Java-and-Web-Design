/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cs3913spring2023gui;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author dkatz
 */
public class HomeWork2 {

    /**
     * @param args the command line arguments
     */
    static JButton buttons[] = new JButton[8];
    static boolean flipColors[] = new boolean[8];
    
    public static void main(String[] args) {
        for (int i = 0; i < 8; i++){flipColors[i] = true;}
        JFrame jf = new JFrame("Homework Two");
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel jp = new JPanel();
        jp.setBackground(Color.PINK);
        jp.setLayout(new GridLayout(4,2,2,2));
        
        for (int i = 0; i < 8; i++){
            buttons[i] = new JButton();
            int R = (int)(Math.random()*256);
            int G = (int)(Math.random()*256);
            int B = (int)(Math.random()*256);
            Color color = new Color(R, G, B);
            //buttons[i].setLabel("Hey");
            buttons[i].addActionListener(new ButtonPressed());
            
            buttons[i].setBackground(color);
            buttons[i].setOpaque(true);
            buttons[i].setBorderPainted(false);
            jp.add(buttons[i]);
        }
        
        jf.setBackground(Color.yellow);
        jf.add(jp);
        jf.setSize(1000,400);
        jf.setVisible(true);
        
        while(true){
            for(int i = 0; i < 8; i++){
                if(flipColors[i] == false){continue;}
                int R = (int)(Math.random()*256);
                int G = (int)(Math.random()*256);
                int B = (int)(Math.random()*256);
                buttons[i].setBackground(new Color(R, G, B));
                Thread myThread = Thread.currentThread();           
            }
            try{
                    Thread.sleep(1000);
                }catch(InterruptedException e){}
        }
    }
}
class ButtonPressed implements ActionListener{
        
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton current = (JButton)e.getSource();
        for(int i = 0; i < 8; i++){
            if(HomeWork2.buttons[i] == current){
                HomeWork2.flipColors[i] = !(HomeWork2.flipColors[i]);
                break;
            }
        }
    }
}