package ru.cereevse.kyrs.repository;

import org.springframework.data.repository.CrudRepository;
import ru.cereevse.kyrs.model.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
