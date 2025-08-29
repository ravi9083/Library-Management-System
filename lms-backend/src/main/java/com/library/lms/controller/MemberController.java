package com.library.lms.controller;

import com.library.lms.entity.Member;
import com.library.lms.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@CrossOrigin(origins = "*")
public class MemberController {

    private final MemberService service;

    public MemberController(MemberService service) {
        this.service = service;
    }

    @GetMapping
    public List<Member> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Member get(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public Member create(@Valid @RequestBody Member member) {
        return service.create(member);
    }

    @PutMapping("/{id}")
    public Member update(@PathVariable Long id, @Valid @RequestBody Member member) {
        return service.update(id, member);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
