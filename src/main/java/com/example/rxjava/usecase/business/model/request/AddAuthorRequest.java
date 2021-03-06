package com.example.rxjava.usecase.business.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Kei Takayama
 * Created on 6/12/20.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddAuthorRequest {
    private String name;
}
