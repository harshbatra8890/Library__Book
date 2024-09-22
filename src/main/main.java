package main;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import dao.BookDao;
import dao.BookDaoImpl;
import dao.entity.Books;
import utils.DBUtils;

public class main {
	public static void main(String[] args) {
		createTableAuto();

		Scanner sc = new Scanner(System.in);
		int option = -1;

		while (true) {
			showMenu();
			System.out.print("\n Which option you want to choose: ");
			try {
				option = sc.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("\n You have entered the wrong input");

			}

			sc.nextLine();

			if (option == 6) {
				System.out.print("\n Are you sure you want to exit (y/n): ");
				String input = sc.nextLine();

				if (input.length() == 1) {
					char ch = input.charAt(0);
					if (ch == 'y' || ch == 'Y') {
						System.out.println("\n Thanks for using our portal.");
						System.out.println("\n Visit again soon!!");
						System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
						sc.close();
						return;
					} else if (ch == 'n' || ch == 'N') {
						continue;
					}

				}
			}

			handleMenuOption(option);
		}
	}

	private static void createTableAuto() {
		String createTable = "CREATE TABLE `harsh_books` (\r\n" + "    `title` varchar(30) DEFAULT NULL,\r\n"
				+ "    `book_id` int NOT NULL AUTO_INCREMENT,\r\n" + "    `author_name` varchar(35) DEFAULT NULL,\r\n"
				+ "    `price` decimal(7,2) DEFAULT NULL,\r\n" + "    PRIMARY KEY (`book_id`)\r\n" + ");\r\n" + "";

		try (Connection conn = DriverManager.getConnection(DBUtils.getUrl(), DBUtils.getUsername(),
				DBUtils.getPassword()); Statement stmt = conn.createStatement();) {

			int result;
			try {
				result = stmt.executeUpdate(createTable);
				if (result == 0) {
					System.out.println("\n Table created successfully!");
				} else {
					System.out.println("\n Table creation failed!");
				}
			} catch (SQLSyntaxErrorException e) {
				System.out.println("\n Table already exists!");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void showMenu() {
		System.out.println("\n Select an option:");
		System.out.println("\n 1. Add a Book");
		System.out.println("\n 2. Update a Book");
		System.out.println("\n 3. Delete a Book");
		System.out.println("\n 4. View a book");
		System.out.println("\n 5. View all Books");
		System.out.println("\n 6. Exit Menu");

	}

	private static void handleMenuOption(int option) {
		switch (option) {
		case 1:
			addBook();
			break;
		case 2:
			updateBook();
			break;
		case 3:
			deleteBook();
			break;
		case 4:
			viewBook();
			break;
		case 5:
			viewAllBooks();
			break;
		case 6:

			break;
		default:
			System.out.println("\n Invalid option. Please try again.");
			break;
		}
	}

	private static void addBook() {
		Scanner sc = new Scanner(System.in);
		System.out.print("\n Enter the title : ");
		String title = sc.nextLine();

		System.out.print("\n Enter price: ");
		double price =0;
		try {
			price = sc.nextDouble();
		} catch (InputMismatchException e1) {
			System.out.println("\n Enter the compatabile input Your input is incorrect...");
		}

		sc.nextLine();

		System.out.print("\n Enter author_name: ");
		String author_name = sc.nextLine();

		Books book = new Books(title, price, author_name);

		BookDao bookDao = new BookDaoImpl();
		try {
			if (bookDao.addBook(book) > 0) {
				System.out.println("\n Result: [The Book " + book.getTitle() + " is added successfully]");
			} else {
				System.out.println("\n Can't add the book");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(DBUtils.ERROR_MSG);
		}

	}

	private static void updateBook() {
		Scanner sc = new Scanner(System.in);

		System.out.print("\n Enter the id of book to update: ");
		int book_id = sc.nextInt();
		sc.nextLine();

		BookDao dao = new BookDaoImpl();
		try {
			Books book = dao.showBookById(book_id);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		System.out.print("\n Enter name of the new book: ");
		String name = sc.nextLine();

		System.out.print("\n Price of the new book: ");
		double price = sc.nextDouble();

		sc.nextLine();

		System.out.print("\n Enter the author name of new book: ");
		String author_name = sc.nextLine();

		Books book = new Books(name, price, author_name);
		try {
			if (dao.updateBook(book, book_id)) {
				System.out
						.println("\n The Book: " + book.getTitle() + " with id: " + book_id + " updated successfully");
			} else {
				System.out.println("\n Can't modify the book with id: " + book_id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void deleteBook() {
		Scanner sc = new Scanner(System.in);
		System.out.print("\n Enter the id of the book that you want to delete: ");
		int book_id = sc.nextInt();

		BookDao dao = new BookDaoImpl();
		Books book = null;
		try {
			book = dao.showBookById(book_id);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		try {
			if (dao.deleteABook(book_id) > 0) {
				System.out.println(
						"\n The book: " + book.getTitle() + " with id:  " + book_id + " is deleted successfully!");
			} else {
				System.out.println("\n Book with id: " + book_id + " not found!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private static void viewBook() {
		Scanner sc = new Scanner(System.in);
		System.out.print("\n Enter the id of the book that you want to view: ");
		int book_id = sc.nextInt();

		BookDao dao = new BookDaoImpl();
		try {
			Books book = dao.showBookById(book_id);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	private static void viewAllBooks() {
		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.println("\n How do you want to display the list?");
			System.out.println("\n 1. In Ascending order of id");
			System.out.println("\n 2. In Descending order of id");
			System.out.println("\n 3. In Ascending order of book name");
			System.out.println("\n 4. In Descending order of book name");
			System.out.println("\n 5. In Ascending order of price");
			System.out.println("\n 6. In Descending order of price");
			System.out.println("\n 7. Exit");

			System.out.print("\n Select one option from above: ");
			int option = 0;
			try {
				option = sc.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("\n Please Enter a number!");
			}
			sc.nextLine();

			String orderBy = "";
			switch (option) {
			case 1:
				orderBy = "ORDER BY book_id ASC";
				break;
			case 2:
				orderBy = "ORDER BY book_id DESC";
				break;
			case 3:
				orderBy = "ORDER BY title ASC";
				break;
			case 4:
				orderBy = "ORDER BY title DESC";
				break;
			case 5:
				orderBy = "ORDER BY price ASC";
				break;
			case 6:
				orderBy = "ORDER BY price DESC";
				break;
			case 7:
				return;
			default:
				System.out.println("\n Invalid option. Please write the correct input"
						+ ".");
				continue;
			}

			BookDao dao = new BookDaoImpl();
			dao.displayBooks(orderBy);
		}
	}
}
