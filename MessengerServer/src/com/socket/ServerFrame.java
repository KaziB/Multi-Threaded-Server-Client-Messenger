package com.socket;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.UIManager;

public class ServerFrame extends javax.swing.JFrame {

    public SocketServer server;
    public Thread serverThread;
    public String filePath = "D:/Data.xml";
    public JFileChooser fileChooser;
    
    public ServerFrame() {
        initComponents();     
        jTextField3.setEditable(false);
        jTextField3.setBackground(Color.WHITE);
        
        fileChooser = new JFileChooser();
        //jTextArea1.setEditable(false);
    }
    
    public boolean isWin32(){
        return System.getProperty("os.name").startsWith("Windows");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        startServerButton = new javax.swing.JButton();
        jTextField3 = new javax.swing.JTextField();
        browseButton = new javax.swing.JButton();
        ip = new javax.swing.JLabel();
        ipText = new javax.swing.JLabel();
        port = new javax.swing.JLabel();
        portText = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Triply Server");
        setPreferredSize(new java.awt.Dimension(550, 190));
        setSize(new java.awt.Dimension(546, 182));
        getContentPane().setLayout(null);

        startServerButton.setText("Start Server");
        startServerButton.setEnabled(false);
        startServerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startServerButtonActionPerformed(evt);
            }
        });
        getContentPane().add(startServerButton);
        startServerButton.setBounds(445, 36, 91, 23);
        getContentPane().add(jTextField3);
        jTextField3.setBounds(107, 37, 328, 20);

        browseButton.setText("Browse...");
        browseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseButtonActionPerformed(evt);
            }
        });
        getContentPane().add(browseButton);
        browseButton.setBounds(10, 36, 91, 23);

        ip.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ip.setForeground(new java.awt.Color(255, 255, 255));
        ip.setText("IP:");
        getContentPane().add(ip);
        ip.setBounds(71, 93, 20, 14);

        ipText.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ipText.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(ipText);
        ipText.setBounds(107, 93, 294, 25);

        port.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        port.setForeground(new java.awt.Color(255, 255, 255));
        port.setText("Port");
        getContentPane().add(port);
        port.setBounds(71, 136, 30, 14);

        portText.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        portText.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(portText);
        portText.setBounds(118, 136, 271, 23);

        jLabel1.setIcon(new javax.swing.ImageIcon("D:\\2.1_SD\\Messenger\\Messenger\\src\\com\\ui\\backTriply.jpg")); // NOI18N
        jLabel1.setText("jLabel1");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 0, 550, 180);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void startServerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startServerButtonActionPerformed
        server = new SocketServer(this);
        startServerButton.setEnabled(false); 
        browseButton.setEnabled(false);
        
    }//GEN-LAST:event_startServerButtonActionPerformed

    public void RetryStart(int port){
        if(server != null){ 
            server.stop(); 
        }
        server = new SocketServer(this, port);
    }
    
    private void browseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseButtonActionPerformed
        fileChooser.showDialog(this, "Select");
        File file = fileChooser.getSelectedFile();
        
        if(file != null){
            filePath = file.getPath();
            if(this.isWin32()){ filePath = filePath.replace("\\", "/"); }
            jTextField3.setText(filePath);
            startServerButton.setEnabled(true);
        }
    }//GEN-LAST:event_browseButtonActionPerformed

    public static void main(String args[]) {

        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception ex){
            System.out.println("Look & Feel Exception");
        }
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ServerFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton browseButton;
    private javax.swing.JLabel ip;
    public javax.swing.JLabel ipText;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JLabel port;
    public javax.swing.JLabel portText;
    private javax.swing.JButton startServerButton;
    // End of variables declaration//GEN-END:variables
}
