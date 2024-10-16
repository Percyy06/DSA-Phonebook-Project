import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

class Contact {
    String name;
    String phoneNumber;

    public Contact(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Phone Number: " + phoneNumber;
    }
}

public class Main extends JFrame {

    ArrayList<Contact> phonebook = new ArrayList<>();

    Color backgroundColor = new Color(0x032221); // Dark green background
    Color buttonColor = new Color(0x00DF81); // Bright green buttons
    Color textColor = new Color(0xF1F7F6); // Light text
    Color buttonBorderColor = new Color(0x000F81); // Dark blue button border
    Color secondaryButtonColor = new Color(0x03624C); // Darker green for buttons
    Color hoverButtonColor = new Color(0x2CC295); // Light green on hover
    Font mainFont = new Font("Roboto", Font.BOLD, 16);

    public Main() {
        // Frame setup
        setTitle("Phonebook Application");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main Menu Panel
        JPanel mainMenuPanel = new JPanel();
        mainMenuPanel.setLayout(new GridLayout(6, 1, 10, 10));
        mainMenuPanel.setBackground(backgroundColor);

        JButton insertButton = createStyledButton("Insert Contact");
        JButton searchButton = createStyledButton("Search Contact");
        JButton displayButton = createStyledButton("Display All Contacts");
        JButton deleteButton = createStyledButton("Delete Contact");
        JButton updateButton = createStyledButton("Update Contact");
        JButton sortButton = createStyledButton("Sort Contacts");

        mainMenuPanel.add(insertButton);
        mainMenuPanel.add(searchButton);
        mainMenuPanel.add(displayButton);
        mainMenuPanel.add(deleteButton);
        mainMenuPanel.add(updateButton);
        mainMenuPanel.add(sortButton);

        add(mainMenuPanel);

        // Button Click Sound
        ActionListener playSound = e -> playSound("sounds/videoplayback.wav");

        // Insert Contact Page
        insertButton.addActionListener(e -> {
            playSound.actionPerformed(e);
            JPanel insertPanel = new JPanel(new GridLayout(3, 2, 10, 10));
            insertPanel.setBackground(backgroundColor);
            JTextField nameField = new JTextField();
            JTextField phoneField = new JTextField();
            JButton submitButton = createStyledButton("Add Contact", secondaryButtonColor);

            insertPanel.add(createStyledLabel("Enter Name:"));
            insertPanel.add(nameField);
            insertPanel.add(createStyledLabel("Enter Phone:"));
            insertPanel.add(phoneField);
            insertPanel.add(submitButton);

            switchPanel(insertPanel);

            submitButton.addActionListener(event -> {
                playSound.actionPerformed(event);
                String name = nameField.getText();
                String phone = phoneField.getText();

                // Check for duplicate contacts
                if (isDuplicateContact(name, phone)) {
                    JOptionPane.showMessageDialog(this, "Contact already exists!");
                    return;
                }

                if (!name.isBlank() && !phone.isBlank()) {
                    phonebook.add(new Contact(name, phone));
                    JOptionPane.showMessageDialog(this, "Contact Added!");
                    switchPanel(mainMenuPanel);
                } else {
                    JOptionPane.showMessageDialog(this, "Please fill in both fields!");
                }
            });
        });

        // Search Contact Page
        searchButton.addActionListener(e -> {
            playSound.actionPerformed(e);
            JPanel searchPanel = new JPanel(new GridLayout(2, 1, 10, 10));
            searchPanel.setBackground(backgroundColor);
            JTextField searchField = new JTextField();
            JButton searchSubmit = createStyledButton("Search", secondaryButtonColor);

            searchPanel.add(createStyledLabel("Enter Name to Search:"));
            searchPanel.add(searchField);
            searchPanel.add(searchSubmit);

            switchPanel(searchPanel);

            searchSubmit.addActionListener(event -> {
                playSound.actionPerformed(event);
                String name = searchField.getText();
                for (Contact contact : phonebook) {
                    if (contact.name.equalsIgnoreCase(name)) {
                        JOptionPane.showMessageDialog(this, "Contact Found: " + contact);
                        switchPanel(mainMenuPanel);
                        return;
                    }
                }
                JOptionPane.showMessageDialog(this, "Contact Not Found");
                switchPanel(mainMenuPanel);
            });
        });

        // Display All Contacts Page
        displayButton.addActionListener(e -> {
            playSound.actionPerformed(e);
            JTextArea displayArea = new JTextArea();
            displayArea.setEditable(false);
            displayArea.setBackground(backgroundColor);
            displayArea.setForeground(textColor);
            displayArea.setFont(mainFont);
            for (Contact contact : phonebook) {
                displayArea.append(contact.toString() + "\n");
            }
            JScrollPane scrollPane = new JScrollPane(displayArea);
            JPanel displayPanel = new JPanel(new BorderLayout());
            displayPanel.setBackground(backgroundColor);
            displayPanel.add(scrollPane, BorderLayout.CENTER);
            JButton backButton = createStyledButton("Back to Main Menu", secondaryButtonColor);
            displayPanel.add(backButton, BorderLayout.SOUTH);
            switchPanel(displayPanel);

            backButton.addActionListener(event -> {
                playSound.actionPerformed(event);
                switchPanel(mainMenuPanel);
            });
        });

        // Delete Contact Page
        deleteButton.addActionListener(e -> {
            playSound.actionPerformed(e);
            JPanel deletePanel = new JPanel(new GridLayout(2, 1, 10, 10));
            deletePanel.setBackground(backgroundColor);
            JTextField deleteField = new JTextField();
            JButton deleteSubmit = createStyledButton("Delete", secondaryButtonColor);

            deletePanel.add(createStyledLabel("Enter Name to Delete:"));
            deletePanel.add(deleteField);
            deletePanel.add(deleteSubmit);

            switchPanel(deletePanel);

            deleteSubmit.addActionListener(event -> {
                playSound.actionPerformed(event);
                String name = deleteField.getText();
                boolean found = false;
                for (int i = 0; i < phonebook.size(); i++) {
                    if (phonebook.get(i).name.equalsIgnoreCase(name)) {
                        phonebook.remove(i);
                        JOptionPane.showMessageDialog(this, "Contact Deleted");
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    JOptionPane.showMessageDialog(this, "Contact Not Found");
                }
                switchPanel(mainMenuPanel);
            });
        });

        // Update Contact Page
        updateButton.addActionListener(e -> {
            playSound.actionPerformed(e);
            JPanel updatePanel = new JPanel(new GridLayout(3, 2, 10, 10));
            updatePanel.setBackground(backgroundColor);
            JTextField nameField = new JTextField();
            JTextField newPhoneField = new JTextField();
            JButton updateSubmit = createStyledButton("Update", secondaryButtonColor);

            updatePanel.add(createStyledLabel("Enter Name to Update:"));
            updatePanel.add(nameField);
            updatePanel.add(createStyledLabel("Enter New Phone Number:"));
            updatePanel.add(newPhoneField);
            updatePanel.add(updateSubmit);

            switchPanel(updatePanel);

            updateSubmit.addActionListener(event -> {
                playSound.actionPerformed(event);
                String name = nameField.getText();
                String newPhone = newPhoneField.getText();
                boolean contactFound = false;

                for (Contact contact : phonebook) {
                    if (contact.name.equalsIgnoreCase(name)) {
                        contact.phoneNumber = newPhone;
                        JOptionPane.showMessageDialog(this, "Contact Updated Successfully!");
                        contactFound = true;
                        break;
                    }
                }

                if (!contactFound) {
                    JOptionPane.showMessageDialog(this, "Contact Not Found.");
                }

                // Switch back to main menu after updating
                switchPanel(mainMenuPanel);
            });
        });

        // Sort Contacts Page
        sortButton.addActionListener(e -> {
            playSound.actionPerformed(e);
            phonebook.sort((c1, c2) -> c1.name.compareToIgnoreCase(c2.name));
            JOptionPane.showMessageDialog(this, "Contacts Sorted Alphabetically!");
        });
    }

    // Helper method to create a styled button
    private JButton createStyledButton(String text) {
        return createStyledButton(text, buttonColor);
    }

    // Overloaded method to create buttons with a custom color
    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(textColor);
        button.setFont(mainFont);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(buttonBorderColor));
        return button;
    }

    // Helper method to create a styled label
    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(textColor);
        label.setFont(mainFont);
        return label;
    }

    // Helper method to switch between panels
    private void switchPanel(JPanel panel) {
        getContentPane().removeAll();
        getContentPane().add(panel);
        revalidate();
        repaint();
    }

    // Method to play sound effects
    private void playSound(String soundFile) {
        try {
            File soundPath = new File(soundFile);
            if (soundPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(soundPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
            } else {
                System.out.println("Sound file not found: " + soundFile);
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
        }
    }

    // Helper method to check for duplicate contacts
    private boolean isDuplicateContact(String name, String phone) {
        for (Contact contact : phonebook) {
            if (contact.name.equalsIgnoreCase(name) || contact.phoneNumber.equals(phone)) {
                return true; // Duplicate found
            }
        }
        return false; // No duplicates
    }

    // Main method to run the application
    public static void main(String[] args) {
        // Start the application
        SwingUtilities.invokeLater(() -> {
            Main mainApp = new Main();
            mainApp.setVisible(true);
        });
    }
}
