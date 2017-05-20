package my.spike.elasticsearch.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import my.spike.elasticsearch.model.Book;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.search.SearchHit;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

@Service
public class BookRepository {

	private TransportClient client;
	private ObjectMapper objectMapper;

	public BookRepository(TransportClient transportClient, ObjectMapper objectMapper) {
		this.client = transportClient;
		this.objectMapper = objectMapper;
	}

	public List<Book> getBooks() {
		final ArrayList<Book> books = new ArrayList<>();
		final SearchResponse response = client.prepareSearch("my-index")
				.setTypes("book")
				.get();
		for (SearchHit r: response.getHits()) {
				try {
					books.add(objectMapper.readValue(r.getSourceAsString(), Book.class));
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
		}
		return books;
	}

	public void save(Book book) {
		try {
			IndexResponse response = client.prepareIndex("my-index", "book", book.getIsbn())
					.setSource(jsonBuilder()
							.startObject()
							.field("description", book.getDescription())
							.field("author", book.getAuthor())
							.endObject()
					).get();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
