package Chapter02;

import java.io.*;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;

public class CreateAndReadSeqFile extends JFrame implements ActionListener{

	private TextField account, name, balance, phone;
	private Button enter, print;
	private DataOutputStream output;
	private DataInputStream input;
	private String filename = "client.txt";
	
	public CreateAndReadSeqFile() {
		super("������");
		try {
			output = new DataOutputStream(new FileOutputStream(filename));
			input = new DataInputStream(new FileInputStream(filename));
			
		}catch (IOException e) {
			System.err.println(e.toString());
			System.exit(1);
		}
		setSize(250, 130);
		setLayout(new GridLayout(5, 2));
		add(new Label("���¹�ȣ"));
		account = new TextField(20);
		add(account);
		add(new Label("�̸�"));
		name = new TextField(20);
		add(name);
		add(new Label("�ܰ�"));
		balance = new TextField(20);
		add(balance);
		add(new Label("��ȭ��ȣ"));
		phone= new TextField(20);
		add(phone);
		
		enter = new Button("�Է�");
		enter.addActionListener(this);
		add(enter);
		print = new Button("���");
		print.addActionListener(this);
		add(print);
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void addRecord() {
		int accountNo, phoneNum = 0;
		String d;
		if (!account.getText().equals("")) {
			try {
				accountNo = Integer.parseInt(account.getText());
				phoneNum = Integer.parseInt(phone.getText());
				
				if(accountNo > 0) {
					output.writeInt(accountNo);
					output.writeUTF(name.getText());
					d = balance.getText();
					output.writeDouble(Double.valueOf(d));
					output.writeInt(phoneNum);
				}
				account.setText("");
				name.setText("");
				balance.setText("");
				phone.setText("");
			}catch (NumberFormatException nfe) {
				System.err.println("������ �Է��ؾ� �մϴ�.");
				
			}catch (IOException io) {
				System.err.println(io.toString());
				System.exit(1);
			}
		}
	}
	
	public void readRecord() {
		int accountNo, phoneNum;
		double readBalance;
		String namedata;

		try {
			
			do {
				accountNo = input.readInt();
				namedata = input.readUTF();
				readBalance = input.readDouble();
				phoneNum = input.readInt();
			}while (Integer.parseInt(account.getText()) != accountNo);
			
			account.setText(String.valueOf(accountNo));
			name.setText(namedata);
			balance.setText(String.valueOf(readBalance));
			phone.setText(String.valueOf(phoneNum));
			
		}catch (EOFException eof) {
			try {
				input = new DataInputStream(new FileInputStream(filename)); // ��� ��ȸ�� �� �ֵ��� �ٽ� ����
			}catch (FileNotFoundException fnf) {}
			
		}catch (IOException io) {
			System.err.println(io.toString());
			System.exit(1);
		}
	}
	
	/*public void closeFile() {
		try {
			input.close();
			System.exit(0);
		} catch (IOException io) {
			System.err.println(io.toString());
			System.exit(1);
		}
	}*/
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == enter) addRecord();
		else  readRecord();
	}
	
	public static void main(String[] args) {
		new CreateAndReadSeqFile();
	}
}
