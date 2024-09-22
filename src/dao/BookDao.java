package dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import dao.entity.Books;

public interface BookDao {
	
     int addBook(Books book) throws SQLException;
    
     boolean updateBook(Books book, int empId)throws SQLException;
     
     int deleteABook(int book_id) throws SQLException;
     
     List<Books> viewAllBooks() throws SQLException;
     
     Books showBookById(int empId) throws SQLException;

	void displayBooks(String orderBy);
	
	
	// it is the interface and the bookdaoimpl is define these interface
}
