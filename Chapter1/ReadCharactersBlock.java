package Chapter1;
import java.io.*;

public class ReadCharactersBlock {

	public static void main(String args[]) throws java.io.IOException {
		byte[] buffer = new byte[80];
		int numberRead;
		while((numberRead = System.in.read(buffer)) >= 0) {
			System.out.write(buffer, 0, numberRead);
			System.out.println("ReadLength : " + numberRead); // �� +2�� ���� ��µǴ���
		}
	}
}
