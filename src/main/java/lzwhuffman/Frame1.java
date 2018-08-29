package lzwhuffman;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class Frame1 extends JFrame {
    Huffman huffman = new Huffman();
    LZW lzw = new LZW();

    private String SourceFileName = new String();
    private String DestinationFileName = new String();

    public void OpenToCompress() {
        int n;
        do {
            n = jFileChooser1.showOpenDialog(this);
            if (n == JFileChooser.APPROVE_OPTION && jFileChooser1.getSelectedFile().exists()
                    || n == JFileChooser.CANCEL_OPTION) break;
        } while (true);
        if (n == JFileChooser.APPROVE_OPTION) {
            SourceFileName = jFileChooser1.getSelectedFile().getPath();
            CompressMenu.setEnabled(true);
            DeCompressMenu.setEnabled(false);
            SourceTextField.setText(SourceFileName);
            CompressRadioButton.setSelected(true);
        }
    }

    public void OpenToDecompress() {
        int n;
        do {
            n = jFileChooser1.showOpenDialog(this);
            if (n == JFileChooser.APPROVE_OPTION && jFileChooser1.getSelectedFile().exists()
                    || n == JFileChooser.CANCEL_OPTION) break;
        } while (true);
        if (n == JFileChooser.APPROVE_OPTION) {
            SourceFileName = jFileChooser1.getSelectedFile().getPath();
            DeCompressMenu.setEnabled(true);
            CompressMenu.setEnabled(false);
            SourceTextField.setText(SourceFileName);
            DecompressRadioButton.setSelected(true);
        }
    }

    public void DoCompress() {
        if (JFileChooser.APPROVE_OPTION == jFileChooser1.showSaveDialog(this)) {
            DestinationFileName = jFileChooser1.getSelectedFile().getPath();
            if (HuffmanCheckBoxMenuItem.isSelected())
                huffman.Compress(SourceFileName, DestinationFileName);
            else if (LZWCheckBoxMenuItem.isSelected())
                lzw.Compress(SourceFileName, DestinationFileName);
        }
    }

    public void DoDecompress() {
        if (JFileChooser.APPROVE_OPTION == jFileChooser1.showSaveDialog(this)) {
            DestinationFileName = jFileChooser1.getSelectedFile().getPath();
            if (HuffmanCheckBoxMenuItem.isSelected())
                huffman.DeCompress(SourceFileName, DestinationFileName);
            else if (LZWCheckBoxMenuItem.isSelected())
                lzw.DeCompress(SourceFileName, DestinationFileName);
        }
    }

    private JPanel contentPane;
    private JFileChooser jFileChooser1 = new JFileChooser();
    private JMenuBar jMenuBar1 = new JMenuBar();
    private JMenu FileMenuBar = new JMenu();
    private JMenu OpenFileMenu = new JMenu();
    private JMenuItem toCompressItem = new JMenuItem();
    private JMenuItem toDeCompressItem = new JMenuItem();
    private JMenuItem CompressMenu = new JMenuItem();
    private JMenuItem DeCompressMenu = new JMenuItem();
    private JMenuItem ExitMenu = new JMenuItem();


    private Border border1;
    private JMenu MethodMenu = new JMenu();
    private JMenu jMenu2 = new JMenu();
    private JColorChooser jColorChooser1 = new JColorChooser();
    private TitledBorder titledBorder1;
    private TitledBorder titledBorder2;


    private JCheckBoxMenuItem HuffmanCheckBoxMenuItem = new JCheckBoxMenuItem();
    private JCheckBoxMenuItem LZWCheckBoxMenuItem = new JCheckBoxMenuItem();
    private JTextField SourceTextField = new JTextField();
    private JTextField DestinationTextField = new JTextField();
    private JButton BrowseSource = new JButton();
    private JButton BrowseDestination = new JButton();
    private ButtonGroup buttonGroup1 = new ButtonGroup();
    private JRadioButton CompressRadioButton = new JRadioButton();
    private JRadioButton DecompressRadioButton = new JRadioButton();
    private JLabel jLabel1 = new JLabel();
    private JLabel jLabel2 = new JLabel();
    private JButton DoButton = new JButton();
    private JMenuItem jMenuItem1 = new JMenuItem();

    public Frame1() {
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {

        contentPane = (JPanel) this.getContentPane();
        border1 = BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.white, Color.white, new Color(115, 114, 105), new Color(165, 163, 151));
        titledBorder1 = new TitledBorder("");
        titledBorder2 = new TitledBorder("");
        contentPane.setLayout(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setJMenuBar(jMenuBar1);
        this.setResizable(false);
        this.setSize(new Dimension(520, 270));
        this.setTitle("Vahid Mavaji\'s Program for Compressing Files");
        jFileChooser1.setBounds(new Rectangle(0, 0, 425, 245));
        FileMenuBar.setText("File");
        OpenFileMenu.setText("Open File...");
        toCompressItem.setText(" to Compress");

        SourceFileName = null;
        DestinationFileName = null;

        toCompressItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                toCompressItem_actionPerformed(e);
            }
        });
        toDeCompressItem.setText(" to Decompress");
        toDeCompressItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                toDeCompressItem_actionPerformed(e);
            }
        });
        CompressMenu.setEnabled(false);
        CompressMenu.setText("Compress");
        CompressMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CompressMenu_actionPerformed(e);
            }
        });
        DeCompressMenu.setEnabled(false);
        DeCompressMenu.setText("Decompress");
        DeCompressMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DeCompressMenu_actionPerformed(e);
            }
        });
        ExitMenu.setText("Exit");
        ExitMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ExitMenu_actionPerformed(e);
            }
        });
        MethodMenu.setText("Method");
        jMenu2.setText("Help");
        jColorChooser1.setBounds(new Rectangle(128, 45, 429, 347));
        contentPane.setBackground(SystemColor.inactiveCaption);
        contentPane.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void inputMethodTextChanged(InputMethodEvent e) {
            }

            public void caretPositionChanged(InputMethodEvent e) {

            }
        });
        HuffmanCheckBoxMenuItem.setText("Huffman");
        HuffmanCheckBoxMenuItem.setState(true);
        HuffmanCheckBoxMenuItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                HuffmanCheckBoxMenuItem_mouseReleased(e);
            }
        });
        LZWCheckBoxMenuItem.setText("LZW");
        LZWCheckBoxMenuItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                LZWCheckBoxMenuItem_mouseReleased(e);
            }
        });
        LZWCheckBoxMenuItem.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(ChangeEvent e) {

            }
        });
        SourceTextField.setBounds(new Rectangle(160, 84, 232, 25));
        DestinationTextField.setBounds(new Rectangle(160, 121, 232, 25));
        BrowseSource.setBackground(SystemColor.inactiveCaptionText);
        BrowseSource.setBounds(new Rectangle(400, 84, 79, 27));
        BrowseSource.setText("Browse");
        BrowseSource.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                BrowseSource_actionPerformed(e);
            }
        });
        BrowseDestination.setBackground(SystemColor.inactiveCaptionText);
        BrowseDestination.setBounds(new Rectangle(401, 121, 79, 27));
        BrowseDestination.setText("Browse");
        BrowseDestination.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                BrowseDestination_actionPerformed(e);
            }
        });
        CompressRadioButton.setBackground(SystemColor.inactiveCaption);
        CompressRadioButton.setForeground(Color.cyan);
        CompressRadioButton.setSelected(true);
        CompressRadioButton.setText("Compress");
        CompressRadioButton.setBounds(new Rectangle(165, 45, 103, 25));
        CompressRadioButton.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                CompressRadioButton_itemStateChanged(e);
            }
        });
        DecompressRadioButton.setBackground(SystemColor.inactiveCaption);
        DecompressRadioButton.setForeground(Color.cyan);
        DecompressRadioButton.setText("DeCompress");
        DecompressRadioButton.setBounds(new Rectangle(287, 44, 103, 25));
        jLabel1.setForeground(Color.white);
        jLabel1.setText("Source  File   Name     :");
        jLabel1.setBounds(new Rectangle(29, 86, 127, 20));
        jLabel2.setBounds(new Rectangle(24, 123, 131, 22));
        jLabel2.setForeground(Color.white);
        jLabel2.setText("Destination File Name :");
        DoButton.setBackground(SystemColor.inactiveCaptionText);
        DoButton.setBounds(new Rectangle(232, 161, 79, 27));
        DoButton.setText("DO !");
        DoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DoButton_actionPerformed(e);
            }
        });
        jMenuItem1.setText("About...");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jMenuItem1_actionPerformed(e);

            }
        });
        jMenuBar1.add(FileMenuBar);
        jMenuBar1.add(MethodMenu);
        jMenuBar1.add(jMenu2);
        FileMenuBar.add(OpenFileMenu);
        FileMenuBar.add(CompressMenu);
        FileMenuBar.add(DeCompressMenu);
        FileMenuBar.addSeparator();
        FileMenuBar.add(ExitMenu);
        OpenFileMenu.add(toCompressItem);
        OpenFileMenu.add(toDeCompressItem);
        MethodMenu.add(HuffmanCheckBoxMenuItem);
        MethodMenu.add(LZWCheckBoxMenuItem);
        contentPane.add(DecompressRadioButton, null);
        contentPane.add(CompressRadioButton, null);
        contentPane.add(SourceTextField, null);
        contentPane.add(BrowseSource, null);
        contentPane.add(DestinationTextField, null);
        contentPane.add(BrowseDestination, null);
        contentPane.add(jLabel1, null);
        contentPane.add(jLabel2, null);
        contentPane.add(DoButton, null);
        buttonGroup1.add(CompressRadioButton);
        buttonGroup1.add(DecompressRadioButton);
        jMenu2.add(jMenuItem1);
    }

    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            System.exit(0);
        }
    }

    void toCompressItem_actionPerformed(ActionEvent e) {
        OpenToCompress();
    }

    void toDeCompressItem_actionPerformed(ActionEvent e) {
        OpenToDecompress();
    }

    void CompressMenu_actionPerformed(ActionEvent e) {
        DoCompress();
    }

    void DeCompressMenu_actionPerformed(ActionEvent e) {
        DoDecompress();
    }

    void ExitMenu_actionPerformed(ActionEvent e) {
        System.exit(0);
    }


    void BrowseSource_actionPerformed(ActionEvent e) {
        if (CompressRadioButton.isSelected())
            OpenToCompress();
        else
            OpenToDecompress();

    }

    void BrowseDestination_actionPerformed(ActionEvent e) {
        if (JFileChooser.APPROVE_OPTION == jFileChooser1.showSaveDialog(this)) {
            DestinationFileName = jFileChooser1.getSelectedFile().getPath();
            DestinationTextField.setText(DestinationFileName);
        }
    }

    void DoButton_actionPerformed(ActionEvent e) {
        SourceFileName = SourceTextField.getText();

        DestinationFileName = DestinationTextField.getText();
        if (new File(SourceFileName).exists() && DestinationFileName != null) {
            if (CompressRadioButton.isSelected()) {
                if (HuffmanCheckBoxMenuItem.isSelected())
                    huffman.Compress(SourceFileName, DestinationFileName);
                else if (LZWCheckBoxMenuItem.isSelected())
                    lzw.Compress(SourceFileName, DestinationFileName);
            } else if (DecompressRadioButton.isSelected()) {
                if (HuffmanCheckBoxMenuItem.isSelected())
                    huffman.DeCompress(SourceFileName, DestinationFileName);
                else if (LZWCheckBoxMenuItem.isSelected())
                    lzw.DeCompress(SourceFileName, DestinationFileName);
            }
        }
    }


    void CompressRadioButton_itemStateChanged(ItemEvent e) {
        if (SourceFileName == null) return;
        if (CompressMenu.isEnabled() && CompressRadioButton.isSelected() == false) {
            CompressMenu.setEnabled(false);
            DeCompressMenu.setEnabled(true);
        } else if (CompressMenu.isEnabled() == false && CompressRadioButton.isSelected()) {
            CompressMenu.setEnabled(true);
            DeCompressMenu.setEnabled(false);
        }

    }

    void HuffmanCheckBoxMenuItem_mouseReleased(MouseEvent e) {
        if (LZWCheckBoxMenuItem.getState() == true)
            LZWCheckBoxMenuItem.setState(false);
    }

    void LZWCheckBoxMenuItem_mouseReleased(MouseEvent e) {
        if (HuffmanCheckBoxMenuItem.getState() == true)
            HuffmanCheckBoxMenuItem.setState(false);
    }

    void jMenuItem1_actionPerformed(ActionEvent e) {
        AboutDialog a = new AboutDialog();
        a.setLocation(this.getX() + 100, this.getY() + 100);
        a.setSize(250, 150);
        a.show();
    }

}


