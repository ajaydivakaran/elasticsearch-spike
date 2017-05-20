package my.spike.elasticsearch.controller;

import my.spike.elasticsearch.model.Book;
import my.spike.elasticsearch.repository.BookRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController {

	private BookRepository bookRepository;

	public BookController(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@GetMapping("/books")
	public List<Book> getBooks() {
		return bookRepository.getBooks();
	}

	@PostMapping("/book")
	public void createBook(@RequestBody Book book) {
		bookRepository.save(book);
	}
}
