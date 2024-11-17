package com.spring.webflux.service;

import com.spring.webflux.exception.CustomException;
import com.spring.webflux.exception.DatabaseException;
import com.spring.webflux.exception.UserNotFoundException;
import com.spring.webflux.model.User;
import com.spring.webflux.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Mono<User> getUserById(String id) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new UserNotFoundException("User not found with id: " + id)));
    }

    public Mono<User> createUser(User user) {
        return userRepository.save(user)
                .onErrorResume(e -> {
                    System.out.println("Error creating user: " + e.getMessage());
                    return Mono.error(new DatabaseException("Error saving user to database"));
                });
    }

    public Mono<User> updateUser(String id, User user) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new UserNotFoundException("User not found with id: " + id)))
                .flatMap(existingUser -> {
                    existingUser.setName(user.getName());
                    existingUser.setEmail(user.getEmail());
                    return userRepository.save(existingUser);
                })
                .onErrorResume(e -> Mono.error(new CustomException("Error updating user: " + e.getMessage())));
    }

    public Mono<Boolean> deleteUser(String id) {
        return userRepository.findById(id)
                .flatMap(user -> userRepository.deleteById(id).then(Mono.just(true)).or(Mono.just(false)));
    }
}
