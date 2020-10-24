package burp;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BurpTab {
    public String fromHost = "";
    public String toHost = "";

    private JPanel RootPanel;
    private JButton saveButton;
    private JTextField fromField;
    private JTextField toField;

    private IBurpExtenderCallbacks callbacks;

    public void saveSettings() {
        callbacks.saveExtensionSetting("fromHost",fromHost);
        callbacks.saveExtensionSetting("toHost",toHost);
    }

    public void loadSettings() {
        fromHost = callbacks.loadExtensionSetting("fromHost");
        toHost = callbacks.loadExtensionSetting("toHost");
        if(fromHost != null && toHost != null) {
            fromField.setText(fromHost);
            toField.setText(toHost);
        }
    }

    public BurpTab(IBurpExtenderCallbacks callbacks) {
        this.callbacks = callbacks;
        setupUI();
        loadSettings();

        // token save button
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fromHost = fromField.getText();
                toHost = toField.getText();
                saveSettings();
            }
        });
    }

    private void setupUI() {
        RootPanel = new JPanel(new MigLayout());

        RootPanel.add(new JLabel("<html><b>Host from:</b><html>"));
        fromField = new JTextField("from");
        fromField.setMinimumSize(new Dimension(150,-1));
        RootPanel.add(fromField);

        RootPanel.add(new JLabel("<html><b>Host to:</b><html>"));
        toField = new JTextField("to");
        toField.setMinimumSize(new Dimension(150,-1));
        RootPanel.add(toField,"wrap");
        saveButton = new JButton("Save");
        RootPanel.add(saveButton,"wrap");
        JLabel label = new JLabel("<html><p>Traffic Redirector does not change the Host/IP that burp suite uses to initially check rule scope, therefore if you want to included requests under your rule scope that were modified using Traffic Redirector, use the previous Host/IP.</p><html>");
        label.setPreferredSize(new Dimension(400,300));
        RootPanel.add(label,"span 4");


        RootPanel.setVisible(true);
    }

    public JComponent getRootComponent() {
        return RootPanel;
    }

}
