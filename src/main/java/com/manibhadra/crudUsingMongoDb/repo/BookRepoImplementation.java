package com.manibhadra.crudUsingMongoDb.repo;

import com.manibhadra.crudUsingMongoDb.exception.DatabaseOperationException;
import com.manibhadra.crudUsingMongoDb.exception.ResourceNotFoundException;
import com.manibhadra.crudUsingMongoDb.model.BookStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BookRepoImplementation implements BookRepo {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public boolean existsByAuthorMobNum(String authorMobNum) {
        Query query = new Query();
        query.addCriteria(Criteria.where("authorMobNum").is(authorMobNum));
        return mongoTemplate.exists(query, BookStore.class);
    }

    @Override
    public boolean existsByAuthorEmailId(String authorEmailId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("authorEmailId").is(authorEmailId));
        return mongoTemplate.exists(query, BookStore.class);
    }

    @Override
    public BookStore save(BookStore bookStore) {
        return mongoTemplate.save(bookStore);
    }

    @Override
    public List<BookStore> findAll() {
        List<BookStore> bookStores = mongoTemplate.findAll(BookStore.class);
        if (bookStores.isEmpty())
            throw new DatabaseOperationException(("failed to find all book stores "));
        return bookStores;
    }


    @Override
    public List<BookStore> findByIsDeletedFalse() {
        Query query = new Query();
        query.addCriteria(Criteria.where("deleted").is(false));
        List<BookStore> bookStores = mongoTemplate.find(query, BookStore.class);
        if (bookStores.isEmpty())
            throw new DatabaseOperationException("failed to find book store with deleted = false");
        return bookStores;
    }

    @Override
    public Optional<BookStore> findById(String bookId) {
        BookStore bookStore = mongoTemplate.findById(bookId, BookStore.class);
        if (bookStore == null)
            throw new ResourceNotFoundException("BookStore not found for id " + bookId);
        return Optional.of(bookStore);
    }

    @Override
    public Boolean existsById(String bookId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(bookId));
        return mongoTemplate.exists(query, BookStore.class);
    }

    @Override
    public List<BookStore> findByDeletedTrue() {
        Query query = new Query();
        query.addCriteria(Criteria.where("deleted").is(true));
        List<BookStore> bookStores = mongoTemplate.find(query, BookStore.class);
        if (bookStores.isEmpty())
            throw new DatabaseOperationException("failed to book store with deleted =  true");
        return bookStores;
    }

    @Override
    public void deleteById(String bookId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(bookId));
        mongoTemplate.remove(query, BookStore.class);
    }
}
