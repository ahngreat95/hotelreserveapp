package com.hotel.hotel.domain.repository;

import com.hotel.hotel.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
