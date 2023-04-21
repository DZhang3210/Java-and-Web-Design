import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.*;


public class HW5 extends JPanel implements ActionListener {

    static String user_name;
    static int port;
    static PrintStream output;
    static JTextArea message_area;
    
    JTextField text_box1;
    JTextField text_box2;
    JTextField text_box3;


    public HW5() {
        JPanel input_field = new JPanel();
        input_field.setLayout(new BorderLayout());
        input_field.setBackground(new Color(201, 102, 97));
        
        //TEXT DIALOGUE FOR CHAT
        text_box1 = new JTextField(42);
        text_box1.setToolTipText("Type here!");
        text_box1.setForeground(new Color(43, 99, 64));
        text_box1.addActionListener(evt -> actionPerformed(evt));
       
        //SEND AND CONNECT BUTTONS
        JButton send_button = new JButton("SEND");
        send_button.setBackground(new Color(102, 178, 255));
        send_button.setForeground(Color.white);
        send_button.setOpaque(true);
        send_button.setBorderPainted(false);
        send_button.addActionListener(evt -> actionPerformed(evt)); 
        
        JButton connect_button = new JButton("Connect");
        connect_button.setBackground(new Color(43, 99, 64));
        connect_button.setForeground(Color.white);
        connect_button.setOpaque(true);
        connect_button.setBorderPainted(false);
        connect_button.addActionListener(evt -> actionConnect(evt));
        
        //INSERTING TEXT BOXES INTO INPUT_FIELD
        input_field.add(text_box1);
        input_field.add(text_box1, BorderLayout.CENTER);
        input_field.add(send_button, BorderLayout.EAST);
        
        //TEXT BOXES FOR USERNAME + PORT NUM
        text_box2 = new JTextField(20);
        text_box2.setToolTipText("Username: ");
        text_box2.setForeground(new Color(43, 99, 64));
        text_box2.addActionListener(evt -> actionPerformed(evt));
        
        text_box3 = new JTextField(20);
        text_box3.setToolTipText("Port Num: ");
        text_box3.setForeground(new Color(43, 99, 64));
        text_box3.addActionListener(this);
        
        //CREATING SEPERATE JPANEL TO PLACE CONNECT
        JPanel user_info = new JPanel();
        user_info.setLayout(new BorderLayout());
        user_info.setBackground(new Color(201, 102, 97)); 
        user_info.add(text_box2, BorderLayout.WEST);
        user_info.add(text_box3, BorderLayout.CENTER);
        user_info.add(connect_button, BorderLayout.EAST);
        
        //INSERTING INPUT FIELD AT THE VERY BOTTOM
        input_field.add(user_info, BorderLayout.SOUTH);        
        
        message_area = new JTextArea(32, 44);
        message_area.setForeground(new Color(43, 99, 64));
        message_area.setEditable(false);

        JPanel chat_box = new JPanel();
        chat_box.setLayout(new BorderLayout());
        chat_box.setBackground(new Color(224, 157, 153));
        chat_box.add(message_area, BorderLayout.SOUTH);
        chat_box.add(input_field, BorderLayout.NORTH);
        add(chat_box);
        
    }

    public void actionPerformed(ActionEvent evt) {
        output.print("  " + text_box1.getText() + "\r\n");
        text_box1.setText("");
    }

    public void actionConnect(ActionEvent evt) {
        user_name = text_box2.getText();
        String port_text = text_box3.getText();
        port = Integer.parseInt(text_box3.getText());

        output.print(text_box1.getText() + "\r\n");
        output.print("  >" + user_name +  " successfully connected to port " + port_text + "\r\n");
        text_box2.setText(""); text_box3.setText("");
    }

    public static void GUI_setup() {
        JFrame jf = new JFrame("Chat Panel");
        jf.setVisible(true);
        jf.add(new HW5());
        jf.setSize(600, 600);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        GUI_setup();

        try {
            Socket s = new Socket("localhost", 5190);
            Scanner input = new Scanner(s.getInputStream());
            output = new PrintStream(s.getOutputStream());
            message_area.setText("*Enter Username and Press Send To Begin Chatting*\n");
            
            String nextLine = "";
            while (true){
                nextLine = input.nextLine();
                message_area.setText(message_area.getText() + nextLine + "\n");
            }
        }
        catch (IOException ex) {}
    }
}
