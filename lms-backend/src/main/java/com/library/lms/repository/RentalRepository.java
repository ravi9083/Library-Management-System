package com.library.lms.repository;

import com.library.lms.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findByMemberId(Long memberId);
    List<Rental> findByBookId(Long bookId);
}
