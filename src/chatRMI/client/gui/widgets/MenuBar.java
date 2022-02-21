package chatRMI.client.gui.widgets;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class MenuBar extends JMenuBar {
    JMenu boutonFichier = new JMenu("Fichier");
    JMenuItem boutonQuitter = new JMenuItem("Quitter");

    public MenuBar() {
        add(boutonFichier);
        boutonFichier.setMnemonic(KeyEvent.VK_F);

        boutonFichier.addSeparator();

        boutonQuitter.setAccelerator(KeyStroke.getKeyStroke('Q', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        boutonFichier.add(boutonQuitter);

        boutonQuitter.addActionListener((ActionEvent ev) ->
        {
            if (confirmationQuitter())
            {
                System.exit(0);
            }
        });
    }

    public boolean confirmationQuitter() {
        if (JOptionPane.showConfirmDialog(this, "Voulez-vous vraiment quitter ?", "Quitter", JOptionPane.YES_NO_OPTION) == 0) {
            return true;
        }

        return false;
    }
}