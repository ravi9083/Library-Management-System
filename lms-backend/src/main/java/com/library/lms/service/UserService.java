package com.library.lms.service;

import com.library.lms.entity.User;
import com.library.lms.exception.NotFoundException;
import com.library.lms.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public List<User> getAll() {
        return repo.findAll();
    }

    public User getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
    }

    public User create(User user) {
        return repo.save(user);
    }

    public User update(Long id, User updated) {
        User existing = getById(id);
        existing.setUsername(updated.getUsername());
        existing.setPassword(updated.getPassword());
        existing.setRole(updated.getRole());
        return repo.save(existing);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
