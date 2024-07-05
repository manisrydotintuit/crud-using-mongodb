package com.manibhadra.crudUsingMongoDb.repo;

import com.manibhadra.crudUsingMongoDb.model.BookStore;

import java.util.List;
import java.util.Optional;

public interface BookRepo {
    boolean existsByAuthorMobNum(String authorMobNum);

    boolean existsByAuthorEmailId(String authorEmailId);

    BookStore save(BookStore bookStore);

    List<BookStore> findAll();

    List<BookStore> findByIsDeletedFalse();

    Optional<BookStore> findById(String bookId);

    Boolean existsById(String bookId);

    List<BookStore> findByDeletedTrue();

    void deleteById(String bookId);
}




//import com.mongodb.client.MongoClient;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.mongodb.MongoDatabaseFactory;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.convert.MongoConverter;
//import org.springframework.data.mongodb.repository.MongoRepository;




//@Repository
//public interface BookRepo extends MongoRepository<BookStore, String> {
//    boolean existsByAuthorMobNum(String authorMobNum);
//
//    boolean existsByAuthorEmailId(String authorEmailId);
//
//    List<BookStore> findByDeletedTrue();
//}
