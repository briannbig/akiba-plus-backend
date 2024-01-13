package com.github.briannbig.akiba.repository;

import com.github.briannbig.akiba.entities.User;
import com.github.briannbig.akiba.entities.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByNationalID(String nationalID);

    Optional<User> findByTelephone(String telephone);

    List<User> findByRoles_RoleName(RoleName roleName);
}