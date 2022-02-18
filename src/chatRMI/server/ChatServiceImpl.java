package chatRMI.server;

import chatRMI.remoteInterfaces.ChatService;
import chatRMI.remoteInterfaces.ClientInfo;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Vector;

public class ChatServiceImpl extends UnicastRemoteObject implements ChatService {
    private final List<ClientInfo> clientInfos;

    public ChatServiceImpl() throws RemoteException {
        super();
        this.clientInfos = new Vector<>();
    }

    @Override
    public synchronized void login(ClientInfo client) {
        try {
            if (clientInfos.stream().anyMatch(c -> {
                try {
                    return c.getName().equals(client.getName());
                } catch(RemoteException e)
                {
                    e.printStackTrace();
                }
                return false;
            })) {
                client.loginCallback(false);
            } else {
                this.clientInfos.add(client);
                System.out.println("Client " + client.getName() + " logged in");
                client.loginCallback(true);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void logout(ClientInfo client) {
        try {
            if (clientInfos.stream().anyMatch(c -> {
                try {
                    return c.getName().equals(client.getName());
                } catch (RemoteException e)
                {
                    e.printStackTrace();
                }
                return false;
            })) {
                this.clientInfos.remove(client);
                client.logoutCallback(true);
            } else {
                client.logoutCallback(false);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void sendMessage(ClientInfo client, String message) {
        try {
            for (ClientInfo c : this.clientInfos) {
                c.messageReceivedCallback(client, message);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
