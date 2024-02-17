package com.hritvik.WhistlistManagementXindusAssignment.repository;

import com.hritvik.WhistlistManagementXindusAssignment.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {


    boolean existsByUserName(String username);

    Optional<Users> findByUserName(String username);
}

