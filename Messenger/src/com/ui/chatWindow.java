/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ui;

import com.socket.Message;
import com.socket.SocketClient;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JLabel;


/**
 *
 * @author Simanta
 */
public class chatWindow extends javax.swing.JFrame {
    chatWindow cw;
    chatWindow cw1;
    LoginScreen ls;
    public SocketClient client;
    public String username = "", recipient;
    public static String target;
    public int index, index2;
    public static File file;
    String user2;
    /**
     * Creates new form chatWindow
     */
    public chatWindow() {
        initComponents();
        
        jScrollPane1.setOpaque(false);
jScrollPane1.getViewport().setOpaque(false);
jScrollPane1.setBorder(null);
jScrollPane1.setViewportBorder(null);

messageScreen.setBorder(null);
messageScreen.setBackground(new Color(0, 0, 0, 0));
    }
    public chatWindow(LoginScreen ls, SocketClient client, String username) {
        initComponents();
        jScrollPane1.setOpaque(false);
jScrollPane1.getViewport().setOpaque(false);
jScrollPane1.setBorder(null);
jScrollPane1.setViewportBorder(null);

messageScreen.setBorder(null);
messageScreen.setBackground(new Color(0, 0, 0, 0));
        this.username = username;
        this.ls = ls;
        this.client = client;
        System.out.println(this.username);
        user2 = username;
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        messageScreen = new javax.swing.JTextArea();
        chatWindowSendButton = new javax.swing.JButton();
        msgBoxField = new javax.swing.JTextField();
        nameLabel = new javax.swing.JLabel();
        browseButton = new javax.swing.JButton();
        fileSendButton = new javax.swing.JButton();
        fileBox = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(380, 560));
        getContentPane().setLayout(null);

        jScrollPane1.setOpaque(false);

        messageScreen.setEditable(false);
        messageScreen.setColumns(20);
        messageScreen.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        messageScreen.setForeground(new java.awt.Color(255, 255, 255));
        messageScreen.setRows(5);
        messageScreen.setOpaque(false);
        jScrollPane1.setViewportView(messageScreen);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(10, 77, 330, 350);

        chatWindowSendButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ui/send-512.png"))); // NOI18N
        chatWindowSendButton.setBorder(null);
        chatWindowSendButton.setBorderPainted(false);
        chatWindowSendButton.setContentAreaFilled(false);
        chatWindowSendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chatWindowSendButtonActionPerformed(evt);
            }
        });
        getContentPane().add(chatWindowSendButton);
        chatWindowSendButton.setBounds(300, 480, 50, 30);

        msgBoxField.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        msgBoxField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                msgBoxFieldActionPerformed(evt);
            }
        });
        getContentPane().add(msgBoxField);
        msgBoxField.setBounds(-1, 470, 300, 50);

        nameLabel.setFont(new Font("Montserrat", Font.ITALIC, 18) );
        nameLabel.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(nameLabel);
        nameLabel.setBounds(30, 10, 234, 38);

        browseButton.setText("Browse");
        browseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseButtonActionPerformed(evt);
            }
        });
        getContentPane().add(browseButton);
        browseButton.setBounds(10, 440, 70, 23);

        fileSendButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ui/send-512.png"))); // NOI18N
        fileSendButton.setBorder(null);
        fileSendButton.setBorderPainted(false);
        fileSendButton.setContentAreaFilled(false);
        fileSendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileSendButtonActionPerformed(evt);
            }
        });
        getContentPane().add(fileSendButton);
        fileSendButton.setBounds(310, 440, 30, 31);
        getContentPane().add(fileBox);
        fileBox.setBounds(90, 440, 210, 20);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ui/chatName.jpg"))); // NOI18N
        getContentPane().add(jLabel2);
        jLabel2.setBounds(0, 0, 360, 65);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ui/chatRoomImage.jpg"))); // NOI18N
        jLabel1.setMaximumSize(new java.awt.Dimension(362, 510));
        jLabel1.setPreferredSize(new java.awt.Dimension(362, 510));
        getContentPane().add(jLabel1);
        jLabel1.setBounds(-6, -6, 390, 530);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void chatWindowSendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chatWindowSendButtonActionPerformed
        
        username = LoginScreen.username;
        
        System.out.println(username);
        String msg = msgBoxField.getText();
        target = nameLabel.getText();
        
        
        index = Character.getNumericValue(username.charAt(0));
        index2 = Character.getNumericValue(target.charAt(0));
        //client.getIndexes(index, index2);
        
        if(!msg.isEmpty() && !target.isEmpty()){
            msgBoxField.setText("");
            System.out.println(username + " " + msg + " " + target + " " + index + " " + index2);
            LoginScreen.client.send(new Message("message", username, msg, target, index, index2));
            System.out.println(index + "  " + index2);
        }
        /*if(arr[index][index2] == false) {
                chatwindow[index][index2] = new chatWindow();
                arr[index][index2] = true;
            
                chatwindow[index][index2].setVisible(true);
            }
        client.getFrame(chatwindow, index, index2);*/
        //LoginScreen.client.getFrame(LoginScreen.chatwindow, index, index2);
        
    }//GEN-LAST:event_chatWindowSendButtonActionPerformed

    private void msgBoxFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_msgBoxFieldActionPerformed
        username = LoginScreen.username;
        
        System.out.println(username);
        String msg = msgBoxField.getText();
        String target = nameLabel.getText();
        
        
        index = Character.getNumericValue(username.charAt(0));
        index2 = Character.getNumericValue(target.charAt(0));
        //client.getIndexes(index, index2);
        
        if(!msg.isEmpty() && !target.isEmpty()){
            msgBoxField.setText("");
            System.out.println(username + " " + msg + " " + target + " " + index + " " + index2);
            LoginScreen.client.send(new Message("message", username, msg, target, index, index2));
            System.out.println(index + "  " + index2);
        }
        /*if(arr[index][index2] == false) {
                chatwindow[index][index2] = new chatWindow();
                arr[index][index2] = true;
            
                chatwindow[index][index2].setVisible(true);
            }
        client.getFrame(chatwindow, index, index2);*/
        //LoginScreen.client.getFrame(LoginScreen.chatwindow, index, index2);
    }//GEN-LAST:event_msgBoxFieldActionPerformed

    private void browseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseButtonActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showDialog(this, "Select File");
        file = fileChooser.getSelectedFile();
        
        if(file != null){
            if(!file.getName().isEmpty()){
                fileSendButton.setEnabled(true); String str;
                
                if(fileBox.getText().length() > 30){
                    String t = file.getPath();
                    str = t.substring(0, 20) + " [...] " + t.substring(t.length() - 20, t.length());
                }
                else{
                    str = file.getPath();
                }
                fileBox.setText(str);
            }
        }
    }//GEN-LAST:event_browseButtonActionPerformed

    private void fileSendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileSendButtonActionPerformed
        long size = file.length();
        String target = nameLabel.getText();
            if(size < 120 * 1024 * 1024){
                LoginScreen.client.send(new Message("upload_req", username, file.getName(), target, index, index2));
            }
            else{
                messageScreen.append("File is size too large\n");
            }
    }//GEN-LAST:event_fileSendButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(chatWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(chatWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(chatWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(chatWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                chatWindow cw = new chatWindow();
                cw.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton browseButton;
    public javax.swing.JButton chatWindowSendButton;
    private javax.swing.JTextField fileBox;
    private javax.swing.JButton fileSendButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTextArea messageScreen;
    public javax.swing.JTextField msgBoxField;
    public javax.swing.JLabel nameLabel;
    // End of variables declaration//GEN-END:variables
}