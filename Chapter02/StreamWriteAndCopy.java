package Chapter02;

import java.io.*;


public class StreamWriteAndCopy extends FilterOutputStream{
	
	FileOutputStream fout1;
	FileOutputStream fout2;
	
	public StreamWriteAndCopy(String args[]) {
		super(null);
		try {
			fout1 = new FileOutputStream(args[1]);
			fout2 = new FileOutputStream(args[2]);
		} catch (FileNotFoundException fnf) { 
			System.err.println("파일을 찾지 못했습니다.");
		}
		
	}
	
	public static void copy(InputStream in, OutputStream out) throws IOException { // BufferInputStream.copy();
		synchronized (in) {
			synchronized (out) {
				BufferedInputStream bin = new BufferedInputStream(in);
				BufferedOutputStream bout = new BufferedOutputStream(out);
				while(true) {
					int data = bin.read();
					if (data == -1) break;
					bout.write(data);
				}
			bout.flush();	
			}
		}
	}
	
	@Override
	public void write(int b) throws IOException{
		out = fout1;
		super.write(b);
		out = fout2;
		super.write(b);
	}
	
	@Override
	public void write(byte[] data) throws IOException{
		out = fout1;
	    super.write(data);
	    out = fout2;
	    super.write(data);
	}
	
	@Override
	public void flush() throws IOException{
		out = fout1;
		super.flush();
		out = fout2;
		super.flush();
	}
	
	@Override
	public void close() throws IOException{
		out = fout1;
		super.close();
		out = fout2;
		super.close();
	}
	
	public static void main(String[] args) {
		StreamWriteAndCopy WACStream = new StreamWriteAndCopy(args);
		
		try {
			FileInputStream FIStream = new FileInputStream(args[0]);
			copy(FIStream, WACStream);		// BufferInputStream.copy();
		} catch (FileNotFoundException fnf) { 
			System.err.println("파일을 찾지 못했습니다.");
		} catch (IOException e) {
			System.err.println(e);
		}
	}
}
