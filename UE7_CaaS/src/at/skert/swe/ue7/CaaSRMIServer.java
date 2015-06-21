package at.skert.swe.ue7;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

import at.skert.swe.ue7.rmi.RmiService;

public class CaaSRMIServer {
  public static void main(String[] args) throws RemoteException, MalformedURLException {
    RmiService.launch();
  }
}
