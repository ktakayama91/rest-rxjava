package com.example.rxjava.adapter.input.web;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import com.example.rxjava.adapter.input.web.request.AddBookWebRequest;
import com.example.rxjava.adapter.input.web.request.UpdateBookWebRequest;
import com.example.rxjava.adapter.input.web.response.BaseWebResponse;
import com.example.rxjava.adapter.input.web.response.BookWebResponse;
import com.example.rxjava.domain.BookModel;
import com.example.rxjava.usecase.business.model.request.AddBookRequest;
import com.example.rxjava.usecase.business.model.request.UpdateBookRequest;
import com.example.rxjava.usecase.port.input.BookUseCase;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Kei Takayama
 * Created on 6/12/20.
 */
@RestController
@RequestMapping(value = "/api/books")
public class BookController {

    private BookUseCase bookUseCase;

    public BookController(BookUseCase bookUseCase) {
        this.bookUseCase = bookUseCase;
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Single<ResponseEntity<BaseWebResponse>> addBook(
            @RequestBody AddBookWebRequest addBookWebRequest) {
        return bookUseCase.addBook(toAddBookRequest(addBookWebRequest))
                .subscribeOn(Schedulers.io())
                .map(s -> ResponseEntity.created(URI.create("/api/books/" + s))
                        .body(BaseWebResponse.successNoData()));
    }

    private AddBookRequest toAddBookRequest(AddBookWebRequest addBookWebRequest) {
        AddBookRequest addBookRequest = new AddBookRequest();
        BeanUtils.copyProperties(addBookWebRequest, addBookRequest);
        return addBookRequest;
    }

    @PutMapping(
            value = "/{bookId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Single<ResponseEntity<BaseWebResponse>> updateBook(@PathVariable(value = "bookId") String bookId,
                                                              @RequestBody UpdateBookWebRequest updateBookWebRequest) {
        return bookUseCase.updateBook(toUpdateBookRequest(bookId, updateBookWebRequest))
                .subscribeOn(Schedulers.io())
                .toSingle(() -> ResponseEntity.ok(BaseWebResponse.successNoData()));
    }

    private UpdateBookRequest toUpdateBookRequest(String bookId, UpdateBookWebRequest updateBookWebRequest) {
        UpdateBookRequest updateBookRequest = new UpdateBookRequest();
        BeanUtils.copyProperties(updateBookWebRequest, updateBookRequest);
        updateBookRequest.setId(bookId);
        return updateBookRequest;
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Single<ResponseEntity<BaseWebResponse<List<BookWebResponse>>>> getAllBooks(@RequestParam(value = "limit", defaultValue = "5") int limit,
                                                                                      @RequestParam(value = "page", defaultValue = "0") int page) {
        return bookUseCase.getAllBooks(limit, page)
                .subscribeOn(Schedulers.io())
                .map(bookResponses -> ResponseEntity.ok(BaseWebResponse.successWithData(toBookWebResponseList(bookResponses))));
    }

    private List<BookWebResponse> toBookWebResponseList(List<BookModel> bookResponseList) {
        return bookResponseList
                .stream()
                .map(this::toBookWebResponse)
                .collect(Collectors.toList());
    }

    private BookWebResponse toBookWebResponse(BookModel bookResponse) {
        BookWebResponse bookWebResponse = new BookWebResponse();
        BeanUtils.copyProperties(bookResponse, bookWebResponse);
        bookWebResponse.setAuthorName(bookResponse.getAuthor().getName());
        return bookWebResponse;
    }

    @GetMapping(
            value = "/{bookId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Single<ResponseEntity<BaseWebResponse<BookWebResponse>>> getBookDetail(@PathVariable(value = "bookId") String bookId) {
        return bookUseCase.getBookDetail(bookId)
                .subscribeOn(Schedulers.io())
                .map(bookResponse -> ResponseEntity.ok(BaseWebResponse.successWithData(toBookWebResponse(bookResponse))));
    }

    @DeleteMapping(
            value = "/{bookId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Single<ResponseEntity<BaseWebResponse>> deleteBook(@PathVariable(value = "bookId") String bookId) {
        return bookUseCase.deleteBook(bookId)
                .subscribeOn(Schedulers.io())
                .toSingle(() -> ResponseEntity.ok(BaseWebResponse.successNoData()));
    }

}
