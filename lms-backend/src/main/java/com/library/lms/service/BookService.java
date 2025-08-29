package com.library.lms.service;

import com.library.lms.entity.Book;
import com.library.lms.exception.NotFoundException;
import com.library.lms.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository repo;

    public BookService(BookRepository repo) {
        this.repo = repo;
    }

    public List<Book> getAll() {
        return repo.findAll();
    }

    public Book getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found with id: " + id));
    }

    public Book create(Book book) {
        return repo.save(book);
    }

    public Book update(Long id, Book updated) {
        Book existing = getById(id);
        existing.setTitle(updated.getTitle());
        existing.setAuthor(updated.getAuthor());
        existing.setIsbn(updated.getIsbn());
        existing.setStatus(updated.getStatus());
        return repo.save(existing);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
