package chatRMI.client.gui;

import chatRMI.client.Client;
import chatRMI.client.gui.widgets.ContentPanel;
import chatRMI.client.gui.widgets.MenuBar;
import chatRMI.client.gui.widgets.SideBar;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatAtomOneDarkContrastIJTheme;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.rmi.RemoteException;

public class ClientGUI extends JFrame {

    private Client m_client;

    public ClientGUI(String title, Client client) throws RemoteException {
        super(title);

        FlatAtomOneDarkContrastIJTheme.setup();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1024, 760));

        UIDefaults def = UIManager.getLookAndFeelDefaults();

        this.m_client = client;

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new SideBar(), new ContentPanel());
        splitPane.setDividerLocation(150);
        this.add(splitPane);

        this.setJMenuBar(new MenuBar());

        client.setGui(this);

        pack();
    }

    public void addChatMessage(String message) throws BadLocationException {
        ContentPanel.addMessage(message);
    }
}
