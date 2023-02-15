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
public class CS3913Spring2023GUI {

    /**
     * @param args the command line arguments
     */
    static JButton [] arr;

    public static void main(String[] args) {
        JFrame jf = new JFrame("My First window!");
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel jp = new JPanel();
        jp.setBackground(Color.PINK);
        jp.setLayout(new GridLayout(4,2,4,4));
        
        arr = new JButton[8];
        for (int i = 0; i < 8; i++){
            arr[i] = new JButton("Click Me");
            int R = (int)(Math.random()*256);
            int G = (int)(Math.random()*256);
            int B = (int)(Math.random()*256);
            Color color = new Color(R, G, B);
            arr[i].addActionListener(new ButtonPressed());
            arr[i].setBackground(color);
            arr[i].setOpaque(true);
            arr[i].setBorderPainted(false);
            jp.add(arr[i]);
        }
        
        jf.setBackground(Color.yellow);
        jf.add(jp);
        jf.setSize(1000,400);
        jf.setVisible(true);
  
    }
    
}

class ButtonPressed implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 8; i++){
            int R = (int)(Math.random()*256);
            int G = (int)(Math.random()*256);
            int B = (int)(Math.random()*256);
            Color color = new Color(R, G, B);
//            CS3913Spring2023GUI.arr[i].setText("THANK YOU!");
            //Color color = new Color(rand.nextInt());
            CS3913Spring2023GUI.arr[i].setBackground(color);
//            CS3913Spring2023GUI.arr[i].setBackground(Color(R, G, B).getAlpha)
//             JButton source = (JButton) e.getSource();
//            source.setText("THANK YOU!");
        }
        //JButton source = (JButton) e.getSource();

    }
    
}