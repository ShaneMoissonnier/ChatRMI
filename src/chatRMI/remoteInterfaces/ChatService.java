package chatRMI.remoteInterfaces;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatService extends Remote {
    void login(ClientInfo client) throws RemoteException;
    void logout(ClientInfo client) throws RemoteException;
    void sendMessage(ClientInfo client, String message) throws RemoteException;
}
