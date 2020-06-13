package com.example.rxjava.usecase.port.input;

import java.util.List;
import java.util.Optional;

import com.example.rxjava.adapter.input.web.request.AddBookWebRequest;
import com.example.rxjava.domain.BookModel;
import com.example.rxjava.usecase.business.model.request.AddBookRequest;
import com.example.rxjava.usecase.business.model.request.UpdateBookRequest;
import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * @author Kei Takayama
 * Created on 6/12/20.
 */

public interface BookUseCase {

    Single<BookModel> addBook(AddBookRequest addBookRequest);

    Completable updateBook(UpdateBookRequest updateBookRequest);

    Single<List<BookModel>> getAllBooks(int limit, int page);

    Single<BookModel> getBookDetail(String id);

    Completable deleteBook(String id);
}
