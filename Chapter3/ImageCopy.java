package Chapter3;

import java.io.*;
import java.util.Scanner;

public class ImageCopy{

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String jpgname = in.nextLine();
		File filename = new File(jpgname);
		byte buf[] = new byte[1024];
		int count = 0, star = 1;
		double percent = 0;
		
		//if (filename.exists()) {
			try {
				RandomAccessFile rafjpg = new RandomAccessFile(jpgname, "rw");
				RandomAccessFile rafcopy = new RandomAccessFile("copy.jpg", "rw");
				
				rafjpg.seek(0);
				
				double lfilesize = rafjpg.length();
				
				while(rafjpg.read(buf) != -1) {
					rafcopy.write(buf);
					//rafcopy.seek(1024*count++); // buf로 write , read 하면서 자동으로 포인터 저장
					//rafjpg.seek(1024*count);
					
					percent =  (double) (count * 1024 / lfilesize * 100 ); 
					
					if (((int) (percent) % 10) == 0 && (int) (percent) != 0) {
						if(star * 10 == (int) percent) {
							star++;
							System.out.print("*");
						}
					}
				}
				
			} catch (FileNotFoundException fnf) {
				
			  } 
			  catch (IOException e) {			  
			  }		
		//}		
	}	
}
