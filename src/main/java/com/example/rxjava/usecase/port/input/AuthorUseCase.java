package com.example.rxjava.usecase.port.input;

import com.example.rxjava.domain.AuthorModel;
import com.example.rxjava.usecase.business.model.request.AddAuthorRequest;
import io.reactivex.Single;

/**
 * @author Kei Takayama
 * Created on 6/12/20.
 */

public interface AuthorUseCase {

    Single<AuthorModel> addAuthor(AddAuthorRequest addAuthorRequest);
}
