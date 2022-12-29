import java.net.Socket;
import java.util.*;
import java.io.*;

//The class of Client handler runs inside a thread declared in Server
//Separate thread for separate client?
public class ClientHandler implements Runnable{
    
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    //initialising the array list

    public Socket socket;
    //initialising an inbuilt socket class

    private BufferedReader bufferedReader;
    //initialising a reader

    private BufferedWriter bufferedWriter;
    //initialising a writer

    private String clientUsername;
    //initialising the client's Username



    public ClientHandler(Socket socket){
        //initialising the socket once for 1 server (does not depend on the client)

        //below process executes exactly once - instantiates the necessities this class requires and
        //adds these details to an array list
        //collects username once and broadcasts a message saying that a client has entered the chat
        try{
            this.socket = socket;
            //assigning the input socket variable to the one inside the class

            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            //assigning a new bufferwriter 

            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //assigning a new bufferreader

            this.clientUsername = bufferedReader.readLine();
            //collecting the new client's username from the first line of the input

            clientHandlers.add(this);
            //add all "this" to an arraylist. "this" refers to the full class

            broadcastMessage("Server: " + clientUsername + " has entered the chat");
            //sends message for everyone

        }    
        catch(IOException e){
            closeEverything(socket, bufferedReader, bufferedWriter);
            //closes everything in case of an exception

        }
    }

    //overrides the runnable class
    @Override
    public void run(){
        String messageFromClient; //collects the message from the client

        while(socket.isConnected()){ //while(1) 1 is whenever the socket is connected
            try{
                messageFromClient = bufferedReader.readLine(); //reads the message from the client
                broadcastMessage(messageFromClient); //sends the message
            }
            catch(IOException e){
                closeEverything(socket, bufferedReader, bufferedWriter);
                break;
            }
    }
}

//this function inputs the message to be sent 
//it broadcasts the message to all other users except itself
public void broadcastMessage(String messageToSend){
    for(ClientHandler clientHandler : clientHandlers){ //for all the clienthandlers in the arraylist
        try{
            if(!clientHandler.clientUsername.equals(clientUsername)){
                //if statement means that whenever the client username doesn't match a clientHandler's username, 
                // it will broadcast the message

                clientHandler.bufferedWriter.write(messageToSend); //that particular clienthandler's bufferwriter gets return

                clientHandler.bufferedWriter.newLine(); //a new line is initialised
                
                clientHandler.bufferedWriter.flush(); //the data is flushed. when it is flushed, data is transmitted
                //even when u dont close it
            
            }
        }
        catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }

    }
}

public void removeClientHandler(){
    clientHandlers.remove(this); //remove "this" particular class
    broadcastMessage("Server: " + clientUsername + "user has left the chat");
}

public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
    removeClientHandler(); //removes the clienthandler


    try{
        //these codes are to avoid null pointer exception, which means that even when the values of these aren't null,
        //they get closed
        if(bufferedReader!=null){
            bufferedReader.close(); 
        }
        if(bufferedWriter!=null){
            bufferedReader.close();
        }
        if (socket != null){
            socket.close();
        }
    }
    catch(IOException e){
        e.printStackTrace();
    }
}
}