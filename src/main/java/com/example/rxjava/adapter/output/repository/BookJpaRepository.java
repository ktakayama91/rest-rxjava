package com.example.rxjava.adapter.output.repository;

import com.example.rxjava.adapter.output.repository.entity.Book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Kei Takayama
 * Created on 6/12/20.
 */

@Repository
public interface BookJpaRepository extends JpaRepository<Book, String> {
}
