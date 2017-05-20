package my.spike.elasticsearch.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Book {
	private String isbn;
	private String description;
	private String author;
}
