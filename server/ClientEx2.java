import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientEx2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BufferedReader in = null;
		BufferedWriter out = null;
		Socket socket = null;
		Scanner scanner = new Scanner(System.in);
		try {
			socket = new Socket("localhost",9998);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			while(true) {
				System.out.println("send >>");
				String outputMessage = scanner.nextLine();
				if(outputMessage.equalsIgnoreCase(outputMessage)) {
					out.write(outputMessage + "\n");
					out.flush();
				}
				out.write(outputMessage);
				out.flush();
				String inputMessage = in.readLine();
				System.out.println("sever "+ inputMessage);
				
			}
		}catch(IOException e) {
			System.out.println(e.getMessage());
		}finally {
			try {
				scanner.close();
				socket.close();
			}catch(IOException e) {
				System.out.println("error");
			}
		}
	}

}
