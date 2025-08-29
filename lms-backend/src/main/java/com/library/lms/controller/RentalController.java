package com.library.lms.controller;

import com.library.lms.dto.BorrowRequest;
import com.library.lms.entity.Rental;
import com.library.lms.service.RentalService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rentals")
@CrossOrigin(origins = "*")
public class RentalController {

    private final RentalService service;

    public RentalController(RentalService service) {
        this.service = service;
    }

    @GetMapping
    public List<Rental> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Rental get(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping("/borrow")
    public Rental borrow(@Valid @RequestBody BorrowRequest req) {
        return service.borrow(req);
    }

    @PostMapping("/return/{rentalId}")
    public Rental returnBook(@PathVariable Long rentalId) {
        return service.returnBook(rentalId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
