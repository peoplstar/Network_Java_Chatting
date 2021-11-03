package Chapter1;
import java.io.*;

public class ReadCharacter {

	public static void main(String[] args) throws java.io.IOException {
		int data;
		while((data =System.in.read()) >= 0) {
			System.out.write(data);
		}
		System.out.println("Terminated");
	}

}
