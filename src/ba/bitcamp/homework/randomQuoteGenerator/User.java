package ba.bitcamp.homework.randomQuoteGenerator;

import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;
import java.io.*;

/**
 * class that represents client side in network communication. It has two constant variables, port number, 
 * and IP address of the server
 * 
 * @author nermin
 * 
 */
public class User {
	public static final int port = 1717;
	public static final String serverIP = "127.0.0.1";

	/**
	 * for connecting to server. It uses SocketRW object for sending and receiving messages to and from server.
	 * First we enter password, if it is correct, the server returns to us random quote. 
	 * Then the this method saves this quote to file received_quotes.
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	private static void connectToServer() throws UnknownHostException,
			IOException {
		Socket client = new Socket(serverIP, port);
		SocketRW sRW = new SocketRW(client.getInputStream(),
				client.getOutputStream());
		Scanner scan = new Scanner(System.in);
		while (true) {
			System.out.print("Enter  password: ");
			String pass = scan.nextLine();
			sRW.send(pass);

			String quoteFromServer = sRW.recieve();
			String timeStamp = new SimpleDateFormat("yyyy.MM.dd_HH:mm:ss")
					.format(Calendar.getInstance().getTime());
			timeStamp = "[" + timeStamp + "]";
			File fout = new File(
					"D:\\AndroidDevelopment\\JAVA DEVELOPMENT\\RandomQuotesGenerator\\src\\Files\\received_quotes.txt");
			FileWriter fileWriter = new FileWriter(fout, true);
			BufferedWriter bw = new BufferedWriter(fileWriter);
			try {
				// bw.newLine();
				bw.write(timeStamp + "-" + quoteFromServer);
				bw.newLine();
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		// System.out.println("\nGotovo");
		// client.close();
	}

	public static void main(String[] args) {
		try {
			connectToServer();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
