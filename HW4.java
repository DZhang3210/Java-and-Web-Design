import java.io.*;
import java.net.*;
import java.util.*;
import java.sql.DriverManager;

public class HW4 {
	
	//THIS I WAS UNABLE TO FIGURE OUT HOW TO GET THE MARIADB DATABASE TO STORE THE FILES
	//I WROTE SOME NOTES AND CODE WHILE ATTEMPTING TO DO IT
	
//    static int port = 5190;
//    //Example ip Address and username which might be inserted by user
//    String url="jdbc:mariadb://localhost:5190/";
//    String username="username";
//    String password = "password";
//    
//    Connection conn = DriverManager.getConnection(url,username,password);
//    ServerSocket ss = new ServerSocket(5190);
//    String ip = "000.000.000.000";
//    Socket sock = ss.accept();
//    String query = "INSERT INTO logins (" + sock.getInetAdress() + ", username) "
//    		+ "VALUES ('" + ip + "', '" + username + "');";
	
//    //Query to create Table 
//    static String createTable = 
//    		"CREATE TABLE LOGINS ("
//    		+ "id INT NOT NULL AUTO_INCREMENT,"
//    		+ "ip_address VARCHAR(45) NOT NULL,"
//    		+ "login_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,"
//    		+ "username VARCHAR(50) NOT NULL,"
//    		+ "PRIMARY KEY (id)"
//    		+ ");";
//    //Statement 

    
    public static void main(String[] args) {
    	String error = "200";
        ServerSocket socket = null;
        try {
            socket = new ServerSocket(port);
            while(true) { new Connection(socket.accept()).start();}
        }catch(IOException ex) { error = "500"; }
        System.out.println(error);
    }
}

class Connection extends Thread{
    static ArrayList<Connection> users = new ArrayList<Connection>();
    Socket client;
    String username;

    Connection(Socket data) { client = data; }
    public void run() {
        try {
            Scanner user_input = new Scanner(client.getInputStream());
            username = user_input.nextLine();
            String message = "";
            users.add(this);

            while (!message.equals("STOP")) {
                for (Connection conn : users) {conn.send(user_input.nextLine(), username);}
            }
            client.close();
        }catch(IOException ex) {}
    }

    public void send(String msg, String usern){
        try { 
        	new PrintStream(client.getOutputStream()).println("  " + usern + ": " + msg);
        }
        catch(IOException ex) {}
    }
}
