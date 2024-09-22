package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class DBUtils {

	// Queries
	public final static String INSERT_QUERY1 = "INSERT INTO harsh_books(title, price, author_name) VALUES (?,?,?);";

	public final static String UPDATE_BOOK = "UPDATE harsh_books " + "SET title =?, price =?, author_name=? "
			+ "WHERE book_id=?";

	public final static String SHOW_BOOK_DETAILS = "SELECT * FROM harsh_books WHERE book_id=?";
	public final static String REMOVE_BOOK = "DELETE FROM harsh_books WHERE book_id=? ";
	public final static String SHOW_ALL_BOOKS = "SELECT * FROM harsh_books";

	public static final String ERROR_MSG = "\n Oops! Something went wrong";

	// Database connection strings
	private static String URL;
	private static String USERNAME;
	private static String PASSWORD;

	static {
		Properties credentials = new Properties(); // Initialize the Properties object
		try (BufferedReader reader = new BufferedReader(new FileReader("db_credentials.txt"))) {
			credentials.load(reader);
		} catch (IOException e) {
			System.err.println("Error reading credentials file.");
			e.printStackTrace();
		}
		URL = credentials.getProperty("URL");
		USERNAME = credentials.getProperty("USERNAME");
		PASSWORD = credentials.getProperty("PASSWORD");
	}

	public static String getUrl() {
		return URL;
	}

	public static String getUsername() {
		return USERNAME;
	}

	public static String getPassword() {
		return PASSWORD;
	}
	
	public static void main(String[] args) {
		System.out.println(URL+ "   ->    "+USERNAME+"  ->   "+PASSWORD);
	}
}
