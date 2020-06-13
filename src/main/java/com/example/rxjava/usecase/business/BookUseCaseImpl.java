package com.example.rxjava.usecase.business;

import java.util.List;

import com.example.rxjava.domain.BookModel;
import com.example.rxjava.usecase.business.model.request.AddBookRequest;
import com.example.rxjava.usecase.business.model.request.UpdateBookRequest;
import com.example.rxjava.usecase.port.input.BookUseCase;
import com.example.rxjava.usecase.port.outport.BookRepositoryPort;
import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * @author Kei Takayama
 * Created on 6/12/20.
 */

public class BookUseCaseImpl implements BookUseCase {

    private BookRepositoryPort bookRepository;

    public BookUseCaseImpl(BookRepositoryPort bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Single<BookModel> addBook(AddBookRequest addBookRequest) {
        return bookRepository.save(addBookRequest);
    }

    @Override
    public Completable updateBook(UpdateBookRequest updateBookRequest) {
        return bookRepository.update(updateBookRequest);
    }

    @Override
    public Single<List<BookModel>> getAllBooks(int limit, int page) {
        return bookRepository.findAll(limit, page);
    }

    @Override
    public Single<BookModel> getBookDetail(String id) {
        return bookRepository.findById(id);
    }

    @Override
    public Completable deleteBook(String id) {
        return bookRepository.delete(id);
    }
}
