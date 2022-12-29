import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.Socket;
import java.io.*;
import java.util.*;
import java.net.*;
import javax.swing.JScrollPane;


public class Client extends JFrame {

    final private Font mainFont = new Font("Arial", Font.BOLD, 18);
    private JTextField messageInput = new JTextField();
    private JTextArea messageArea = new JTextArea(35,10);
    private JLabel lbchatin = new JLabel("Type your message");
    private JLabel lbmessage = new JLabel("Messages");
    
    private Socket socket; 
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter; 
    private String username;
    public Client(Socket socket, String username){
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = username; 
            createGUI();
            handleEvents();
        }
        catch(IOException e){
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }
    
public void sendMessage() {
    try{
        bufferedWriter.write(username); 
        bufferedWriter.newLine(); 
        bufferedWriter.flush(); 

        try (Scanner scanner = new Scanner(System.in)) {
            while(socket.isConnected()){ 
                String messageToSend = scanner.nextLine(); 
                
                bufferedWriter.write(username + ": " + messageToSend); 
                bufferedWriter.newLine(); 
                bufferedWriter.flush(); 
            }
        }
    }
    catch(IOException e){
        closeEverything(socket, bufferedReader, bufferedWriter); 
    }
}

public void listenForMessage(){
    new Thread(new Runnable(){ 
        @Override
        public void run(){
            String msgFromGroupChat;

            while(socket.isConnected()){
                try {
                    msgFromGroupChat = bufferedReader.readLine(); 
                    System.out.println(msgFromGroupChat);
                    messageArea.append("\n" + msgFromGroupChat + "\n");
                    
                }
                catch (IOException e){
                    closeEverything(socket, bufferedReader, bufferedWriter); 
                    
                }
            }
        }
    }).start(); 
}

private void handleEvents(){

messageInput.addKeyListener(new KeyListener() {
@Override
public void keyTyped(KeyEvent e){
}
@Override
public void keyPressed(KeyEvent e){
}
@Override
public void keyReleased(KeyEvent e){
if(e.getKeyCode() == 10){
try{
String messageToSend = messageInput.getText();
messageArea.append("\n"+ "You: "+ messageToSend + "\n");
bufferedWriter.write(username + ": " + messageToSend);
bufferedWriter.newLine();
bufferedWriter.flush();
messageInput.setText("");





}
catch (IOException ex){
closeEverything(socket, bufferedReader, bufferedWriter); 
}

}
}});
}
    public void createGUI() {


        JPanel chatPanel = new JPanel();
        chatPanel.setLayout(new GridLayout(2, 1));

        chatPanel.add(lbchatin);
        chatPanel.add(messageInput);
        chatPanel.setOpaque(false);
        

        
        messageArea.setEditable(false);
        

        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new GridLayout(10, 1));

                JScrollPane jscrollpane = new JScrollPane(messageArea);
                messagePanel.add(jscrollpane);


        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(155, 155, 255));
        mainPanel.add(chatPanel, BorderLayout.SOUTH);
        mainPanel.setOpaque(false);


                mainPanel.add(messagePanel, BorderLayout.NORTH);

        add(mainPanel);


        this.setTitle("Chat App");
        this.setSize(300,600);
        this.setMinimumSize(new Dimension(300, 600));
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);

 }


public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedwriter){
    try{
        if(bufferedReader!=null){
            bufferedReader.close();
        }
        if(bufferedWriter != null){
            bufferedWriter.close();
        }
        if (socket != null){
            socket.close();
        }
    }
    catch(IOException e){
        e.printStackTrace(); 
    }
}

 

public static void main(String[] args){
    try (Scanner scanner = new Scanner(System.in)) {
	System.out.println("Enter your username for the group chat: ");
        String username = scanner.nextLine();
        Socket socket = new Socket("localhost", 1234);
        Client client = new Client(socket, username);

        client.listenForMessage(); 
        client.sendMessage();

        
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }

}
}

