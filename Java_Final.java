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
import java.util.ArrayList;
import javax.swing.*;
import java.util.List;
import java.util.Arrays;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner; 

public class Java_Final {
    public static int quizzesComplete = 0;
    public static int quizzesCorrect = 0;
    public static String Name;
    public static boolean lost = false;
    public static double timer = 5e9;
    public static int numQuestion = 1;
    public static boolean winner = false;
    public static Color backgroundColor = Color.CYAN;
    public static int winningRound = 12;
    /**
     *
     * @param args the command line arguments
     */
    
    public static void main(String args[]) {
        Read obj = new Read();
        String [] questions = obj.returnQuestions();
        String[][] example= obj.returnAnswers();
        double[] timers = obj.returnTimers();
        ExamQ e2 = new ExamQ(questions, example, 0);
        int numRounds = 0;
        double reducedSeconds = 0;
        
        for(int i = 1; i < winningRound + 1; i++)
        {
           for (int j = 0; j < Math.min(i,4); j++){
            int random = (int)(Math.random()*4);
            new CreateQuiz(numQuestion, timers, backgroundColor, backgroundColor, questions, example, j, reducedSeconds).start();
            } 
           while (quizzesComplete != Math.min(i, 4)){}
           if (quizzesCorrect != Math.min(i,4)) break;
           
           numRounds += 1; quizzesComplete = 0; quizzesCorrect = 0;
           if (i > 4){reducedSeconds+=1e9;}
           if (i == winningRound){ winner = true; break; }
        }
        
        createEndScreen(numRounds, backgroundColor);
    }
    //========================createEndScreen===================
    static void createEndScreen(int numRounds, Color color){
    JFrame jf = new JFrame("Quiz complete");
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
    Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
    jf.setSize((int)(rect.getMaxX()),(int)(rect.getMaxY()));
    jf.setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));
    JLabel b1;
    if (winner == false)
        b1 = new JLabel("You completed " + numRounds +" rounds");
    else
         b1 = new JLabel("You win");
    b1.setBackground(color); b1.setOpaque(true);
    jf.add(b1);jf.setVisible(true);
  }
}

//============================READ CLASS========================
//READ CLASS WHICH HELPS READ THE FILES
class Read {
    String[] files = {"TrueFalse.txt","Multiple_Choice.txt", "Multiple_Choice2.txt", "Quick_Math.txt"};
    ArrayList<String> questions = new ArrayList<>();
    ArrayList<String[]> answers = new ArrayList<>();
    ArrayList<Double> timers =  new ArrayList<>();
    
    Read() {
    for (String file : files) {
        try{
            File currFile = new File(file);
            Scanner scan = new Scanner(currFile);
            String temp = scan.nextLine();
            double timer = Integer.valueOf(temp);
            while(scan.hasNextLine()){
                temp = scan.nextLine();
                if (temp.equals("")) {break;}
                String[] split1 = temp.split(":");
                questions.add(split1[0]);
                answers.add(split1[1].split("/"));
                timers.add(timer);
            }
        scan.close();
        }
        catch (FileNotFoundException e) {
        System.out.println("File not found");
        }   
    }        
    }
    String[] returnQuestions() {
        String[] array = new String[questions.size()];
        for (int i = 0; i < questions.size(); ++i) {array[i] = questions.get(i);}
        return array;
    }
    String[][] returnAnswers() {
        String [][] array = new String[answers.size()][];
        for (int i = 0; i < answers.size(); ++i) {array[i] = answers.get(i);}
        return array;
    }
    double[] returnTimers() {
        double[] array = new double[timers.size()];
        for (int i = 0; i < timers.size(); ++i) {array[i] = (double)(timers.get(i))*(Math.pow(10,9));}
        return array;
    }
}

//======================EXAMQ================================

class ExamQ{
    boolean correct = false; boolean touched = false;
    String questions[]; String answers[][];
    int size; int screenNum; 
    ExamQ(String[] questions, String[][] answers, int screenNum){
        this.size = questions.length; this.questions = questions;
        this.answers = answers; this.screenNum = screenNum;
    }

//Create a window which adds the top question area as well as button, and then returns
//needed functions so that they can be edited later
Object[] createWindow(int index, int gNumber, Color  color, Color backgroundColor){
    JFrame jf = new JFrame("Quiz Question " + gNumber);
    jf.setSize(600,300);
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
    Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
    jf.setSize((int)(rect.getMaxX()/2),(int)(rect.getMaxY()/2));
    int x = 0; int y = 0;
    if (screenNum%2 == 1) x = (int)(rect.getMaxX()/2);
    if (screenNum >1) y = (int)(rect.getMaxY()/2);
    
    jf.setLocation(x, y); jf.setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));
    JPanel jp = new JPanel(); jp.setLayout(new GridLayout(2,1,0,0));
    FunQuestion fq = new FunQuestion(questions[index], color, backgroundColor);
    jp.add(fq); jp.add(createQuestions(answers[index]));
    jf.add(jp);jf.setVisible(true);
    
    Object[] interactions = {jf, fq};
    return interactions;
}

//Creating a custom JPanel which increases number of JButtons, based on inputted answers array
JPanel createQuestions(String[] answers){
    JPanel jp = new JPanel();
    jp.setLayout(new GridLayout(1, answers.length, 0, 0));
    for(int i = 0; i < answers.length; i++){
        JButton b1 = new JButton(); b1.setOpaque(true); b1.setText(answers[i]);
        if (answers[i].charAt(0)=='%'){
            b1.setText(answers[i].substring(0,answers[i].length()));
            b1.addActionListener(new TrueResult());}
        else{b1.addActionListener(new FalseResult());}
        jp.add(b1);
    }
    return jp;
}

//================================StartQuiz============================
//Creates quiz with a number of questions, increments the color, and processes the final endQuiz
public void StartQuiz(int numQuestions, double[] timer, Color color, Color backgroundColor, double secondsReduced){
    int totalCorrect = 0;
    
    for(int i = 0; i < numQuestions; i +=1){
        int random = (int)(Math.random()*size);
        double curr_timer = timer[random] - secondsReduced;
        Object [] interactions = createWindow(random, i+1, color, backgroundColor);
        double currentTime = System.nanoTime();
        //Green
        double twoThirdsAway = ((curr_timer * 2)/3); //Yellow
        double oneThird = ((curr_timer)/3); //Red
        
        double timeElapsed = System.nanoTime() - currentTime;
        while (timeElapsed < curr_timer && touched == false){
            if (HW3.lost == true) break;
            if(timeElapsed > twoThirdsAway) ((FunQuestion)(interactions[1])).changeColor(Color.RED);
            else if(timeElapsed > oneThird) ((FunQuestion)(interactions[1])).changeColor(Color.YELLOW);
            else ((FunQuestion)(interactions[1])).changeColor(Color.GREEN);
            timeElapsed = System.nanoTime() - currentTime;
        }
        ((JFrame)(interactions[0])).setVisible(false);
        if (correct == true){totalCorrect += 1;} if(touched == false){break;}
        correct = false; touched = false;
    }
createEnd(totalCorrect, numQuestions, Color.GREEN, screenNum);
}



//==========================CUSTOM_BUTTON_ACTIONLISTENERS=================
//ActionListeners for the Button to either continue or automatically shut down all other Windows/Quizzes
class TrueResult implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent e){ correct = true; touched = true;}
}
class FalseResult implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e){HW3.lost = true;}
}

//===========================CUSTOM_CREATE_END============================
//Final Method to process if we either continue or increment to amount of quizzes complete
void createEnd(int total_correct, int total, Color backgroundColor, int screenNum){
    if (total_correct == total) HW3.quizzesCorrect += 1;
    HW3.quizzesComplete += 1;
}
}
//===========================END_OF_EXAMQ========================

//=============================CreateQuiz[Thread]=========================
//Custom thread subclass which given certain info is able to generate thread which runs a quiz
class CreateQuiz extends Thread{
    int numQuestion; double[] timer; int screenNum;
    Color color; Color backgroundColor; double secondsReduced;
    String[] questions; String[][] answers;
    CreateQuiz(int numQuestion,double[] timer, Color color, Color backgroundColor, 
            String[] questions, String[][] answers, int screenNum, double secondsReduced){
        this.numQuestion=  numQuestion; this.timer = timer; this.screenNum = screenNum;
        this.color = color; this.backgroundColor = backgroundColor;
        this.questions = questions; this.answers = answers;   
        this.secondsReduced = secondsReduced;
    }
    @Override
    public void run(){
        new ExamQ(questions, answers, screenNum).StartQuiz(numQuestion, timer, color, backgroundColor, secondsReduced);
    } 
}
//===============================FunQuestion[JPanel]================================
//Custom JPanel which enables timer countdown color shifting as well design
class FunQuestion extends JPanel{
    int xLoc; int yLoc;
    int buttonWidth=300; final int buttonHeight=100; String question;
    boolean init;
    Color color; Color backgroundColor;
    FunQuestion(String question,Color color, Color backgroundColor){
        super();
        init =true;
        this.question = question; this.buttonWidth  = 10 * question.length();
        this.color= color; this.backgroundColor = backgroundColor;
    }
    void changeColor(Color color){
        this.color = color;
        repaint();
    }
    
    void drawButton(Graphics g){
        g.setColor(color);
        g.fill3DRect(xLoc-buttonWidth/2, yLoc-buttonHeight/2, buttonWidth, buttonHeight, true);
        g.setColor(Color.black);
        g.setFont(new Font("ARIAL",Font.PLAIN,16));
        g.drawString(question, xLoc-buttonWidth/3, yLoc);
    }
    //Generally Moot unless find a use for it
    void drawOval(Graphics g, int x, int y, int width, int height){
        g.setColor(color);
        g.fillOval(x, y, width, height);
    }
    @Override
    //The main function which creates components
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        setBackground(backgroundColor);
        xLoc=getSize().width/2; yLoc=getSize().height/2;
        drawButton(g);
    }
}

