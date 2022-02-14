package chatRMI.server;

import chatRMI.remoteInterfaces.ChatService;
import chatRMI.remoteInterfaces.ClientInfo;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Vector;

public class ChatServiceImpl implements ChatService {
    private final List<ClientInfo> clientInfos;

    public ChatServiceImpl() {
        this.clientInfos = new Vector<>();
    }

    @Override
    public void login(ClientInfo client) {
        try {
            if (clientInfos.stream().anyMatch(c -> c.getName().equals(client.getName()))) {
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
    public void logout(ClientInfo client) {
        try {
            if (clientInfos.stream().anyMatch(c -> c.getName().equals(client.getName()))) {
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
    public void sendMessage(ClientInfo client, String message) {
        try {
            for (ClientInfo c : this.clientInfos) {
                c.messageReceivedCallback(client, message);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
