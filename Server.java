import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket serverSocket;
    
    public Server(ServerSocket serverSocket) { 
        //declaring a function inside this class

        //this pointer is as same as ->
        //the role of this function is to collect the serverSocket and assign it
        //inside this class's serverSocket
        //key role is to instantiate a serversocket for a given server

        this.serverSocket = serverSocket;
    }

//function that starts the server

public void startServer() {

    try{

        while(!serverSocket.isClosed()) {
            //whenever the serversocket isn't closed, it actively accepts a client

            Socket socket = serverSocket.accept();
            //this code is to accept a client's socket
            //next line of code proceeds when a client connects to this server

            System.out.println("A new client has connected!");

            ClientHandler clientHandler = new ClientHandler(socket);
            //a client handler starts for this unique socket(hostname and port)
            
            Thread Jthread = new Thread(clientHandler);
            //after declaring the clienthandler, it is channelised to a thread to run parallely
            Jthread.start();
            //thread starts

            //while loop reverts back to finding another serversocket

        }
    }
    catch (IOException e){

    }

}
 
public void closeServerSocket() {
    try {
        if (serverSocket!= null){
            serverSocket.close();
        }
        //whenever a serversocket is not closed but needs to be closed, use this
    }
    catch(IOException e){
        e.printStackTrace();
    }
}

public static void main(String[] args) throws IOException{
    ServerSocket serverSocket = new ServerSocket(1234);
    //a new server socket is created with a unique port number
    //this is an inbuilt entity

    Server server = new Server(serverSocket);
    //pass this serversocket to the function of server class



    server.startServer();
    //this function starts all the processes



}


}