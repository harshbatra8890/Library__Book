package dao.entity;

public class Books {
	// `title`, `book_id`, `price` `author_name`
	private String title;
	private int book_id;
	private String author_name;
	private double price;

	public Books() {
		super();
	}

	public Books(String title, double price, String author_name) {
		super();
		this.title = title;
		this.price = price;
		this.author_name = author_name;

	}

	public Books(int book_id, String title, String author_name, double price) {
		super();
		this.title = title;
		this.book_id = book_id;
		this.author_name = author_name;
		this.price = price;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getBook_id() {
		return book_id;
	}

	public void setBook_id(int book_id) {
		this.book_id = book_id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getAuthor_name() {
		return author_name;
	}

	public void setAuthor_name(String author_name) {
		this.author_name = author_name;
	}

	@Override
	public String toString() {
		return "\n Books [title = " + title + ", book_id = " + book_id + ", author_name = " + author_name + ", price = "
				+ price + "]";
	}

}
