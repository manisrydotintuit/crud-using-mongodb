package com.manibhadra.crudUsingMongoDb.VadidateBookStore;

import com.manibhadra.crudUsingMongoDb.model.ApiResponse;
import com.manibhadra.crudUsingMongoDb.model.BookStore;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ValidateBookStore {


    public ApiResponse createOkResponse(Object data, String message) {
        return new ApiResponse<>(data, true, HttpStatus.OK.value(), message, null);
    }

    public ApiResponse createBadRequestResponse(String errorMessage) {
        return new ApiResponse<>(null, false, HttpStatus.BAD_REQUEST.value(), errorMessage, "some error occurred");
    }

    public ApiResponse createNotFoundResponse(String bookDetailsNotFound) {
        return new ApiResponse<>(null, false, HttpStatus.NOT_FOUND.value(), "Book details not found", bookDetailsNotFound);

    }
}
