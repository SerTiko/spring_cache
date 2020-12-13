package com.cache.demo.service;

import com.cache.demo.entity.Book;

import java.util.List;

public interface BookService {

    List<Book> findAllByIsbn(String isbn);

    Book saveBook(Book book);

    void deleteBookByIsbnWithoutEvict(String isbn);

    void deleteBookByIsbnWithEvict(String isbn);

    Book saveBookWithUpdateCache(Book book);
}
