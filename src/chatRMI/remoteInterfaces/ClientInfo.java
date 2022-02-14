package chatRMI.remoteInterfaces;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInfo extends Remote, Serializable {
    void loginCallback(boolean status) throws RemoteException; // Called once, when we log into the server
    void messageReceivedCallback(ClientInfo client, String message) throws RemoteException; // Called everytime we receive a message
    void logoutCallback(boolean status) throws RemoteException; // Called once, whe we log out

    String getName();
}
