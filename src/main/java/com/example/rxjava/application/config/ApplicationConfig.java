package com.example.rxjava.application.config;

import com.example.rxjava.adapter.output.AuthorRepositoryAdapter;
import com.example.rxjava.adapter.output.BookRepositoryAdapter;
import com.example.rxjava.adapter.output.repository.AuthorJpaRepository;
import com.example.rxjava.adapter.output.repository.BookJpaRepository;
import com.example.rxjava.usecase.business.AuthorUseCaseImpl;
import com.example.rxjava.usecase.business.BookUseCaseImpl;
import com.example.rxjava.usecase.port.input.AuthorUseCase;
import com.example.rxjava.usecase.port.input.BookUseCase;
import com.example.rxjava.usecase.port.outport.AuthorRepositoryPort;
import com.example.rxjava.usecase.port.outport.BookRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Kei Takayama
 * Created on 6/12/20.
 */

@Configuration
@ComponentScan(basePackages = "com.example.rxjava.adapter")
public class ApplicationConfig {

    @Bean
    public BookRepositoryPort getBookRepositoryPort(AuthorJpaRepository authorJpaRepository, BookJpaRepository bookJpaRepository) {
        return new BookRepositoryAdapter(authorJpaRepository, bookJpaRepository);
    }

    @Bean
    public AuthorRepositoryPort getAuthorRepositoryPort(AuthorJpaRepository authorJpaRepository) {
        return new AuthorRepositoryAdapter(authorJpaRepository);
    }

    @Bean
    public BookUseCase getBookUseCase(BookRepositoryPort bookRepositoryPort) {
        return new BookUseCaseImpl(bookRepositoryPort);
    }

    @Bean
    public AuthorUseCase getAuthorUseCase(AuthorRepositoryPort authorRepositoryPort) {
        return new AuthorUseCaseImpl(authorRepositoryPort);
    }

}
