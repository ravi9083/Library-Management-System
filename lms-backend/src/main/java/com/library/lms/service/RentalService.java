package com.library.lms.service;

import com.library.lms.dto.BorrowRequest;
import com.library.lms.entity.Book;
import com.library.lms.entity.Member;
import com.library.lms.entity.Rental;
import com.library.lms.exception.BadRequestException;
import com.library.lms.exception.NotFoundException;
import com.library.lms.repository.RentalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class RentalService {

    private final RentalRepository rentalRepo;
    private final BookService bookService;
    private final MemberService memberService;

    public RentalService(RentalRepository rentalRepo, BookService bookService, MemberService memberService) {
        this.rentalRepo = rentalRepo;
        this.bookService = bookService;
        this.memberService = memberService;
    }

    public List<Rental> getAll() {
        return rentalRepo.findAll();
    }

    public Rental getById(Long id) {
        return rentalRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Rental not found with id: " + id));
    }

    @Transactional
    public Rental borrow(BorrowRequest req) {
        Book book = bookService.getById(req.getBookId());
        if (book.getStatus() != Book.Status.AVAILABLE) {
            throw new BadRequestException("Book is not available for borrow.");
        }

        Member member = memberService.getById(req.getMemberId());

        // create rental
        Rental rental = Rental.builder()
                .book(book)
                .member(member)
                .rentalDate(Instant.now())
                .status(Rental.Status.ACTIVE)
                .build();

        // change book status
        book.setStatus(Book.Status.BORROWED);

        // save entities
        Rental saved = rentalRepo.save(rental);
        // update book via bookService
        bookService.create(book); // using create/save to persist book status change

        return saved;
    }

    @Transactional
    public Rental returnBook(Long rentalId) {
        Rental rental = getById(rentalId);
        if (rental.getStatus() == Rental.Status.RETURNED) {
            throw new BadRequestException("Rental already returned.");
        }

        rental.setReturnDate(Instant.now());
        rental.setStatus(Rental.Status.RETURNED);

        // set book available again
        Book book = rental.getBook();
        book.setStatus(Book.Status.AVAILABLE);
        bookService.create(book);

        return rentalRepo.save(rental);
    }

    public void delete(Long id) {
        rentalRepo.deleteById(id);
    }

    public List<Rental> getByMember(Long memberId) {
        return rentalRepo.findByMemberId(memberId);
    }
}
