package com.scott.repositories;

import com.scott.models.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    //List<Role> findByOrderByNameAsc();
    //List<Role> findRolesByName(String name);
    //Page<Role> findAll(Pageable pageable);
}
