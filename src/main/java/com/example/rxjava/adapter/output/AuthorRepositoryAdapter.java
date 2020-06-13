package com.example.rxjava.adapter.output;

import java.util.UUID;

import com.example.rxjava.adapter.output.repository.AuthorJpaRepository;
import com.example.rxjava.adapter.output.repository.entity.Author;
import com.example.rxjava.domain.AuthorModel;
import com.example.rxjava.usecase.business.model.request.AddAuthorRequest;
import com.example.rxjava.usecase.port.outport.AuthorRepositoryPort;

import io.reactivex.Single;
import org.springframework.beans.BeanUtils;

/**
 * @author Kei Takayama
 * Created on 6/12/20.
 */

public class AuthorRepositoryAdapter implements AuthorRepositoryPort {

    private AuthorJpaRepository authorJpaRepository;

    public AuthorRepositoryAdapter(AuthorJpaRepository authorJpaRepository) {
        this.authorJpaRepository = authorJpaRepository;
    }

    @Override
    public Single<AuthorModel> save(AddAuthorRequest addAuthorRequest) {
        return Single.create(singleSubscriber -> {
            AuthorModel authorModel = toAuthorModel(authorJpaRepository.save(toAuthor(addAuthorRequest)));
            singleSubscriber.onSuccess(authorModel);
        });
    }

    private Author toAuthor(AddAuthorRequest addAuthorRequest) {
        Author author = new Author();
        BeanUtils.copyProperties(addAuthorRequest, author);
        author.setId(UUID.randomUUID().toString());
        return author;
    }

    private AuthorModel toAuthorModel(Author author) {
        return AuthorModel.builder()
                .id(author.getId())
                .name(author.getName())
                .build();
    }

}
