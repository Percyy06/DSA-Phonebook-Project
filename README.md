import javax.sound.sampled.*; // Importing sound-related classes
import javax.swing.*; // Importing Swing for GUI components
import java.awt.*; // Importing AWT for layout managers and components
import java.awt.event.ActionListener; // Importing ActionListener for button actions
import java.io.File; // Importing File for handling file operations
import java.io.IOException; // Importing IOException for handling input/output exceptions
import java.util.ArrayList; // Importing ArrayList for dynamic arrays

// Class representing a contact with a name and phone number
class Contact {
    String name; // Name of the contact
    String phoneNumber; // Phone number of the contact

    // Constructor to initialize a new contact
    public Contact(String name, String phoneNumber) {
        this.name = name; // Set the name
        this.phoneNumber = phoneNumber; // Set the phone number
    }

    // Override toString method for displaying contact information
    @Override
    public String toString() {
        return "Name: " + name + ", Phone Number: " + phoneNumber; // Format for displaying contact
    }
}

// Main class for the Phonebook application, extending JFrame for GUI
public class Main extends JFrame {

    ArrayList<Contact> phonebook = new ArrayList<>(); // List to store contacts
    Color backgroundColor = new Color(60, 63, 65); // Dark background color for the application
    Color buttonColor = new Color(80, 80, 150); // Color for buttons
    Color textColor = new Color(210, 210, 210); // Color for text
    Font mainFont = new Font("Arial", Font.BOLD, 16); // Font settings for the application

    // Constructor to set up the main application window
    public Main() {
        // Frame setup
        setTitle("Phonebook Application"); // Set the title of the window
        setSize(400, 400); // Set the size of the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close application on exit
        setLocationRelativeTo(null); // Center the window on the screen

        // Main Menu Panel setup
        JPanel mainMenuPanel = new JPanel(); // Create a panel for the main menu
        mainMenuPanel.setLayout(new GridLayout(6, 1, 10, 10)); // Set layout for buttons
        mainMenuPanel.setBackground(backgroundColor); // Set background color

        // Create buttons for the main menu
        JButton insertButton = createStyledButton("Insert Contact");
        JButton searchButton = createStyledButton("Search Contact");
        JButton displayButton = createStyledButton("Display All Contacts");
        JButton deleteButton = createStyledButton("Delete Contact");
        JButton updateButton = createStyledButton("Update Contact");
        JButton sortButton = createStyledButton("Sort Contacts");

        // Add buttons to the main menu panel
        mainMenuPanel.add(insertButton);
        mainMenuPanel.add(searchButton);
        mainMenuPanel.add(displayButton);
        mainMenuPanel.add(deleteButton);
        mainMenuPanel.add(updateButton);
        mainMenuPanel.add(sortButton);

        add(mainMenuPanel); // Add the main menu panel to the frame

        // Button Click Sound setup
        ActionListener playSound = e -> playSound("resources/selectButtonSound.mp4"); // Sound to play on button click

        // Insert Contact Page
        insertButton.addActionListener(e -> {
            playSound.actionPerformed(e); // Play sound on button click
            JPanel insertPanel = new JPanel(new GridLayout(3, 2, 10, 10)); // Create insert contact panel
            insertPanel.setBackground(backgroundColor); // Set background color
            JTextField nameField = new JTextField(); // Text field for name input
            JTextField phoneField = new JTextField(); // Text field for phone number input
            JButton submitButton = createStyledButton("Add Contact"); // Button to submit new contact

            // Add components to insert panel
            insertPanel.add(createStyledLabel("Enter Name:")); 
            insertPanel.add(nameField); 
            insertPanel.add(createStyledLabel("Enter Phone:")); 
            insertPanel.add(phoneField); 
            insertPanel.add(submitButton); 

            switchPanel(insertPanel); // Switch to insert panel

            // Action for submitting a new contact
            submitButton.addActionListener(event -> {
                playSound.actionPerformed(event); // Play sound on submit
                String name = nameField.getText(); // Get name from text field
                String phone = phoneField.getText(); // Get phone number from text field
                if (!name.isEmpty() && !phone.isEmpty()) { // Check if fields are not empty
                    phonebook.add(new Contact(name, phone)); // Add new contact to the phonebook
                    JOptionPane.showMessageDialog(this, "Contact Added!"); // Show confirmation message
                    switchPanel(mainMenuPanel); // Switch back to main menu
                }
            });
        });

        // Search Contact Page
        searchButton.addActionListener(e -> {
            playSound.actionPerformed(e); // Play sound on button click
            JPanel searchPanel = new JPanel(new GridLayout(2, 1, 10, 10)); // Create search panel
            searchPanel.setBackground(backgroundColor); // Set background color
            JTextField searchField = new JTextField(); // Text field for search input
            JButton searchSubmit = createStyledButton("Search"); // Button to submit search

            // Add components to search panel
            searchPanel.add(createStyledLabel("Enter Name to Search:")); 
            searchPanel.add(searchField); 
            searchPanel.add(searchSubmit); 

            switchPanel(searchPanel); // Switch to search panel

            // Action for submitting a search
            searchSubmit.addActionListener(event -> {
                playSound.actionPerformed(event); // Play sound on search submit
                String name = searchField.getText(); // Get name from search field
                for (Contact contact : phonebook) { // Loop through contacts
                    if (contact.name.equalsIgnoreCase(name)) { // Check for match
                        JOptionPane.showMessageDialog(this, "Contact Found: " + contact); // Show found contact
                        switchPanel(mainMenuPanel); // Return to main menu after search
                        return; // Exit the loop
                    }
                }
                // Show message if contact not found
                JOptionPane.showMessageDialog(this, "Contact Not Found"); 
                switchPanel(mainMenuPanel); // Return to main menu if not found
            });
        });

        // Display All Contacts Page
        displayButton.addActionListener(e -> {
            playSound.actionPerformed(e); // Play sound on button click
            JTextArea displayArea = new JTextArea(); // Text area to display contacts
            displayArea.setEditable(false); // Make text area non-editable
            displayArea.setBackground(new Color(45, 48, 50)); // Set background color
            displayArea.setForeground(textColor); // Set text color
            displayArea.setFont(mainFont); // Set font for text area

            // Add all contacts to the display area
            for (Contact contact : phonebook) {
                displayArea.append(contact.toString() + "\n"); // Append contact info
            }
            JScrollPane scrollPane = new JScrollPane(displayArea); // Make display area scrollable
            JPanel displayPanel = new JPanel(new BorderLayout()); // Create display panel
            displayPanel.setBackground(backgroundColor); // Set background color
            displayPanel.add(scrollPane, BorderLayout.CENTER); // Add scroll pane to panel
            JButton backButton = createStyledButton("Back to Main Menu"); // Button to go back
            displayPanel.add(backButton, BorderLayout.SOUTH); // Add back button to panel
            switchPanel(displayPanel); // Switch to display panel

            // Action for going back to main menu
            backButton.addActionListener(event -> {
                playSound.actionPerformed(event); // Play sound on back button click
                switchPanel(mainMenuPanel); // Switch back to main menu
            });
        });

        // Delete Contact Page
        deleteButton.addActionListener(e -> {
            playSound.actionPerformed(e); // Play sound on button click
            JPanel deletePanel = new JPanel(new GridLayout(2, 1, 10, 10)); // Create delete panel
            deletePanel.setBackground(backgroundColor); // Set background color
            JTextField deleteField = new JTextField(); // Text field for name to delete
            JButton deleteSubmit = createStyledButton("Delete"); // Button to submit delete

            // Add components to delete panel
            deletePanel.add(createStyledLabel("Enter Name to Delete:")); 
            deletePanel.add(deleteField); 
            deletePanel.add(deleteSubmit); 

            switchPanel(deletePanel); // Switch to delete panel

            // Action for submitting a delete request
            deleteSubmit.addActionListener(event -> {
                playSound.actionPerformed(event); // Play sound on delete submit
                String name = deleteField.getText(); // Get name from text field
                // Loop through contacts to find the one to delete
                for (int i = 0; i < phonebook.size(); i++) {
                    if (phonebook.get(i).name.equalsIgnoreCase(name)) { // Check for match
                        phonebook.remove(i); // Remove contact from the phonebook
                        JOptionPane.showMessageDialog(this, "Contact Deleted"); // Show confirmation message
                        switchPanel(mainMenuPanel); // Return to main menu
                        return; // Exit the loop
                    }
                }
                // Show message if contact not found
                JOptionPane.showMessageDialog(this, "Contact Not Found"); 
                switch
