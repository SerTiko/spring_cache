package com.cache.demo.service.impl;

import com.cache.demo.entity.Book;
import com.cache.demo.repository.BookRepository;
import com.cache.demo.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Override
    @Cacheable(value = "bookIsbn", key = "#isbn")
    public List<Book> findAllByIsbn(String isbn) {
        return bookRepository.getByIsbn(isbn);
    }

    @Override
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void deleteBookByIsbnWithoutEvict(String isbn) {
        List<Book> byIsbn = bookRepository.getByIsbn(isbn);
        bookRepository.deleteAll(byIsbn);
    }

    @Override
    @CacheEvict(cacheNames = "bookIsbn", key = "#isbn")
    public void deleteBookByIsbnWithEvict(String isbn) {
        List<Book> byIsbn = bookRepository.getByIsbn(isbn);
        bookRepository.deleteAll(byIsbn);
    }

    @Override
//    used to inform spring cache to update cache
    @CacheEvict(cacheNames = "bookIsbn", allEntries = true)
    public Book saveBookWithUpdateCache(Book book) {
        return bookRepository.save(book);
    }

}
