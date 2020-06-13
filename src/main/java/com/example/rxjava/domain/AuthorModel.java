package com.example.rxjava.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Kei Takayama
 * Created on 6/12/20.
 */

@Getter
@Setter
@Builder
public class AuthorModel {
    private String id;
    private String name;
}
