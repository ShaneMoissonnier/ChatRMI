package chatRMI.client.gui;

import chatRMI.client.fc.ClientAbstract;
import chatRMI.client.gui.widgets.ConnectionButtons;
import chatRMI.client.gui.widgets.ContentPanel;
import chatRMI.client.gui.widgets.SideBar;
import chatRMI.common.Message;
import chatRMI.remoteInterfaces.ClientInfo;

import java.rmi.RemoteException;
import java.util.List;

@SuppressWarnings("serial")
public class ClientGUI extends ClientAbstract {
    private boolean loggedIn;

    public ClientGUI(String host, String name) throws RemoteException {
        super(host, name);
        loggedIn = false;
    }

    @Override
    protected void shutDown() throws RemoteException {
        if (loggedIn) {
            this.logout();
        }
    }

    @Override
    protected void loadHistory(List<Message> history) throws RemoteException {
        for (Message m : history) {
            ContentPanel.addMessage(m);
        }
    }

    public boolean isLoggedIn() throws RemoteException {
        return loggedIn;
    }

    @Override
    public void loginCallback(boolean status, List<Message> history) throws RemoteException {
        super.loginCallback(status, history);

        if (status) {
            this.loggedIn = true;
            SideBar.onSelfLogin(this.chatService.getLoggedInClients());
            ConnectionButtons.setLoggedIn();
        }
    }

    @Override
    public void otherLoginCallback(ClientInfo other) throws RemoteException {
        super.otherLoginCallback(other);
        SideBar.onOtherLogin(other);
    }

    @Override
    public void logoutCallback(boolean status) throws RemoteException {
        super.logoutCallback(status);

        if (status) {
            this.loggedIn = false;
            ContentPanel.onDisconnect();
            SideBar.onSelfLogout();
            ConnectionButtons.setLoggedOut();
        }
    }

    @Override
    public void otherLogoutCallback(ClientInfo other) throws RemoteException {
        super.otherLogoutCallback(other);
        SideBar.onOtherLogout(other);
    }

    @Override
    public void messageReceivedCallback(Message message) throws RemoteException {
        super.messageReceivedCallback(message);
        ContentPanel.addMessage(message);
    }
}
