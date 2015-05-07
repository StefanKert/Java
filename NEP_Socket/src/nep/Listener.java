package nep;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Random;

public class Listener {
  private HashMap<InetAddress, Integer> establishedConnections;

  public Listener() {
    establishedConnections = new HashMap<InetAddress, Integer>();
  }

  public void listen(int port) throws IOException {
    DatagramSocket serverSocket = new DatagramSocket(port);
    while (true) {
      byte[] receiveData = new byte[1024];
      byte[] sendData = new byte[1024];
      DatagramPacket receivePacket = new DatagramPacket(receiveData,
          receiveData.length);
      serverSocket.receive(receivePacket);
      String sentence = new String(receivePacket.getData()).substring(0,
          receivePacket.getLength() - 1);
      System.out.println("Received: " + sentence);
      System.out.println("Sentence Length: " + sentence.length());
      InetAddress IPAddress = receivePacket.getAddress();
      int clientport = receivePacket.getPort();
      if (sentence.equals("HELLO")) {
        if (establishedConnections.containsKey(IPAddress)) {
          sendData = "Connection already established.".getBytes();
        } else {
          Integer randomInteger = new Integer(generateRandomNumber());
          establishedConnections.put(IPAddress, randomInteger);
          sendData = randomInteger.toString().getBytes();
        }
      } else if (Listener.isInteger(sentence)) {
        if (establishedConnections.containsKey(IPAddress)) {
          int number = Integer.parseInt(sentence);
          if (number > establishedConnections.get(IPAddress))
            sendData = "smaller".getBytes();
          else if (number < establishedConnections.get(IPAddress))
            sendData = "greater".getBytes();
          else
            sendData = "YOU DID IT!!!!".getBytes();
        } else {
          sendData = "Connection not established yet".getBytes();
        }
      } else {
        sendData = (sentence + " is an invalid input.").getBytes();
      }
      DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,
          IPAddress, clientport);
      serverSocket.send(sendPacket);
    }
  }

  private static boolean isInteger(String s) {
    try {
      Integer.parseInt(s);
    } catch (NumberFormatException e) {
      return false;
    } catch (NullPointerException e) {
      return false;
    }
    // only got here if we didn't return false
    return true;
  }

  private int generateRandomNumber() {
    Random rand = new Random();
    return rand.nextInt(100);
  }

  public static void main(String args[]) throws Exception {
    Listener listener = new Listener();
    listener.listen(1200);
  }
}
