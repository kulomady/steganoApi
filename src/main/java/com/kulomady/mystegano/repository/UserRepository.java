package com.kulomady.mystegano.repository;

import com.kulomady.mystegano.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface UserRepository extends JpaRepository<User, String> {

    @Query("select u from User u where u.username = ?1 and u.password = ?2")
    User findUser(String username, String password);
}
