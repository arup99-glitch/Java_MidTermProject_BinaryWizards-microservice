package com.com.BinaryWizards.bookservice.repository;

import com.com.BinaryWizards.bookservice.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookEntity,Long> { }

