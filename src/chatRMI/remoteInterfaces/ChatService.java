package chatRMI.remoteInterfaces;

import chatRMI.server.Message;

import javax.swing.text.BadLocationException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * The remote interface the server offers to the clients.
 * It allows the clients to log in, log out and send messages
 */
public interface ChatService extends Remote {
    /**
     * Called when a client tries to log into the server.
     *
     * @param client The client trying to log in
     */
    void login(ClientInfo client) throws RemoteException;

    /**
     * Called when a client tries to log out of the server
     *
     * @param client The client trying to log out
     */
    void logout(ClientInfo client) throws RemoteException;

    /**
     * Called when a client tries to send a message
     *
     * @param client  The client sending the message
     * @param message The message
     */
    void sendMessage(ClientInfo client, String message) throws RemoteException, BadLocationException;

    /**
     * Called when a client attempts to retrieve messages from users.
     */
    List<Message> getMessageList() throws RemoteException;

    void setMessageList(List<Message> messages) throws RemoteException;
}
