package Chapter1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class GUIObject extends JFrame implements ActionListener {

   JTextField input, output;
   JTextArea panelTextArea;
   
   public GUIObject() {
      super("GUI Object");
      this.setLayout(new FlowLayout());
      
      JPanel panelName = new JPanel();
      panelName.setLayout(new BoxLayout(panelName, BoxLayout.Y_AXIS)); 
      JPanel panelField = new JPanel();
      panelField.setLayout(new BoxLayout(panelField, BoxLayout.Y_AXIS));
      JPanel panelBox = new JPanel();
      panelBox.setLayout(new FlowLayout());
      
      JPanel top = new JPanel();
      
      input = new JTextField(20);
      output = new JTextField(20);
      JPanel bottom = new JPanel();
      Button checking = new Button("확인");
      checking.addActionListener(this);
      
      panelName.add(new JLabel("입력파일"));
      panelName.add(new JLabel("출력파일"));
      panelField.add(input);
      panelField.add(output);
      panelBox.add(checking);
      
      top.setLayout(new BorderLayout()); 
      top.add(panelName, BorderLayout.WEST);
      top.add(panelBox, BorderLayout.EAST);
      top.add(panelField, BorderLayout.CENTER);

      JPanel panelFile = new JPanel();
      panelFile.add(new JLabel("파일내용"));
      //JPanel Area = new JPanel();
      panelTextArea = new JTextArea(7,30);
      //Area.add(panelTextArea);
         
      JScrollPane sc = new JScrollPane(panelTextArea);
      sc.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

      bottom.setLayout(new BorderLayout());
      bottom.add(panelFile, BorderLayout.NORTH);
      bottom.add(sc, BorderLayout.CENTER);
      
      this.add(top);
      this.add(bottom);
      
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 프로세스 종료 후 이후 오류 제거
      setSize(400, 450);
      setVisible(true);
   }
   
   public void actionPerformed(ActionEvent ae) {
      int bytesRead;
      byte[] buffer = new byte[256];
      FileInputStream fin = null;
      FileOutputStream fout = null;
      
      try {
         fin = new FileInputStream(input.getText());
         fout = new FileOutputStream(output.getText());
         while((bytesRead = fin.read(buffer)) >= 0) {
            fout.write(buffer, 0, bytesRead);
         }
         String text = new String(buffer);
         String NewTest= text.trim();
         panelTextArea.append(NewTest);
         
      } catch (IOException e){
         System.err.println("스트림으로부터 데이터를 읽을 수 없습니다.");
      } finally {
         try {
            if (fin != null) fin.close();
            if (fout != null) fout.close();
         } catch (IOException e) {}
      }
   }

   public static void main(String [] args) {
      GUIObject obj = new GUIObject();
   }
}
