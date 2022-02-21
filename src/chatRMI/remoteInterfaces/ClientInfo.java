package chatRMI.remoteInterfaces;

import chatRMI.server.Message;

import javax.swing.text.BadLocationException;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The remote interface the client offers to the server.
 * <p>
 * It allows the client to get feedback on whether a login or a logout is successful or not, and to be notified when
 * something happens (someone logs in, logs out or sent a message)
 */
public interface ClientInfo extends Remote, Serializable {
    /**
     * Called once, when the client logs into the server
     *
     * @param status Whether the login is successful or not
     */
    void loginCallback(boolean status) throws RemoteException;

    /**
     * Called when someone else joins the server
     *
     * @param other The client that just logged in
     */
    void otherLoginCallback(ClientInfo other) throws RemoteException;

    /**
     * Called once, when the client logs out of the server
     *
     * @param status Whether the logout is successful or not
     */
    void logoutCallback(boolean status) throws RemoteException;

    /**
     * Called when someone else leaves the server
     *
     * @param other The client that just logged out
     */
    void otherLogoutCallback(ClientInfo other) throws RemoteException;

    /**
     * Called when someone (self included) sends a message to the server.
     *
     * @param message The message
     */
    void messageReceivedCallback(Message message) throws RemoteException, BadLocationException;

    String getName() throws RemoteException;
}
