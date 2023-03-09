package io.backspace.milkman.repository;

import io.backspace.milkman.model.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<Users, Integer> {
    List<Users> findByEmail(String email);
}
