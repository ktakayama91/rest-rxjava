package com.example.rxjava.usecase.port.outport;

import java.util.List;

import com.example.rxjava.domain.BookModel;
import com.example.rxjava.usecase.business.model.request.AddBookRequest;
import com.example.rxjava.usecase.business.model.request.UpdateBookRequest;
import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * @author Kei Takayama
 * Created on 6/12/20.
 */

public interface BookRepositoryPort {

    Single<BookModel> save(AddBookRequest addBookRequest);

    Completable update(UpdateBookRequest updateBookRequest);

    Single<List<BookModel>> findAll(int limit, int page);

    Single<BookModel> findById(String bookId);

    Completable delete(String id);
}
