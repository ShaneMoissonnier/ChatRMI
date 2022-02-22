package chatRMI.server;

import chatRMI.common.Message;
import chatRMI.remoteInterfaces.ChatService;
import chatRMI.remoteInterfaces.ClientInfo;

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
            client.loginCallback(false, null);
        } else {
            /* We notify the other clients that someone joined */
            for (ClientInfo c : this.clientInfos) {
                c.otherLoginCallback(client);
            }

            /* We log the client in */
            this.clientInfos.add(client);
            logger.info("Client " + client.getName() + " logged in");
            client.loginCallback(true, messageList);
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
     * @param message The message
     */
    @Override
    public synchronized void sendMessage(Message message) throws RemoteException {
        /* We log the message in the server's console */
        System.out.println(message);

        /* We add the message to the list of messages */
        messageList.add(message);

        /* We send the message to every client */
        for (ClientInfo c : this.clientInfos) {
            c.messageReceivedCallback(message);
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
    public void setMessageList(List<Message> messages) throws RemoteException {
        this.messageList = messages;
    }

    @Override
    public List<ClientInfo> getLoggedInClients() throws RemoteException {
        return clientInfos;
    }
}
