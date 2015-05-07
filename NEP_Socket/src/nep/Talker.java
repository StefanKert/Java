package nep;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Talker implements Runnable {
  private DatagramSocket clientSocket;

  public Talker(int receivePort) throws SocketException {
    clientSocket = new DatagramSocket(receivePort);
  }

  public void sendMessage(String message, String host, int port)
      throws IOException {
    byte[] sendData = new byte[1024];
    sendData = message.getBytes();
    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,
        InetAddress.getByName(host), port);
    clientSocket.send(sendPacket);
  }

  public void receiveDataAsync() throws IOException {
    byte[] receiveData = new byte[1024];
    DatagramPacket receivePacket = new DatagramPacket(receiveData,
        receiveData.length);
    clientSocket.receive(receivePacket);
    String modifiedSentence = new String(receivePacket.getData());
    System.out.println("Response:" + modifiedSentence);
  }

  public static void main(String[] args) throws IOException {
    Talker talker = new Talker(1201);
    new Thread(talker).start();
    BufferedReader inFromUser = new BufferedReader(new InputStreamReader(
        System.in));
    while (true) {
      String sentence = inFromUser.readLine();
      talker.sendMessage(sentence + "\0", "localhost", 1200);
    }
  }

  @Override
  public void run() {
    while (true) {
      try {
        receiveDataAsync();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }
}
