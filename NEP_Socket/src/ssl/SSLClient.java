package ssl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class SSLClient {
	SSLSocket sslSocket;
	final String[] enabledCipherSuites = { "SSL_DH_anon_WITH_RC4_128_MD5" };

	public SSLClient(String host, int port) {
		SSLSocketFactory ssf = (SSLSocketFactory) SSLSocketFactory.getDefault();
		try {
			sslSocket = (SSLSocket) ssf.createSocket(host, port);
			((SSLSocket) sslSocket).setEnabledCipherSuites(enabledCipherSuites);
		} catch (IOException e) {
			System.err.println("Could not create socket: " + e.getMessage());
		}
	}

	public void initialize() {
		try {
			OutputStreamWriter out = new OutputStreamWriter(
					sslSocket.getOutputStream());
			BufferedReader in = new BufferedReader(new InputStreamReader(
					sslSocket.getInputStream()));

			// Send HELLO
			writeLine(out, "HELLO");
			// Wait for OK
			while (!in.readLine().equals("OK")) {
				writeLine(out, "Continue with \"OK\".");
			}
			System.out.println("Connection Established.");

			System.out.println("Enter number between 1 - 100:");
			String response;
			do {
				System.out.print("> ");
				BufferedReader consoleIn = new BufferedReader(
						new InputStreamReader(System.in));
				writeLine(out, consoleIn.readLine());

				response = in.readLine();
				System.out.println(response);
				
			} while (!response.equals("GAMEOVER"));
		} catch (IOException e) {
			System.err.println("Could not read from SSL socket: "
					+ e.getMessage());
		}
	}

	private void writeLine(OutputStreamWriter out, String message) {
		try {
			out.write(message + "\n");
			out.flush();
		} catch (IOException e) {
			System.err.println("Could not write to socket: " + e.getMessage());
		}
	}

	public static void main(String[] args) {
		SSLClient server = new SSLClient("127.0.0.1", 5555);
		server.initialize();
	}
}
