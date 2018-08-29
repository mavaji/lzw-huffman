package lzwhuffman;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AboutDialog extends JDialog {
    private JPanel panel1 = new JPanel();
    private JLabel jLabel1 = new JLabel();
    private JLabel jLabel2 = new JLabel();
    private JLabel jLabel3 = new JLabel();
    private JButton jButton1 = new JButton();

    public AboutDialog(Frame frame, String title, boolean modal) {
        super(frame, title, modal);
        try {
            jbInit();
            pack();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public AboutDialog() {
        this(null, "", false);
    }

    private void jbInit() throws Exception {
        panel1.setLayout(null);
        this.setModal(true);
        this.setResizable(false);
        this.setTitle("About...");
        this.getContentPane().setLayout(null);
        panel1.setBackground(SystemColor.desktop);
        panel1.setBounds(new Rectangle(0, 0, 400, 300));
        jLabel1.setBackground(Color.lightGray);
        jLabel1.setFont(new java.awt.Font("Dialog", 1, 16));
        jLabel1.setForeground(Color.cyan);
        jLabel1.setText("Compressing Files");
        jLabel1.setBounds(new Rectangle(52, 0, 186, 39));
        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14));
        jLabel2.setForeground(Color.white);
        jLabel2.setText("Written By Vahid Mavaji");
        jLabel2.setBounds(new Rectangle(45, 22, 176, 41));
        jLabel3.setFont(new java.awt.Font("Dialog", 1, 12));
        jLabel3.setForeground(Color.green);
        jLabel3.setText("Tehran , March 2003");
        jLabel3.setBounds(new Rectangle(63, 46, 140, 40));
        jButton1.setBackground(SystemColor.inactiveCaption);
        jButton1.setBounds(new Rectangle(82, 79, 79, 27));
        jButton1.setForeground(Color.white);
        jButton1.setText("OK");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButton1_actionPerformed(e);
            }
        });
        getContentPane().add(panel1, null);
        panel1.add(jLabel1, null);
        panel1.add(jLabel2, null);
        panel1.add(jLabel3, null);
        panel1.add(jButton1, null);
    }

    void jButton1_actionPerformed(ActionEvent e) {
        this.dispose();
    }
}