package com.example.rxjava.adapter.input.web.response;

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
public class BookWebResponse {
    private String id;
    private String title;
    private String authorName;
}
