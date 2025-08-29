package com.library.lms.service;

import com.library.lms.entity.Member;
import com.library.lms.exception.NotFoundException;
import com.library.lms.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    private final MemberRepository repo;

    public MemberService(MemberRepository repo) {
        this.repo = repo;
    }

    public List<Member> getAll() {
        return repo.findAll();
    }

    public Member getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Member not found with id: " + id));
    }

    public Member create(Member member) {
        return repo.save(member);
    }

    public Member update(Long id, Member updated) {
        Member existing = getById(id);
        existing.setName(updated.getName());
        existing.setEmail(updated.getEmail());
        return repo.save(existing);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
