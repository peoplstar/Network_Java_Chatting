package Chapter3;

import java.io.*;
import java.util.RandomAccess;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;

public class RandomAccount extends JFrame implements ActionListener{

   private TextField accountField, nameField, balanceField;
   private Button enter, print;
   private RandomAccessFile output;
   //private RandomAccessFile input;
   private Record data;
   public RandomAccount() {
      super("고객파일");
      data = new Record();
      try {
         output = new RandomAccessFile("client.txt", "rw");
         //input = new RandomAccessFile("client.txt", "rw");
         
      }catch (IOException e) {
         System.err.println(e.toString());
         System.exit(1);
      }
      setSize(300, 150);
      setLayout(new GridLayout(4, 2));
      add(new Label("계좌번호"));
      accountField = new TextField(20);
      add(accountField);
      add(new Label("이름"));
      nameField = new TextField(20);
      add(nameField);
      add(new Label("잔고"));
      balanceField = new TextField(20);
      add(balanceField);
      
      enter = new Button("입력");
      enter.addActionListener(this);
      add(enter);
      print = new Button("출력");
      print.addActionListener(this);
      add(print);
      
      setVisible(true);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }
   
   public void addRecord() {
      int accountNo = 0;
      Double d;
      if (!accountField.getText().equals("")) {
         try {
            accountNo = Integer.parseInt(accountField.getText());
            
            if(accountNo > 0 && accountNo <= 100) {
               data.setAccount(accountNo);
               data.setName(nameField.getText());
               d = Double.parseDouble(balanceField.getText());
               data.setBalance(d.doubleValue());
               output.seek((long) (accountNo-1) * Record.size());
               data.write(output);
            }
            accountField.setText("");
            nameField.setText("");
            balanceField.setText("");
         }catch (NumberFormatException nfe) {
            System.err.println("숫자를 입력해야 합니다.");
            
         }catch (IOException io) {
            System.err.println("파일쓰기 에러\n" + io.toString());
            System.exit(1);
         }
      }
   }
   
   public void readRecord() {
      int accountNo;
      double readBalance;
      String namedata;
      int count = 0;
       
            if(accountField.getText().equals("")) { // 이름으로 Record 출력

            	while(!((nameField.getText()).equals(data.getName().trim()))){
            		try {
            			
            			output.seek(42*(count));
            			data.read(output);
            			accountNo = data.getAccount();
            	        namedata = data.getName();
            	        readBalance = data.getBalance();    
           	        
            			count++;      			           			
            		} catch(IOException e) {}
            	}
            	try {
	            	output.seek(42*(--count));
	            	data.read(output);
        			accountNo = data.getAccount();
        	        namedata = data.getName();
        	        readBalance = data.getBalance();
	            	accountField.setText(String.valueOf(accountNo));
	    	        nameField.setText(namedata);
	    	        balanceField.setText(String.valueOf(readBalance));
	    	        

            	} catch (IOException e) {}
            	try { // output의 포인터 초기화
                    output.seek(0);
                } catch (IOException e) {
                }
            }
            else /*if(nameField.getText().equals(""))*/ { // 계좌로 Record 출력

            	while(!((Integer.parseInt(accountField.getText())) == (data.getAccount()))) {
            		try {
            			output.seek(42*count);
            			data.read(output);
            			accountNo = data.getAccount();
            	        namedata = data.getName();
            	        readBalance = data.getBalance();    	        
            			count++;
            		} catch(IOException e) {

            		}
            	}
            	try {
	            	output.seek(42*(--count));
	            	data.read(output);
        			accountNo = data.getAccount();
        	        namedata = data.getName();
        	        readBalance = data.getBalance();
	            	accountField.setText(String.valueOf(accountNo));
	    	        nameField.setText(namedata);
	    	        balanceField.setText(String.valueOf(readBalance));
            	} catch (IOException e) {

            	}       	
            }
            try { // output의 포인터 초기화
                output.seek(0);
            } catch (IOException e) {

            }
   }
   
   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == enter) addRecord();
      else  readRecord();
   }
   
   public static void main(String[] args) {
      new RandomAccount();
   }
   
}
class Record {
   private int account;
   private String name;
   private double balance;
   
   public void read(RandomAccessFile file) throws IOException {
      account = file.readInt();
      char namearray[] = new char[15];
      for (int i = 0; i < namearray.length; i++) {
         namearray[i] = file.readChar();
      }
      name = new String(namearray);
      balance = file.readDouble();
   }
   
   public void write(RandomAccessFile file) throws IOException {
      StringBuffer buf;
      file.writeInt(account);
      if (name != null)
         buf = new StringBuffer(name);
      else
         buf = new StringBuffer(15);
      buf.setLength(15);
      file.writeChars(buf.toString());
      file.writeDouble(balance);
   }
   
   public void setAccount(int a) { account = a; }
   public int getAccount() { return account; }
   public void setName(String f) { name = f; }
   public String getName() { return name; }
   public void setBalance(double b) { balance = b; }
   public double getBalance() { return balance; }
   public static int size() { return 42; }
   
}