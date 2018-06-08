package com.kulomady.mystegano.repository;

import com.kulomady.mystegano.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, String> {

}
