package com.cache.demo;

import com.cache.demo.entity.Book;
import com.cache.demo.service.BookService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@EnableCaching
@SpringBootApplication
public class DemoApplication {
    private static final String ISBN = "isbn";
    private static final String ISBN_1 = ISBN + "1";

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(DemoApplication.class, args);
        BookService bookService = run.getBean(BookService.class);
        bookService.saveBook(new Book(ISBN_1, "title"));
        bookService.saveBook(new Book(ISBN + "2", "title"));
        bookService.saveBook(new Book(ISBN + "3", "title"));

        List<Book> books = bookService.findAllByIsbn(ISBN_1);
        System.out.println(books);
//        dao will be called because key is another
        bookService.findAllByIsbn(ISBN);
//        will not be called because it will use cache
        bookService.findAllByIsbn(ISBN);

        bookService.deleteBookByIsbnWithEvict(ISBN_1);
//        in this case find all by ISBN will return deleted objects
//        bookService.deleteBookByIsbnWithoutEvict(ISBN_1);

        List<Book> books1 = bookService.findAllByIsbn(ISBN_1);
        System.out.println(books1);

        bookService.saveBookWithUpdateCache(new Book(ISBN_1, "title"));
        List<Book> allByIsbn = bookService.findAllByIsbn(ISBN_1);
        System.out.println(allByIsbn);

    }

}
