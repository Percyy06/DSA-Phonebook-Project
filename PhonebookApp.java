import java.util.ArrayList;
import java.util.Scanner;

class Contact {
    String name;
    String phoneNumber;

    // Constructor to create a new contact
    public Contact(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Phone Number: " + phoneNumber;
    }
}

public class PhonebookApp {

    // List to hold the contacts
    static ArrayList<Contact> phonebook = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nPhonebook Application");
            System.out.println("1. Insert Contact");
            System.out.println("2. Search Contact");
            System.out.println("3. Display All Contacts");
            System.out.println("4. Delete Contact");
            System.out.println("5. Update Contact");
            System.out.println("6. Sort Contacts");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    insertContact(sc);
                    break;
                case 2:
                    searchContact(sc);
                    break;
                case 3:
                    displayAllContacts();
                    break;
                case 4:
                    deleteContact(sc);
                    break;
                case 5:
                    updateContact(sc);
                    break;
                case 6:
                    sortContacts();
                    break;
                case 0:
                    System.out.println("Exiting Phonebook.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);

        sc.close();
    }

    // 1. Insert Contact
    public static void insertContact(Scanner sc) {
        System.out.print("Enter contact name: ");
        String name = sc.nextLine();
        System.out.print("Enter phone number: ");
        String phoneNumber = sc.nextLine();
        Contact newContact = new Contact(name, phoneNumber);
        phonebook.add(newContact);
        System.out.println("Contact added successfully.");
    }

    // 2. Search Contact
    public static void searchContact(Scanner sc) {
        System.out.print("Enter the name to search: ");
        String name = sc.nextLine();
        for (Contact contact : phonebook) {
            if (contact.name.equalsIgnoreCase(name)) {
                System.out.println("Contact found: " + contact);
                return;
            }
        }
        System.out.println("Contact not found.");
    }

    // 3. Display All Contacts
    public static void displayAllContacts() {
        if (phonebook.isEmpty()) {
            System.out.println("Phonebook is empty.");
        } else {
            System.out.println("\nPhonebook Contacts:");
            for (Contact contact : phonebook) {
                System.out.println(contact);
            }
        }
    }

    // 4. Delete Contact
    public static void deleteContact(Scanner sc) {
        System.out.print("Enter the name to delete: ");
        String name = sc.nextLine();
        for (int i = 0; i < phonebook.size(); i++) {
            if (phonebook.get(i).name.equalsIgnoreCase(name)) {
                phonebook.remove(i);
                System.out.println("Contact deleted successfully.");
                return;
            }
        }
        System.out.println("Contact not found.");
    }

    // 5. Update Contact
    public static void updateContact(Scanner sc) {
        System.out.print("Enter the name to update: ");
        String name = sc.nextLine();
        for (Contact contact : phonebook) {
            if (contact.name.equalsIgnoreCase(name)) {
                System.out.print("Enter the new phone number: ");
                String newPhoneNumber = sc.nextLine();
                contact.phoneNumber = newPhoneNumber;
                System.out.println("Contact updated successfully.");
                return;
            }
        }
        System.out.println("Contact not found.");
    }

    // 6. Sort Contacts (Alphabetically by name)
    public static void sortContacts() {
        if (phonebook.isEmpty()) {
            System.out.println("Phonebook is empty.");
            return;
        }

        phonebook.sort((c1, c2) -> c1.name.compareToIgnoreCase(c2.name));
        System.out.println("Contacts sorted alphabetically by name.");
    }
}