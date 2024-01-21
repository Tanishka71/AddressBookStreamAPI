package com.bridgelabz.addressBook;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class AddressBookSystem {
	static HashMap<String, AddressBook> addressBooks = new HashMap<>();
	static HashMap<String, List<Contact>> cityDictionary = new HashMap<>();
	static HashMap<String, List<Contact>> stateDictionary = new HashMap<>();
	static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {

		while (true) {
			System.out.println("1. Add Address Book");
			System.out.println("2. Switch Address Book");
			System.out.println("3. Add Contact");
			System.out.println("4. Edit Contact");
			System.out.println("5. Delete Contact");
			System.out.println("6. Display Contacts");
			System.out.println("7. Search Persons in City or State");
			System.out.println("8. View Persons by City");
			System.out.println("9. View Persons by State");
			System.out.println("10. Get Contact Count by City");
			System.out.println("11. Get Contact Count by State");
			System.out.println("12. Exit");
			System.out.print("Choose an option: ");
			int choice = scanner.nextInt();
			scanner.nextLine();

			switch (choice) {
			case 1:
				addAddressBook(addressBooks, scanner);
				break;
			case 2:
				switchAddressBook(addressBooks, scanner);
				break;
			case 3:
				addContact(addressBooks, scanner);
				break;
			case 4:
				editContact(addressBooks, scanner);
				break;
			case 5:
				deleteContact(addressBooks, scanner);
				break;
			case 6:
				displayContacts(addressBooks);
				break;
			case 7:
				searchPersonsInCityState(addressBooks, scanner);
				break;

			case 8:
				viewPersonsByCity();
				break;

			case 9:
				viewPersonsByState();
				break;

			case 10:
				getContactCountByCity();
				break;
			case 11:
				getContactCountByState();
				break;

			case 12:
				System.out.println("Exiting program. Goodbye!");
				System.exit(0);

			default:
				System.out.println("Invalid choice. Please choose a valid option.\n");
			}
		}
	}

	/**
	 * @desc Prompts the user for the name of the new Address Book, checks for
	 *       existence, and adds it to the HashMap if it doesn't already exist.
	 * @params addressBooks scanner
	 * @return null
	 */
	private static void addAddressBook(HashMap<String, AddressBook> addressBooks, Scanner scanner) {
		System.out.print("Enter the name of the new Address Book: ");
		String addressBookName = scanner.nextLine();

		if (addressBooks.containsKey(addressBookName)) {
			System.out.println("An Address Book with the given name already exists.\n");
		} else {
			AddressBook newAddressBook = new AddressBook(addressBookName);
			addressBooks.put(addressBookName, newAddressBook);
			System.out.println("Address Book added successfully!\n");
		}
	}

	/**
	 * @desc Prompts the user for the name of the Address Book to switch to and
	 *       provides feedback based on the success of the switch.
	 * @params addressBooks scanner
	 * @return null
	 */
	private static void switchAddressBook(HashMap<String, AddressBook> addressBooks, Scanner scanner) {
		System.out.print("Enter the name of the Address Book to switch to: ");
		String addressBookName = scanner.nextLine();

		if (addressBooks.containsKey(addressBookName)) {
			System.out.println("Switched to Address Book: " + addressBookName + "\n");
		} else {
			System.out.println("Address Book not found with the given name.\n");
		}
	}

	/**
	 * @desc Prompts the user for the Address Book name, creates a new contact, adds
	 *       it to the Address Book, and updates dictionaries for city and state.
	 * @params scanner addressBooks
	 * @return null
	 */
	private static void addContact(HashMap<String, AddressBook> addressBooks, Scanner scanner) {
		System.out.print("Enter the name of the Address Book to add the contact to: ");
		String addressBookName = scanner.nextLine();

		if (addressBooks.containsKey(addressBookName)) {
			AddressBook currentAddressBook = addressBooks.get(addressBookName);
			Contact newContact = createContact(scanner);
			currentAddressBook.contacts.add(newContact);
			System.out.println("Contact added to " + addressBookName + " successfully!\n");
			addToDictionary(newContact.city, newContact, cityDictionary);
			addToDictionary(newContact.state, newContact, stateDictionary);
		} else {
			System.out.println("Address Book not found with the given name.\n");
		}
	}

	/**
	 * @desc Prompts the user for contact information and returns a new Contact
	 *       object.
	 * @params scanner Scanner object for user input.
	 * @return A new Contact object.
	 */
	private static Contact createContact(Scanner scanner) {
		System.out.print("Enter First Name: ");
		String firstName = scanner.nextLine();

		System.out.print("Enter Last Name: ");
		String lastName = scanner.nextLine();

		System.out.print("Enter Address: ");
		String address = scanner.nextLine();

		System.out.print("Enter City: ");
		String city = scanner.nextLine();

		System.out.print("Enter State: ");
		String state = scanner.nextLine();

		System.out.print("Enter ZIP Code: ");
		String zip = scanner.nextLine();

		System.out.print("Enter Phone Number: ");
		String phoneNumber = scanner.nextLine();

		System.out.print("Enter Email: ");
		String email = scanner.nextLine();

		return new Contact(firstName, lastName, address, city, state, zip, phoneNumber, email);
	}

	/**
	 * @desc Prompts the user for the Address Book name, finds the contact based on
	 *       First Name, allows the user to edit the contact, and updates the
	 *       contact in the Address Book.
	 * @params addressBooks scanner
	 * @return null
	 */
	private static void editContact(HashMap<String, AddressBook> addressBooks, Scanner scanner) {
		System.out.print("Enter the name of the Address Book to edit the contact in: ");
		String addressBookName = scanner.nextLine();

		if (addressBooks.containsKey(addressBookName)) {
			AddressBook currentAddressBook = addressBooks.get(addressBookName);

			if (currentAddressBook.contacts.isEmpty()) {
				System.out.println("No contacts found in " + addressBookName + ". Cannot edit.\n");
			} else {
				System.out.print("Enter the First Name of the contact you want to edit: ");
				String firstNameToEdit = scanner.nextLine();

				boolean contactFound = false;

				for (Contact contact : currentAddressBook.contacts) {
					if (contact.firstName.equalsIgnoreCase(firstNameToEdit)) {
						System.out.println("Contact found. Enter new information:");

						Contact editedContact = createContact(scanner);

						contact.firstName = editedContact.firstName;
						contact.lastName = editedContact.lastName;
						contact.address = editedContact.address;
						contact.city = editedContact.city;
						contact.state = editedContact.state;
						contact.zip = editedContact.zip;
						contact.phoneNumber = editedContact.phoneNumber;
						contact.email = editedContact.email;

						contactFound = true;
						System.out.println("Contact updated successfully!\n");
						break;
					}
				}

				if (!contactFound) {
					System.out.println("Contact not found with the given First Name in " + addressBookName + ".\n");
				}
			}
		} else {
			System.out.println("Address Book not found with the given name.\n");
		}
	}

	/**
	 * @desc Prompts the user for the Address Book name, finds the contact based on
	 *       First Name, and removes the contact from the Address Book.
	 * @params addressBooks scanner
	 * @return null
	 */
	private static void deleteContact(HashMap<String, AddressBook> addressBooks, Scanner scanner) {
		System.out.print("Enter the name of the Address Book to delete the contact from: ");
		String addressBookName = scanner.nextLine();

		if (addressBooks.containsKey(addressBookName)) {
			AddressBook currentAddressBook = addressBooks.get(addressBookName);

			if (currentAddressBook.contacts.isEmpty()) {
				System.out.println("No contacts found in " + addressBookName + ". Cannot delete.\n");
			} else {
				System.out.print("Enter the First Name of the contact you want to delete: ");
				String firstNameToDelete = scanner.nextLine();

				boolean contactFound = false;

				for (Iterator<Contact> iterator = currentAddressBook.contacts.iterator(); iterator.hasNext();) {
					Contact contact = iterator.next();
					if (contact.firstName.equalsIgnoreCase(firstNameToDelete)) {
						iterator.remove();
						contactFound = true;
						System.out.println("Contact deleted successfully from " + addressBookName + "!\n");
						break;
					}
				}

				if (!contactFound) {
					System.out.println("Contact not found with the given First Name in " + addressBookName + ".\n");
				}
			}
		} else {
			System.out.println("Address Book not found with the given name.\n");
		}
	}

	/**
	 * @desc Prompts the user for the city or state, searches for persons matching
	 *       the input, and displays the search results.
	 * @params addressBooks scanner
	 * @return null
	 */
	private static void searchPersonsInCityState(HashMap<String, AddressBook> addressBooks, Scanner scanner) {
		System.out.print("Enter the city or state to search for persons: ");
		String cityOrStateToSearch = scanner.nextLine();

		List<Contact> searchResults = addressBooks.values().stream()
				.flatMap(addressBook -> addressBook.contacts.stream())
				.filter(contact -> contact.city.equalsIgnoreCase(cityOrStateToSearch)
						|| contact.state.equalsIgnoreCase(cityOrStateToSearch))
				.collect(Collectors.toList());

		if (searchResults.isEmpty()) {
			System.out.println("No persons found in the given city or state.\n");
		} else {
			System.out.println("Persons found in the given city or state:\n");
			searchResults.forEach(System.out::println);
		}
	}

	/**
	 * @desc Adds a contact to the dictionaries for city and state for efficient
	 *       searches.
	 * @params key The city or state to use as the key in the dictionary.
	 * @params contact The contact to add to the dictionary.
	 * @params dictionary The dictionary to update.
	 * @return null
	 */
	private static void addToDictionary(String key, Contact contact, HashMap<String, List<Contact>> dictionary) {
		dictionary.computeIfAbsent(key, k -> new ArrayList<>()).add(contact);
	}

	/**
	 * @desc Prompts the user for the city, retrieves and displays the persons in
	 *       the specified city.
	 * @params null
	 * @return null
	 */
	private static void viewPersonsByCity() {
		System.out.print("Enter the city to view persons: ");
		String cityToView = scanner.nextLine();
		List<Contact> searchResults = addressBooks.values().stream()
				.flatMap(addressBook -> addressBook.contacts.stream())
				.filter(contact -> contact.city.equalsIgnoreCase(cityToView))
				.collect(Collectors.toList());
		System.out.println("The contacts from the city are:- "+searchResults);

	}

	/**
	 * @desc Prompts the user for the state, retrieves and displays the persons in
	 *       the specified state.
	 * @params null
	 * @return null
	 */
	private static void viewPersonsByState() {
		System.out.print("Enter the state to view persons: ");
		String stateToView = scanner.nextLine();
		List<Contact> searchResults = addressBooks.values().stream()
				.flatMap(addressBook -> addressBook.contacts.stream())
				.filter(contact -> contact.state.equalsIgnoreCase(stateToView))
				.collect(Collectors.toList());
		System.out.println("The contacts from the state are:- "+searchResults);
	}

	/**
	 * @desc Prompts the user for the city, calculates and displays the contact
	 *       count for the specified city.
	 * @params null
	 * @return null
	 */
	private static void getContactCountByCity() {
		System.out.print("Enter the city to get contact count: ");
		String cityToCount = scanner.nextLine();

		long contactCount = addressBooks.values().stream()
				.flatMap(addressBook -> addressBook.contacts.stream())
				.filter(contact -> contact.city.equalsIgnoreCase(cityToCount)).count();

		System.out.println("Contact count in " + cityToCount + ": " + contactCount);
	}

	/**
	 * @desc Prompts the user for the state, calculates and displays the contact
	 *       count for the specified state.
	 * @params null
	 * @return null
	 */
	private static void getContactCountByState() {
		System.out.print("Enter the state to get contact count: ");
		String stateToCount = scanner.nextLine();

		long contactCount =addressBooks.values().stream()
				.flatMap(addressBook -> addressBook.contacts.stream())
				.filter(contact -> contact.state.equalsIgnoreCase(stateToCount)).count();

		System.out.println("Contact count in " + stateToCount + ": " + contactCount);
	}

	/**
	 * @desc: Prompts the user for the Address Book name, retrieves and displays the
	 *        contacts in the specified Address Book.
	 * @params: addressBooks The HashMap containing existing Address Books.
	 * @return: null
	 */
	private static void displayContacts(HashMap<String, AddressBook> addressBooks) {
		System.out.print("Enter the name of the Address Book to display contacts: ");
		Scanner scanner = new Scanner(System.in);
		String addressBookName = scanner.nextLine();

		if (addressBooks.containsKey(addressBookName)) {
			AddressBook currentAddressBook = addressBooks.get(addressBookName);
			if (currentAddressBook.contacts.isEmpty()) {
				System.out.println("No contacts found in " + addressBookName + ".\n");
			} else {
				System.out.println("Contacts in " + addressBookName + ":\n");
				for (Contact contact : currentAddressBook.contacts) {
					System.out.println(contact);
				}
			}
		} else {
			System.out.println("Address Book not found with the given name.\n");
		}
	}
}