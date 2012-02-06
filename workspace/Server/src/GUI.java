import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;


public class GUI extends JFrame{
   
   private int port = 8888;

   private static final long serialVersionUID = 1L;
   private JTextField portField;
   private JTextArea output;
   private JButton startButton;
   private JScrollPane scrollPane;
//   private JButton stopButton;
      
   public GUI() {
      super("Server");
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setSize(500, 400);
      this.setLayout(new BorderLayout());
      JPanel portPanel = new JPanel();
//      JPanel outputPanel = new JPanel();
      JPanel buttonPanel = new JPanel();
      JLabel portLabel = new JLabel();
      portLabel.setText("port:");
      portPanel.add(portLabel);
      portField = new JTextField();
      portField.setText("" + port);
      portPanel.add(portField);
      output = new JTextArea();
      output.setSize(480,300);
      output.setBackground(Color.white);
      output.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
      startButton = new JButton("Start Server");
//      stopButton = new JButton("Stop Server");
      buttonPanel.add(startButton);
//      buttonPanel.add(stopButton);
      startButton.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent e) {
             Controller.getInstance().startServer();
         }
      });
//      stopButton.addActionListener(new ActionListener(){
//         public void actionPerformed(ActionEvent e) {
//             server = new MultiThreadedServer();
//             server.close();
//         }
//      });
      
      this.add(portPanel, BorderLayout.NORTH);
      scrollPane = new JScrollPane(output);
      //outputPanel.add(scrollPane);
      this.add(scrollPane, BorderLayout.CENTER);
      this.add(buttonPanel, BorderLayout.SOUTH);
      
      //this.pack();
      this.setVisible(true);
   }
   
   public void message(String text) {
      output.append(text+"\n");
      output.setCaretPosition( output.getText().length() );
   }
   
   public int getPort() {
      int port;
      try{
         port = Integer.parseInt(portField.getText());
      } catch (Exception e){
         message("Error: ports are Numbers (1-20000)");
         port = -1;
      }
      return port;
   }
}
