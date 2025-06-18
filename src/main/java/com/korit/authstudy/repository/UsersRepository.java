package com.korit.authstudy.repository;

import com.korit.authstudy.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsersRepository extends JpaRepository<User, Integer> {

    List<User> findByUsername(String username);
}
