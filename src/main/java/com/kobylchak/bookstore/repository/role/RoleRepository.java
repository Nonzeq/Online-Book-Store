package com.kobylchak.bookstore.repository.role;

import com.kobylchak.bookstore.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
