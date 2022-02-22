package chatRMI.client.fc;

import chatRMI.common.Message;

import java.rmi.RemoteException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ClientConsole extends ClientAbstract implements Runnable {
    public ClientConsole(String host, String name) throws RemoteException {
        super(host, name);

        this.login();
        this.run();
    }

    @Override
    protected void shutDown() throws RemoteException {
        this.logout();
    }

    @Override
    protected void loadHistory(List<Message> history) throws RemoteException {
        for (Message m : history) {
            this.messageReceivedCallback(m);
        }
    }

    @Override
    public void run() {
        String message;
        Scanner sc = new Scanner(System.in);

        while (true) {
            try {
                message = sc.nextLine();
            } catch (NoSuchElementException ignored) {
                sc.close();
                break;
            }

            if (!message.isBlank() || !message.isEmpty()) {
                try {
                    this.sendMessage(new Message(message, this.getName()));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
