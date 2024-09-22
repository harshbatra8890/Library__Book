package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import dao.entity.Books;
import utils.DBUtils;

public class BookDaoImpl implements BookDao {

	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection(DBUtils.getUrl(), DBUtils.getUsername(), DBUtils.getPassword());
	}

	@Override
	public int addBook(Books book) throws SQLException {
		int result = 0;
		try (Connection conn = getConnection()) {

			// Prepare the statement
			PreparedStatement pStmt = conn.prepareStatement(DBUtils.INSERT_QUERY1);

			// Binding the data with placeholders
			// title, price, author_name
			pStmt.setString(1, book.getTitle());
			pStmt.setDouble(2, book.getPrice());
			pStmt.setString(3, book.getAuthor_name());

			result = pStmt.executeUpdate();
		}
		return result;
	}

	@Override
	public boolean updateBook(Books book, int empId) throws SQLException {
		int result = 0;

		try (Connection conn = getConnection()) {

			// Prepare the query
			PreparedStatement pStmt = conn.prepareStatement(DBUtils.UPDATE_BOOK);
			pStmt.setString(1, book.getTitle());
			pStmt.setDouble(2, book.getPrice());
			pStmt.setString(3, book.getAuthor_name());
			pStmt.setInt(4, empId);

			result = pStmt.executeUpdate();
		}
		return result > 0;
	}

	@Override
	public int deleteABook(int book_id) throws SQLException {
		int result = 0;

		try (Connection conn = getConnection()) {
			PreparedStatement pStmt = conn.prepareStatement(DBUtils.REMOVE_BOOK);

			pStmt.setInt(1, book_id);
			result = pStmt.executeUpdate();
		}

		return result;
	}

	@Override
	public List<Books> viewAllBooks() throws SQLException {
		List<Books> booksList = new ArrayList<Books>();

		try (Connection conn = getConnection()) {
			Statement stmt = conn.createStatement();

			ResultSet rSet = stmt.executeQuery(DBUtils.SHOW_ALL_BOOKS);

			// `title`, `book_id`, `author_name`, `price`

			while (rSet.next()) {
				int book_id = rSet.getInt("book_id");
				String title = rSet.getString("title");
				double price = rSet.getDouble("price");
				String author_name = rSet.getString("author_name");

				booksList.add(new Books(book_id, title, author_name, price));
			}
		}

		return booksList;
	}

	@Override
	public Books showBookById(int empId) throws SQLException {
		Books book = null;
		try (Connection conn = getConnection()) {
			PreparedStatement pStmt = conn.prepareStatement(DBUtils.SHOW_BOOK_DETAILS);
			pStmt.setInt(1, empId);

			// `author_name`, `title`, `book_id`, `price`
			ResultSet rSet = pStmt.executeQuery();
			if (rSet.next()) {
				int bookId = rSet.getInt("book_id");
				String title = rSet.getString("title");
				String author_name = rSet.getString("author_name");
				double price = rSet.getDouble("price");

				book = new Books(bookId, title, author_name, price);
			}
		}

		System.out.println(book);

		return book;
	}

	@Override
	public void displayBooks(String orderBy) {
		String query = "SELECT * FROM harsh_books " + orderBy;
		
		try (Connection conn = getConnection();
		         Statement stmt = conn.createStatement();
		         ResultSet rs = stmt.executeQuery(query)) {

		        while (rs.next()) {
		            int id = rs.getInt("book_id");
		            String title = rs.getString("title");
		            String author = rs.getString("author_name");
		            double price = rs.getDouble("price");

		            System.out.println("\n ID: " + id + ", Title: " + title + ", Author: " + author + ", Price: " + price);
		        }

		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		
	}

}
