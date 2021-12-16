package Chapter12;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*; 

// protocol 읽어오는 메소드 존재 X
public class HeaderViewer extends Frame implements ActionListener{
	private TextField enter;
	private TextArea contents;
	
	public HeaderViewer() {
		super("HeaderViewer");
		setLayout(new BorderLayout());
		enter = new TextField("URL을 입력하세요.");
		enter.addActionListener(this);
		add(enter, BorderLayout.NORTH);
		contents = new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
		add(contents, BorderLayout.CENTER);
		addWindowListener(new WinListener());
		setSize(400, 400);
		setVisible(true);
	}
	public static void main(String[] args) {
		HeaderViewer hv = new HeaderViewer();
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		URL url;
		URLConnection uc;
		InputStream is;
		BufferedReader input;
		String line;
		StringBuffer buffer = new StringBuffer();
		String location = ae.getActionCommand();
		try {
			url = new URL(location);
			uc = url.openConnection();
			HttpURLConnection httpuc = (HttpURLConnection) url.openConnection();
			
			is = uc.getInputStream();
			input = new BufferedReader(new InputStreamReader(is));
			contents.setText("파일을 읽어오는 중입니다....");
			int i = 1;
			buffer.append("HTTP/1.x " + httpuc.getResponseCode() + " ").append(httpuc.getResponseMessage()).append('\n');
			do {
				buffer.append(uc.getHeaderFieldKey(i) + " : ").append(uc.getHeaderField(i)).append('\n');
				i++;
			} while ((uc.getHeaderField(i) != null) && (uc.getHeaderFieldKey(i) != null));
			buffer.append('\n');
			
			while((line = input.readLine()) != null) {
				buffer.append(line).append('\n');
			}
			contents.setText(buffer.toString());
			input.close();
		} catch(MalformedURLException mal) {
			mal.printStackTrace();
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	class WinListener extends WindowAdapter {
		public void windowClosing(WindowEvent we) {
			System.exit(0);
		}
	}
	

}
