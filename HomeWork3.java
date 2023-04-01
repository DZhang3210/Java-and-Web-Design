/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */

/**
 *
 * @author dzhang
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class HW3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        String [] questions = {
            "The Sky is blue",
            "The air is 21% oxygen",
            "This exam is in C++",
            "5+5=55",
            "Spring break was last week",
            "Charlie Chaplain is President of the USA"
        };
        boolean[] answers = {true, true, false, false, false, false};
        ExamQ ex = new ExamQ(questions, answers);
        ex.StartQuiz();
    }
}
class ExamQ{
    static boolean correct = false;
    static boolean touched = false;
    String questions[];
    boolean answers[];
    int size;
    ExamQ(String[] questions, boolean[] answers){
        this.size = questions.length;
        this.questions = questions;
        this.answers = answers;
    }

JFrame createWindow(int index, int gNumber){

    JFrame jf = new JFrame("Quiz Question " + gNumber);
    JPanel jp = new JPanel();
    
    jf.setSize (2000, 1000);
    
    JButton b1 = new JButton(); b1.setOpaque(true); b1.setText("True");
    JButton b2 = new JButton(); b2.setOpaque(true); b2.setText("False");
    
    JPanel jp2 = new JPanel();
    JPanel filler = new JPanel();
    jp2.setLayout(new GridLayout(1,3,0,0));
    jp2.add(b1); jp2.add(filler); jp2.add(b2);
    jp.setLayout(new GridLayout(2,1,0,0));
    JButton quest = new JButton();
    JLabel jl = new JLabel(questions[index], SwingConstants.CENTER);
    quest.add(jl);
    
    if (answers[index] == true){
        b1.addActionListener(new TrueResult());
        b2.addActionListener(new FalseResult());
    }else{
        b1.addActionListener(new FalseResult());
        b2.addActionListener(new TrueResult());
    }
    
    jf.add(jp);
    jp.add(jl);
    jp.add(jp2);
    jf.setVisible(true);
    return jf;
}

void StartQuiz(){
    int totalCorrect = 0;
    for(int i = 0; i < 3; i +=1){
    JFrame jf = createWindow((int)(Math.random()*size), i+1);
    Thread myThread = Thread.currentThread();
    double currentTime = System.nanoTime();
    while (System.nanoTime()- currentTime < 5e9 && touched == false){}
    jf.setVisible(false);
    if (ExamQ.correct == true){totalCorrect += 1;}
    ExamQ.correct = false;
    touched = false;
}
createEnd(totalCorrect);
}
class TrueResult implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent e){
        ExamQ.correct = true;
        ExamQ.touched = true;
    }
}
class FalseResult implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e){
        ExamQ.correct = false;
        ExamQ.touched = true;
    }
}

void createEnd(int total_correct){
    JFrame jf = new JFrame("Quiz complete");
    jf.setSize(2000, 1000);
    jf.setVisible(true);
    jf.setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));
    JPanel jp = new JPanel();
    JLabel b1 = new JLabel("You got " + total_correct + " out of 3!", SwingConstants.LEFT);
    jf.add(b1);
}
}

