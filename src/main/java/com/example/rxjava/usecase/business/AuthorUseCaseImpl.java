package com.example.rxjava.usecase.business;

import com.example.rxjava.domain.AuthorModel;
import com.example.rxjava.usecase.business.model.request.AddAuthorRequest;
import com.example.rxjava.usecase.port.input.AuthorUseCase;
import com.example.rxjava.usecase.port.outport.AuthorRepositoryPort;
import io.reactivex.Single;

/**
 * @author Kei Takayama
 * Created on 6/12/20.
 */

public class AuthorUseCaseImpl implements AuthorUseCase {

    private AuthorRepositoryPort authorRepository;

    public AuthorUseCaseImpl(AuthorRepositoryPort authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Single<AuthorModel> addAuthor(AddAuthorRequest addAuthorRequest) {
        return authorRepository.save(addAuthorRequest);
    }

}
