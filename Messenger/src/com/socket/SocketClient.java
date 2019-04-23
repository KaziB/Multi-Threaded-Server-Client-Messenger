package com.socket;

import com.ui.ChatFrame;
import com.ui.LoginScreen;
import com.ui.chatWindow;
import java.io.*;
import java.net.*;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class SocketClient implements Runnable{
    
    public int port;
    public String serverAddress;
    public Socket socket;
    public static LoginScreen ui;
    public ObjectInputStream In;
    public ObjectOutputStream Out;
    public chatWindow chatWindowObject;
    public int index = 1;
    public chatWindow[][] cw;
    public int index2;
    //public History hist;
    public FindInfo fi;
    public static boolean flag = true;
    public SocketClient(LoginScreen frame) throws IOException{
        ui = frame; 
        //chatWindowObject = chatFrame;
        this.serverAddress = ui.serverAddress; 
        this.port = ui.port;
        
        //socket = new Socket(InetAddress.getByName(serverAddr), port);
          socket = new Socket("192.168.0.102", port);  
        Out = new ObjectOutputStream(socket.getOutputStream());
        Out.flush();
        In = new ObjectInputStream(socket.getInputStream());
        
        //hist = ui.hist;
    }
    public void getFrame(chatWindow[][] frame, int index, int index2) {
        cw = frame;
        this.index = index;
        this.index2 = index2;
    }
    public void getIndexes(int index, int index2) {
        this.index = index;
        this.index2 = index2;
    }
    
    @Override
    public void run() {
        boolean keepRunning = true;
        while(keepRunning){
            try {
                Message msg = (Message) In.readObject();
                System.out.println("Incoming : "+msg.toString());
                
                if(msg.type.equals("message")){
                    
                    if(!msg.recipient.equals("All")) {
                    System.out.println(msg.from + " " + msg.to);
                    if(ui.arr[msg.from][msg.to] == false && ui.arr[msg.to][msg.from] == false) {
                            ui.chatwindow[msg.from][msg.to] = new chatWindow();
                            ui.chatwindow[msg.from][msg.to].nameLabel.setText(msg.sender);
                            //if(ui.arr[msg.to][msg.from] == false)
                            ui.chatwindow[msg.to][msg.from] = ui.chatwindow[msg.from][msg.to];
                            ui.arr[msg.from][msg.to] = true;
                            ui.arr[msg.to][msg.from] = true;
                            ui.chatwindow[msg.from][msg.to].setVisible(true);
                            
                            System.out.println("Create WIndow");
                
                    }
                    }
                    if(ui.arr[msg.from][msg.to] == true && ui.chatwindow[msg.from][msg.to].isVisible()==false) {
                        ui.chatwindow[msg.from][msg.to].setVisible(true);
                    }
                    else if(ui.arr[msg.to][msg.from] == true && ui.chatwindow[msg.to][msg.from].isVisible()==false) {
                        ui.chatwindow[msg.to][msg.from].setVisible(true);
                    }
                    if(msg.recipient.equals("All")) {
                        ui.messageScreen.append(msg.sender + ">" + "All : " + msg.content + "\n");
                    }
                    else if(msg.recipient.equals(ui.username)){
                        System.out.println("1. " + msg.from + "  " + msg.to);
                       // if(ui.arr[msg.from][msg.to] == false)
                        ui.chatwindow[msg.from][msg.to].messageScreen.append("["+msg.sender +" > Me] : " + msg.content + "\n");
                       // else if(ui.arr[msg.to][msg.from] == false)
                       // ui.chatwindow[msg.to][msg.from].messageScreen.append("["+msg.sender +" > Me] : " + msg.content + "\n");
                        //ui.chatwindow[msg.to][msg.from].messageScreen.append("["+msg.sender +" > Me] : " + msg.content + "\n");
                        
                    }
                    else{
                        System.out.println("2. " + msg.from + "  " + msg.to);
                        ui.chatwindow[msg.from][msg.to].messageScreen.append("["+ msg.sender +" > "+ msg.recipient +"] : " + msg.content + "\n");
                        //ui.chatwindow[msg.to][msg.from].messageScreen.append("["+ msg.sender +" > "+ msg.recipient +"] : " + msg.content + "\n");
                        
                    }
                                            
                    
                }
                else if(msg.type.equals("login")) {
                    if(msg.content.equals("TRUE")){
                        flag = false;
                        System.out.println("Login Successful");
                    }
                    else{
                        flag = true;
                        System.out.println("Login Failed");
                    }
                }
                else if(msg.type.equals("test")){
                    ui.connectButton.setEnabled(false);
                    
                }
                else if(msg.type.equals("newuser")){
                    if(!msg.content.equals(ui.username)){
                        boolean exists = false;
                        for(int i = 0; i < ui.model.getSize(); i++){
                            if(ui.model.getElementAt(i).equals(msg.content)){
                                exists = true; break;
                            }
                        }
                        if(!exists){ ui.model.addElement(msg.content); }
                    }
                }
                else if(msg.type.equals("signup")){
                    if(msg.content.equals("TRUE")) {
                        System.out.println("Signup Successful");
                    }
                    else
                        System.out.println("Signup Failed");
                             
                        
                    
                }
                
                else if(msg.type.equals("signout")){
                    
                }
                else if(msg.type.equals("upload_req")){
                    if(JOptionPane.showConfirmDialog(ui, ("Accept '"+msg.content+"' from "+msg.sender+" ?")) == 0){
                        
                        JFileChooser jf = new JFileChooser();
                        jf.setSelectedFile(new File(msg.content));
                        int returnVal = jf.showSaveDialog(ui);
                       
                        String saveTo = jf.getSelectedFile().getPath();
                        if(saveTo != null && returnVal == JFileChooser.APPROVE_OPTION){
                            Download dwn = new Download(saveTo, ui);
                            Thread t = new Thread(dwn);
                            t.start();
                            //send(new Message("upload_res", (""+InetAddress.getLocalHost().getHostAddress()), (""+dwn.port), msg.sender));
                            send(new Message("upload_res", ui.username, (""+dwn.port), msg.sender, index, index2));
                        }
                        else{
                            send(new Message("upload_res", ui.username, "NO", msg.sender, index, index2));
                        }
                    }
                    else{
                        send(new Message("upload_res", ui.username, "NO", msg.sender, index, index2));
                    }
                    
                }
                else if(msg.type.equals("upload_res")){
                   if(!msg.content.equals("NO")){
                        int port  = Integer.parseInt(msg.content);
                        String addr = msg.sender;
                       
                        Upload upl = new Upload(addr, port, chatWindow.file, ui);
                        Thread t = new Thread(upl);
                        t.start();
                    }
                    else{
                        ui.messageScreen.append("[SERVER > Me] : "+msg.sender+" rejected file request\n");
                    }
                }
            }
            catch(Exception ex) {
                keepRunning = false;
                //ui.jTextArea1.append("[Application > Me] : Connection Failure\n");
                
                
                ui.clientThread.stop();
                
                System.out.println("Exception SocketClient run()");
                ex.printStackTrace();
            }
        }
    }
    
    public void send(Message msg){
        try {
            Out.writeObject(msg);
            Out.flush();
            System.out.println("Outgoing : "+msg.toString());
            
            /*if(msg.type.equals("message") && !msg.content.equals(".bye")){
                String msgTime = (new Date()).toString();
                try{
                    //hist.addMessage(msg, msgTime);               
                    //DefaultTableModel table = (DefaultTableModel) ui.historyFrame.jTable1.getModel();
                    //table.addRow(new Object[]{"Me", msg.content, msg.recipient, msgTime});
                }
                catch(Exception ex){}
            }*/
        } 
        catch (IOException ex) {
            System.out.println("Exception SocketClient send()");
        }
    }
    
    public void closeThread(Thread t){
        t = null;
    }
}
