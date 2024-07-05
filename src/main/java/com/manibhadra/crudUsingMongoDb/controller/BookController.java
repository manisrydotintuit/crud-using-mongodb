package com.manibhadra.crudUsingMongoDb.controller;

import com.manibhadra.crudUsingMongoDb.model.ApiResponse;
import com.manibhadra.crudUsingMongoDb.model.BookStore;
import com.manibhadra.crudUsingMongoDb.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping("/addBook")
    public ResponseEntity<ApiResponse<BookStore>> saveBook(@RequestBody BookStore bookStore) {
        BookStore savedBook = bookService.saveBook(bookStore);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(
                        savedBook,
                        true,
                        HttpStatus.OK.value(),
                        "Book is uploaded successfully",
                        null));
    }

    @GetMapping("/getBooks")
    public ResponseEntity<ApiResponse<List<BookStore>>> getBooks() {
        List<BookStore> bookStoreList = bookService.getAllBooks();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(
                        bookStoreList,
                        true,
                        HttpStatus.OK.value(),
                        "Data fetched successfully",
                        null));
    }

    @GetMapping("/getBook/{bookId}")
    public ResponseEntity<ApiResponse<BookStore>> getBook(@PathVariable("bookId") String bookId) {
        BookStore bookStore = bookService.getBook(bookId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(
                        bookStore,
                        true,
                        HttpStatus.OK.value(),
                        "Data is successfully fetched for the id " + bookId,
                        null));
    }

    @PutMapping("/updateBookDetails/{bookId}")
    public ResponseEntity<ApiResponse<BookStore>> updateBookDetails(@PathVariable("bookId") String bookId, @RequestBody BookStore bookStore) {
        BookStore updatedBook = bookService.updateBookDetails(bookId, bookStore);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(
                        updatedBook,
                        true,
                        HttpStatus.OK.value(),
                        "BookStore updated successfully",
                        null));
    }

    @DeleteMapping("/hardDeleteBookDetails/{bookId}")
    public ResponseEntity<ApiResponse<Boolean>> hardDeleteBookDetails(@PathVariable("bookId") String bookId) {
        Boolean exists = bookService.bookExists(bookId);
        bookService.deleteBookDetails(bookId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(
                        true,
                        true,
                        HttpStatus.OK.value(),
                        "BookStore details are deleted successfully",
                        null));
    }

    @DeleteMapping("/softDeleteBookDetails/{bookId}")
    public ResponseEntity<ApiResponse<Boolean>> softDeleteBookDetails(@PathVariable("bookId") String bookId) {
        Boolean exists = bookService.bookExists(bookId);
        bookService.deleteBookDetailsSoftly(bookId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(
                        true,
                        true,
                        HttpStatus.OK.value(),
                        "BookStore details are deleted successfully",
                        null));
    }

    @GetMapping("/getDeletedDataOnly")
    public ResponseEntity<ApiResponse<List<BookStore>>> getDeletedDataOnly() {
        List<BookStore> deletedBooks = bookService.getDeletedDataOnly();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(
                        deletedBooks,
                        true,
                        HttpStatus.OK.value(),
                        "Deleted books fetched successfully",
                        null));
    }

    @GetMapping("/getAllBookDetailsIncludingDeleted")
    public ResponseEntity<ApiResponse<List<BookStore>>> getAllBookDetailsIncludingDeleted() {
        List<BookStore> allBooks = bookService.getAllBookDetailsIncludingDeleted();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(
                        allBooks,
                        true,
                        HttpStatus.OK.value(),
                        "All books (including deleted) fetched successfully",
                        null));
    }
}
