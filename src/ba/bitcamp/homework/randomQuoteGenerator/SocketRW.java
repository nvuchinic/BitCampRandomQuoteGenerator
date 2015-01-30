package ba.bitcamp.homework.randomQuoteGenerator;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
/**
 * class that enables sockets(server and clients) to read and write
 * @author nermin
 *
 */
public class SocketRW {
	InputStream is;
	OutputStream os;

	public SocketRW(InputStream is, OutputStream os) {
		this.is = is;
		this.os = os;
	}
/**
 * sends string message, but first it converts message to byte array and uses output stream to   send it.
 * @param msg String message
 * @throws IOException
 */
	public void send(String msg) throws IOException {
		byte[] msgToByte = msg.getBytes();
		os.write(msgToByte.length);
		os.write(msgToByte);
	}

	/**
	 * receives message. It uses InputStream object for receiving message in byte array format,
	 *  and then transforms it to string using StrinBuilder class.
	 * @return String
	 * @throws IOException
	 */
	public String recieve() throws IOException {
		StringBuilder sb = new StringBuilder();
		int byteRead = 0;
		int msgLength = is.read();
		byte[] buffer = new byte[msgLength];
		while ((byteRead += is.read(buffer)) >= 0) {
			sb.append(new String(buffer).replaceAll("\\s+", " "));
			if (byteRead >= msgLength)
				break;
		}
		return sb.toString();
	}
}