package com.example.rxjava.adapter.output;

import javax.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.example.rxjava.adapter.output.repository.AuthorJpaRepository;
import com.example.rxjava.adapter.output.repository.BookJpaRepository;
import com.example.rxjava.adapter.output.repository.entity.Author;
import com.example.rxjava.adapter.output.repository.entity.Book;
import com.example.rxjava.domain.AuthorModel;
import com.example.rxjava.domain.BookModel;
import com.example.rxjava.usecase.business.model.request.AddBookRequest;
import com.example.rxjava.usecase.business.model.request.UpdateBookRequest;
import com.example.rxjava.usecase.port.outport.BookRepositoryPort;

import io.reactivex.Completable;
import io.reactivex.Single;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;

/**
 * @author Kei Takayama
 * Created on 6/12/20.
 */

public class BookRepositoryAdapter implements BookRepositoryPort {

    private AuthorJpaRepository authorJpaRepository;
    private BookJpaRepository bookJpaRepository;

    public BookRepositoryAdapter(AuthorJpaRepository authorJpaRepository, BookJpaRepository bookJpaRepository) {
        this.authorJpaRepository = authorJpaRepository;
        this.bookJpaRepository = bookJpaRepository;
    }

    @Override
    public Single<BookModel> save(AddBookRequest addBookRequest) {
        return Single.create(singleSubscriber -> {
            Optional<Author> optionalAuthor = authorJpaRepository.findById(addBookRequest.getAuthorId());
            if (!optionalAuthor.isPresent())
                singleSubscriber.onError(new EntityNotFoundException());
            else {
                Book book = bookJpaRepository.save(toBook(addBookRequest));
                BookModel bookModel = toBookModel(book);
                singleSubscriber.onSuccess(bookModel);
            }
        });
    }

    private Book toBook(AddBookRequest addBookRequest) {
        Book book = new Book();
        BeanUtils.copyProperties(addBookRequest, book);
        book.setId(UUID.randomUUID().toString());
        book.setAuthor(Author.builder()
                .id(addBookRequest.getAuthorId())
                .build());
        return book;
    }

    private BookModel toBookModel(Book book) {
        return BookModel.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(toAuthorModel(book.getAuthor()))
                .build();
    }

    private AuthorModel toAuthorModel(Author author) {
        return AuthorModel.builder()
                .id(author.getId())
                .name(author.getName())
                .build();
    }

    @Override
    public Completable update(UpdateBookRequest updateBookRequest) {
        return Completable.create(completableSubscriber -> {
            Optional<Book> optionalBook = bookJpaRepository.findById(updateBookRequest.getId());
            if (!optionalBook.isPresent())
                completableSubscriber.onError(new EntityNotFoundException());
            else {
                Book book = optionalBook.get();
                book.setTitle(updateBookRequest.getTitle());
                bookJpaRepository.save(book);
                completableSubscriber.onComplete();
            }
        });
    }

    @Override
    public Single<List<BookModel>> findAll(int limit, int page) {
        return Single.create(singleSubscriber -> {
            List<BookModel> bookModels = findAllBooksInRepository(limit, page)
                    .stream()
                    .map(this::toBookModel)
                    .collect(Collectors.toList());
            singleSubscriber.onSuccess(bookModels);
        });
    }

    private List<Book> findAllBooksInRepository(int limit, int page) {
        return bookJpaRepository.findAll(PageRequest.of(page, limit)).getContent();
    }

    @Override
    public Single<BookModel> findById(String bookId) {
        return findBookDetailInRepository(bookId);
    }

    private Single<BookModel> findBookDetailInRepository(String id) {
        return Single.create(singleSubscriber -> {
            Optional<Book> optionalBook = bookJpaRepository.findById(id);
            if (!optionalBook.isPresent())
                singleSubscriber.onError(new EntityNotFoundException());
            else {
                BookModel bookResponse = toBookModel(optionalBook.get());
                singleSubscriber.onSuccess(bookResponse);
            }
        });
    }

    @Override
    public Completable delete(String id) {
        return Completable.create(completableSubscriber -> {
            Optional<Book> optionalBook = bookJpaRepository.findById(id);
            if (!optionalBook.isPresent())
                completableSubscriber.onError(new EntityNotFoundException());
            else {
                bookJpaRepository.delete(optionalBook.get());
                completableSubscriber.onComplete();
            }
        });
    }

}
