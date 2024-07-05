package com.manibhadra.crudUsingMongoDb.service;

import com.manibhadra.crudUsingMongoDb.exception.ValidationException;
import com.manibhadra.crudUsingMongoDb.model.BookStore;
import com.manibhadra.crudUsingMongoDb.repo.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class BookService {

    @Autowired
    BookRepo bookRepo;

    public boolean checkAuthorMobNumExists(String authorMobNum) {
        return bookRepo.existsByAuthorMobNum(authorMobNum);
    }

    public boolean checkAuthorEmailIdExists(String authorEmailId) {
        return bookRepo.existsByAuthorEmailId(authorEmailId);
    }

    public boolean isValidMobileNum(String mobileNum) {
        return mobileNum.matches("\\d{10}");
    }

    public boolean isValidEmailId(String emailId) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(emailId);
        return matcher.matches();
    }

    public String validateBookStore(BookStore bookStore) {
        if (bookStore.getBookName() == null || bookStore.getBookName().isEmpty()) {
            return "Book name cannot be null or empty";
        }
        if (bookStore.getAuthorName() == null || bookStore.getAuthorName().isEmpty()) {
            return "Author name cannot be null or empty";
        }
        if (bookStore.getAuthorMobNum() == null || bookStore.getAuthorMobNum().isEmpty()) {
            return "Author mobile number cannot be null";
        }
        if (bookStore.getAuthorEmailId() == null || bookStore.getAuthorEmailId().isEmpty()) {
            return "Author email id cannot be null or empty";
        }
        return null;
    }

    public BookStore saveBook(BookStore bookStore) {
        // Validate book store fields
        String validationError = validateBookStore(bookStore);
        if (validationError != null) {
            throw new ValidationException(validationError);
        }

        // Check if author's mobile number already exists
        if (checkAuthorMobNumExists(bookStore.getAuthorMobNum())) {
            throw new ValidationException("Mobile number is already used by another author");
        }

        // Check if author's email ID already exists
        if (checkAuthorEmailIdExists(bookStore.getAuthorEmailId())) {
            throw new ValidationException("Email ID is already used by another author");
        }

        // Validate mobile number format
        if (!isValidMobileNum(bookStore.getAuthorMobNum())) {
            throw new ValidationException("Mobile number should be exactly 10 digits and numeric");
        }

        // Validate email ID format
        if (!isValidEmailId(bookStore.getAuthorEmailId())) {
            throw new ValidationException("Invalid email format");
        }

        // Set published date before saving
        bookStore.setPublishedDate(LocalDateTime.now());

        // Save the book
        return bookRepo.save(bookStore);
    }

    public List<BookStore> getAllBooks() {
        return bookRepo.findByIsDeletedFalse();
    }

    public BookStore getBook(String bookId) {
        return bookRepo.findById(bookId).orElse(null);
    }

    public BookStore updateBookDetails(String bookId, BookStore bookStore) {
        BookStore existingBookStore = bookRepo.findById(bookId).orElse(null);
        if (existingBookStore == null) {
            return null;
        }

        validateAndUpdate(existingBookStore, bookStore);

        return bookRepo.save(existingBookStore);
    }

    private void validateAndUpdate(BookStore existingBookStore, BookStore bookStore) {
        if (bookStore.getBookName() != null && !bookStore.getBookName().isEmpty()) {
            existingBookStore.setBookName(bookStore.getBookName());
        }
        if (bookStore.getAuthorName() != null && !bookStore.getAuthorName().isEmpty()) {
            existingBookStore.setAuthorName(bookStore.getAuthorName());
        }

        String authorMobNum = bookStore.getAuthorMobNum();
        if (authorMobNum != null && !authorMobNum.isEmpty()) {
            existingBookStore.setAuthorMobNum(authorMobNum);
        }

        String authorEmailId = bookStore.getAuthorEmailId();
        if (authorEmailId != null && !authorEmailId.isEmpty()) {
            existingBookStore.setAuthorEmailId(authorEmailId);
        }
    }

    public Boolean bookExists(String bookId) {
        return bookRepo.existsById(bookId);
    }

    public void deleteBookDetails(String bookId) {
        bookRepo.deleteById(bookId);
    }

    @Transactional
    public void deleteBookDetailsSoftly(String bookId) {
        Optional<BookStore> optionalBookStore = bookRepo.findById(bookId);
        optionalBookStore.ifPresent(bookStore -> {
            bookStore.setDeleted(true);
            bookRepo.save(bookStore);
        });
    }

    public List<BookStore> getDeletedDataOnly() {
        return bookRepo.findByDeletedTrue();
    }

    public List<BookStore> getAllBookDetailsIncludingDeleted() {
        return bookRepo.findAll();
    }
}
