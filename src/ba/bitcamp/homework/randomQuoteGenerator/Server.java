package ba.bitcamp.homework.randomQuoteGenerator;

import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;
import java.io.*;

/**
 * class that represents server side of network communication. It has two
 * constant variables, port and password.
 * 
 * @author nermin
 * 
 */
public class Server {
	public static final int port = 1717;
	private static final String password = "12345";

	/**
	 * starts the server. First server receives string (represents password)
	 * from client machine, if it's equal to value of password variable of the
	 * server, then the server takes random quote from file quotes.txt and sends
	 * it to the client. If not, invalid password is recorded to file
	 * auth_log.txt(with time and IP address of the client)
	 */
	public static void startServer() {
		try {
			BufferedReader br = null;
			ServerSocket server = new ServerSocket(port);
			while (true) {
				System.out.println("waiting");
				Socket client = server.accept();
				InetAddress ia = client.getInetAddress();
				String clientAddress = ia.getHostAddress();
				SocketRW sRW = new SocketRW(client.getInputStream(),
						client.getOutputStream());
				while (true) {
					String messageForClient = null;
					String passFromClient = sRW.recieve();
					String timeStamp = new SimpleDateFormat(
							"yyyy.MM.dd_HH:mm:ss").format(Calendar
							.getInstance().getTime());
					if (passFromClient.equals(password)) {
						int rand = (int) (Math.random() * 15 + 1);
						File file = new File(
								"C:\\Users\\user\\Desktop\\quotes.txt");
						int counter = 0;

						try {
							java.io.FileReader fr = new java.io.FileReader(file);
							br = new BufferedReader(fr);
							String line;
							while ((line = br.readLine()) != null) {
								counter++;
								if (counter == rand)
									messageForClient = line;
							}

						} catch (FileNotFoundException e) {
							System.out.println("File not found: "
									+ file.toString());
						} catch (IOException e) {
							System.out.println("Unable to read file: "
									+ file.toString());
						} finally {
							try {
								br.close();
							} catch (IOException e) {
								System.out.println("Unable to close file: "
										+ file.toString());
							} catch (NullPointerException ex) {
							}
						}
						sRW.send(messageForClient);

					} else {
					System.out.println("Invalid password!");
						timeStamp = "[" + timeStamp + "]";
						File fout = new File(
								"D:\\AndroidDevelopment\\JAVA DEVELOPMENT\\RandomQuotesGenerator\\src\\Files\\auth_log.txt");
						FileWriter fileWriter = new FileWriter(fout, true);
						BufferedWriter bw = new BufferedWriter(fileWriter);
						try {
							// bw.newLine();
							bw.write(timeStamp + "-" + passFromClient + "-"
									+ clientAddress);
							bw.newLine();
							bw.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						// bw.close();
					}

				}
				// client.close();
				// System.out.println("Gotovo");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		startServer();
	}
}
