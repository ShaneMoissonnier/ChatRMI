package chatRMI.client;

import chatRMI.remoteInterfaces.ClientInfo;

public class ClientInfoImpl implements ClientInfo {
    private final String name;

    public ClientInfoImpl(String name) {
        super();
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
        System.out.println(client.getName() + " : " + message);
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
