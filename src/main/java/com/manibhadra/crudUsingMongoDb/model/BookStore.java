package com.manibhadra.crudUsingMongoDb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@Document(collection = "bookDetails")
public class BookStore {

    @Id
    private String id; // Use String or ObjectId for MongoDB _id

    private String bookName;
    private String authorName;
    private String authorMobNum;
    private String authorEmailId;
    private LocalDateTime publishedDate; // Changed from publishedData to publishedDate
    private LocalDateTime updatedDate;
    private boolean deleted = false;


}
