package ocv;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MainAppGUI extends JFrame {

    private JComboBox<String> operationComboBox;
    private JButton selectFileButton;
    private JLabel selectedFileLabel;
    private JButton executeButton;

    public MainAppGUI() {
        setTitle("Image Manipulation App");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        operationComboBox = new JComboBox<>(new String[]{"Brightness Enhancement", "Sharpness Enhancement", "Face Detection"});
        topPanel.add(operationComboBox);

        selectFileButton = new JButton("Select File");
        selectFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(MainAppGUI.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    selectedFileLabel.setText(fileChooser.getSelectedFile().getAbsolutePath());
                }
            }
        });
        topPanel.add(selectFileButton);

        selectedFileLabel = new JLabel("No file selected");
        topPanel.add(selectedFileLabel);

        add(topPanel, BorderLayout.NORTH);

        executeButton = new JButton("Execute");
        executeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = operationComboBox.getSelectedIndex();
                String[] args = {selectedFileLabel.getText()};
                switch (choice) {
                    case 0:
                        Brightness.main(args);
                        break;
                    case 1:
                        Sharpness.main(args);
                        break;
                    case 2:
                        if (!selectedFileLabel.getText().equals("No file selected")) {
                            FaceFile.main(args);
                        } else {
                            JOptionPane.showMessageDialog(MainAppGUI.this, "Please select an image file.");
                        }
                        break;
                    default:
                        JOptionPane.showMessageDialog(MainAppGUI.this, "Invalid choice.");
                }
            }
        });
        add(executeButton, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainAppGUI gui = new MainAppGUI();
                gui.setVisible(true);
            }
        });
    }
}

