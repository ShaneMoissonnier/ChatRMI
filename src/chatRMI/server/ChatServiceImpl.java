package chatRMI.server;

import chatRMI.remoteInterfaces.ChatService;
import chatRMI.remoteInterfaces.ClientInfo;

import javax.swing.text.BadLocationException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;

public class ChatServiceImpl extends UnicastRemoteObject implements ChatService {
    private static final Logger logger = Logger.getLogger("chatServer");

    private final List<ClientInfo> clientInfos;
    private List<Message> messageList;

    public ChatServiceImpl() throws RemoteException {
        super();
        this.clientInfos = new Vector<>();
        this.messageList = new Vector<>();
    }

    /**
     * Called when a client tries to log into the server.
     *
     * @param client The client trying to log in
     */
    @Override
    public synchronized void login(ClientInfo client) throws RemoteException {
        if (clientInfos.contains(client)) {
            /* A client can only be logged in once */
            client.loginCallback(false);
        } else {
            /* We notify the other clients that someone joined */
            for (ClientInfo c : this.clientInfos) {
                c.otherLoginCallback(client);
            }

            /* We log the client in */
            this.clientInfos.add(client);
            logger.info("Client " + client.getName() + " logged in");
            client.loginCallback(true);
        }
    }

    /**
     * Called when a client tries to log out of the server
     *
     * @param client The client trying to log out
     */
    @Override
    public synchronized void logout(ClientInfo client) throws RemoteException {
        if (!clientInfos.contains(client)) {
            /* A client can only log out if they are logged in */
            client.logoutCallback(false);
        } else {
            /* We log the client out */
            this.clientInfos.remove(client);

            /* We notify the other clients that someone left */
            for (ClientInfo c : this.clientInfos) {
                c.otherLogoutCallback(client);
            }

            logger.info("Client " + client.getName() + " logged out");
            client.logoutCallback(true);
        }
    }

    /**
     * Called when a client tries to send a message
     *
     * @param client  The client sending the message
     * @param message The message
     */
    @Override
    public synchronized void sendMessage(ClientInfo client, String message) throws RemoteException, BadLocationException {

        String clientName = client.getName();

        /* We log the message in the server's console */
        System.out.println(clientName + " : " + message);

        /* We add the message to the list of messages */
        Message newMessage = new Message(message, clientName);
        messageList.add(newMessage);

        /* We send the message to every client */
        for (ClientInfo c : this.clientInfos) {
            c.messageReceivedCallback(newMessage);
        }
    }

    /**
     * Called when a client attempts to retrieve messages from users.
     *
     * @return list of messages
     */
    @Override
    public List<Message> getMessageList() throws RemoteException {
        return this.messageList;
    }

    /**
     * Used to update message list
     */
    @Override
    public void setMessageList(List<Message> messages) throws RemoteException
    {
        this.messageList = messages;
    }
}
