package com.example.rxjava.adapter.output.repository;

import com.example.rxjava.adapter.output.repository.entity.Author;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Kei Takayama
 * Created on 6/12/20.
 */

@Repository
public interface AuthorJpaRepository extends JpaRepository<Author, String> {
}
