package com.socket;

import java.io.*;
import java.net.*;






public class SocketServer implements Runnable {
    
    public ServerThread clients[];
    public ServerSocket server = null;
    public Thread       thread = null;
    public int clientCount = 0, port = 80;
    public ServerFrame ui;
    public Database db;
    public FileInfo fi;
    public int from, to;
    public SocketServer(ServerFrame frame){
       
        clients = new ServerThread[50];
        ui = frame;
        db = new Database(ui.filePath);
        
	try{  
	    server = new ServerSocket(port);
            port = server.getLocalPort();
	   // ui.jTextArea1.append("Server startet. IP : " + InetAddress.getLocalHost() + ", Port : " + server.getLocalPort());
	    start(); 
        }
	catch(IOException ioe){  
           // ui.jTextArea1.append("Can not bind to port : " + port + "\nRetrying"); 
            ui.RetryStart(0);
	}
    }
    
    public SocketServer(ServerFrame frame, int Port){
       
        clients = new ServerThread[50];
        ui = frame;
        port = Port;
        db = new Database(ui.filePath);
        
	try{  
	    server = new ServerSocket(port);
            port = server.getLocalPort();
            InetAddress ad = InetAddress.getLocalHost();
            String add = ad.toString();
            
            String po = Integer.toString(port);
            System.out.println(add);
            ui.ipText.setText(add);
            System.out.println("Hello");
            ui.portText.setText(po);
            System.out.println(po); 
            ui.ipText.setVisible(true);
            ui.portText.setVisible(true);
	    //ui.jTextArea1.append("Server startet. IP : " + InetAddress.getLocalHost() + ", Port : " + server.getLocalPort());
            System.out.println("Connected!!");
            System.out.println("IP : " + InetAddress.getLocalHost() + "\nPort : " + server.getLocalPort() + "\n");
	    start(); 
        }
	catch(IOException ioe){  
           // ui.jTextArea1.append("\nCan not bind to port " + port + ": " + ioe.getMessage()); 
            System.out.println("can't bind to port");
	}
    }
	
    public void run(){  
	while (thread != null){  
            try{  
		//ui.jTextArea1.append("\nWaiting for a client ..."); 
	        addThread(server.accept()); 
	    }
	    catch(Exception ioe){ 
               // ui.jTextArea1.append("\nServer accept error: \n");
                ui.RetryStart(0);
	    }
        }
    }
	
    public void start(){  
    	if (thread == null){  
            thread = new Thread(this); 
	    thread.start();
	}
    }
    
    @SuppressWarnings("deprecation")
    public void stop(){  
        if (thread != null){  
            thread.stop(); 
	    thread = null;
	}
    }
    
    private int findClient(int ID){  
    	for (int i = 0; i < clientCount; i++){
        	if (clients[i].getID() == ID){
                    return i;
                }
	}
	return -1;
    }
	
    public synchronized void handle(int ID, Message msg){  
	if (msg.content.equals(".bye")){
            Announce("signout", "SERVER", msg.sender,1,1);
            remove(ID); 
	}
	else{
            if(msg.type.equals("login")){
                if(findUserThread(msg.sender) == null){
                    if(db.checkLogin(msg.sender, msg.content)){
                        clients[findClient(ID)].username = msg.sender;
                        clients[findClient(ID)].send(new Message("login", "SERVER", "TRUE", msg.sender, 1, 1));
                       Announce("newuser", "SERVER", msg.sender, 1,1);
                        SendUserList(msg.sender);
                    }
                    else{
                        clients[findClient(ID)].send(new Message("login", "SERVER", "FALSE", msg.sender,1,1));
                    } 
                }
                else{
                    clients[findClient(ID)].send(new Message("login", "SERVER", "FALSE", msg.sender,1,1));
                }
            }
            else if(msg.type.equals("message")){
                if(msg.recipient.equals("All")){
                    Announce("message", msg.sender, msg.content,1 ,1);
                }
                else{
                   // findUserThread(msg.recipient).send1(new FileInfo(fi.sender, fi.recipient));
                   // clients[findClient(ID)].send1(new FileInfo(fi.sender, fi.recipient));
                    findUserThread(msg.recipient).send(new Message(msg.type, msg.sender, msg.content, msg.recipient, msg.from, msg.to));
                    clients[findClient(ID)].send(new Message(msg.type, msg.sender, msg.content, msg.recipient, msg.from, msg.to));
                    System.out.println(msg.sender + "  " + msg.recipient);
                    System.out.println(msg.from + "  " + msg.to);
                }
            }
            else if(msg.type.equals("test")){
                clients[findClient(ID)].send(new Message("test", "SERVER", "OK", msg.sender, msg.from, msg.to));
            }
            else if(msg.type.equals("signup")){
                if(findUserThread(msg.sender) == null){
                    if(!db.userExists(msg.sender)){
                       db.addUser(msg.sender, msg.content);
                        clients[findClient(ID)].username = msg.sender;
                        clients[findClient(ID)].send(new Message("signup", "SERVER", "TRUE", msg.sender, msg.from, msg.to));
                        clients[findClient(ID)].send(new Message("login", "SERVER", "TRUE", msg.sender, msg.from, msg.to));
                       Announce("newuser", "SERVER", msg.sender, 1, 1);
                        SendUserList(msg.sender);
                   }
                    else{
                       clients[findClient(ID)].send(new Message("signup", "SERVER", "FALSE", msg.sender, msg.from, msg.to));
                    }
                }
                else{
                    clients[findClient(ID)].send(new Message("signup", "SERVER", "FALSE", msg.sender, msg.from, msg.to));
                }
            }
            else if(msg.type.equals("upload_req")){
                if(msg.recipient.equals("All")){
                    clients[findClient(ID)].send(new Message("message", "SERVER", "Uploading to 'All' forbidden", msg.sender, msg.from, msg.to));
                }
                else{
                    findUserThread(msg.recipient).send(new Message("upload_req", msg.sender, msg.content, msg.recipient, msg.from, msg.to));
                }
            }
            else if(msg.type.equals("upload_res")){
                if(!msg.content.equals("NO")){
                    String IP = findUserThread(msg.sender).socket.getInetAddress().getHostAddress();
                    findUserThread(msg.recipient).send(new Message("upload_res", IP, msg.content, msg.recipient, msg.from, msg.to));
                }
                else{
                    findUserThread(msg.recipient).send(new Message("upload_res", msg.sender, msg.content, msg.recipient, msg.from, msg.to));
                }
            }
	}
    }
    
    public void Announce(String type, String sender, String content, int from, int to){
        Message msg = new Message(type, sender, content, "All", from, to);
        for(int i = 0; i < clientCount; i++){
            clients[i].send(msg);
        }
    }
    
    public void SendUserList(String toWhom){
        for(int i = 0; i < clientCount; i++){
            findUserThread(toWhom).send(new Message("newuser", "SERVER", clients[i].username, toWhom,1,1));
        }
    }
    
    public ServerThread findUserThread(String usr){
        for(int i = 0; i < clientCount; i++){
            if(clients[i].username.equals(usr)){
                return clients[i];
            }
        }
        return null;
    }
	
    @SuppressWarnings("deprecation")
    public synchronized void remove(int ID){  
    int pos = findClient(ID);
        if (pos >= 0){  
            ServerThread toTerminate = clients[pos];
           // ui.jTextArea1.append("\nRemoving client thread " + ID + " at " + pos);
	    if (pos < clientCount-1){
                for (int i = pos+1; i < clientCount; i++){
                    clients[i-1] = clients[i];
	        }
	    }
	    clientCount--;
	    try{  
	      	toTerminate.close(); 
	    }
	    catch(IOException ioe){  
	      //	ui.jTextArea1.append("\nError closing thread: " + ioe); 
	    }
	    toTerminate.stop(); 
	}
    }
    
    private void addThread(Socket socket){  
	if (clientCount < clients.length){  
            //ui.jTextArea1.append("\nClient accepted: " + socket);
	    clients[clientCount] = new ServerThread(this, socket);
	   try{  
	      	clients[clientCount].open(); 
	        clients[clientCount].start();  
	        clientCount++; 
	    }
	    catch(IOException ioe){  
	      //	ui.jTextArea1.append("\nError opening thread: " + ioe); 
	    } 
	}
	else{
           // ui.jTextArea1.append("\nClient refused: maximum " + clients.length + " reached.");
	}
    }
}
