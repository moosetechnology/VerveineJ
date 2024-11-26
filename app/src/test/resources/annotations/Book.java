package ad_hoc.annotations;

/**
 * Commentaire ajouté pour tester UTF-8
 * (A comment with accent to test UTF-8)
 */

import java.lang.annotation.Retention;
import java.util.Date;

public class Book {

	public static final Book DESIGN_PATTERNS = new Book("Design Patterns", 
			"Erich Gamma, Richard Helm, Ralph Johnson, John Vlissides");
	
	private String author;

	private String isbn;

	private String publication;

	private String title;

	@XmlElement(name = "Time", required = true, type = String.class)
	protected Date time;
	
	public Book(String title, String author) {
		this.author = author;
		this.title = title;
	}

	@GetProperty("Autor")
	public String getAuthor() {
		return author;
	}

	@GetProperty("ISBN")
	public String getIsbn() {
		return isbn;
	}

	@GetProperty("Publikation")
	public String getPublication() {
		return publication;
	}

	@GetProperty("Titel")
	public String getTitle() {
		return title;
	}

	@SetProperty("Autor")
	public void setAuthor(String author) {
		this.author = author;
	}

	@SetProperty("ISBN")
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	@SetProperty("Publikation")
	public void setPublication(String publication) {
		this.publication = publication;
	}

	@SetProperty("Titel")
	public void setTitle(String title) {
		this.title = title;
	}

	public String toString() {
		return "\"" + title + "\" by " + author; 
	}

	public Book() {
		super();
	}

}
