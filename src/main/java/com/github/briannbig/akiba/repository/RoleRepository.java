package com.github.briannbig.akiba.repository;

import com.github.briannbig.akiba.entities.Role;
import com.github.briannbig.akiba.entities.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByRoleName(RoleName roleName);
}