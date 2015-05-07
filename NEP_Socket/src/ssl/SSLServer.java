package ssl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Random;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

public class SSLServer {
	SSLServerSocket sslSocket;

	final String[] enabledCipherSuites = { "SSL_DH_anon_WITH_RC4_128_MD5" };

	public SSLServer(int port) {
		SSLServerSocketFactory ssf = (SSLServerSocketFactory) SSLServerSocketFactory
				.getDefault();
		try {
			sslSocket = (SSLServerSocket) ssf.createServerSocket(port);
			((SSLServerSocket) sslSocket)
					.setEnabledCipherSuites(enabledCipherSuites);
		} catch (IOException e) {
			System.err.println("Could not create socket: " + e.getMessage());
		}
	}

	public void listen() {
		System.out.println("Server: Started listening...");
		while (true) {
			try {
				SSLSocket s = (SSLSocket) sslSocket.accept();
				System.out.println("Server: New Client connected.");
				OutputStreamWriter out = new OutputStreamWriter(
						s.getOutputStream());
				BufferedReader in = new BufferedReader(new InputStreamReader(
						s.getInputStream()));

				handleClient(in, out);

				out.close();
				in.close();
				s.close();
				System.out.println("Server: Client finished.");
			} catch (IOException e) {
				System.err.println("Could not read from SSL socket: "
						+ e.getMessage());
			}
		}
	}

	private void handleClient(BufferedReader in, OutputStreamWriter out)
			throws IOException {
		// Wait for HELLO
		while (!in.readLine().equals("HELLO")) {
			writeLine(out, "Start communication with \"HELLO\".");
		}

		// Send OK
		writeLine(out, "OK");
		System.out.println("Server: Connection established.");
		
		// Generate random number
		int number = randInt(1, 100);
		
		// Wait for Zahl
		int guess = -1;
		do {
			String s = in.readLine();
			if(!tryParseInt(s)) {
				writeLine(out, "NOTANUMBER");
			} else {
				guess = Integer.parseInt(s);
				if(number < guess)
					writeLine(out, "SMALLER");
				else if(number > guess)
					writeLine(out, "GREATER");
				else
					writeLine(out, "END");
			}
			
		} while(guess != number);				
	}

	private void writeLine(OutputStreamWriter out, String message) {
		try {
			out.write(message + "\n");
			out.flush();
		} catch (IOException e) {
			System.err.println("Could not write to socket: " + e.getMessage());
		}
	}

	private int randInt(int min, int max) {
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}

	private boolean tryParseInt(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static void main(String[] args) {
		SSLServer server = new SSLServer(5555);
		server.listen();
	}
}
