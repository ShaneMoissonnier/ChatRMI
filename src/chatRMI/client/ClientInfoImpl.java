package chatRMI.client;

import chatRMI.remoteInterfaces.ClientInfo;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientInfoImpl extends UnicastRemoteObject implements ClientInfo {
    private final String name;

    public ClientInfoImpl(String name) throws RemoteException {
        this.name = name;
    }

    @Override
    public void loginCallback(boolean status) {
        if (status) {
            System.out.println("Login successful");
        } else {
            System.out.println("Error");
            System.exit(1);
        }
    }

    @Override
    public void messageReceivedCallback(ClientInfo client, String message) {
        try {
            System.out.println(client.getName() + " : " + message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void logoutCallback(boolean status) {
        if (status) {
            System.out.println("Logout successful");
        } else {
            System.out.println("Error");
            System.exit(1);
        }
    }

    @Override
    public String getName() {
        return this.name;
    }
}
