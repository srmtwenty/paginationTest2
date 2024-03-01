package com.scott.controllers;

import com.scott.models.Role;
import com.scott.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@CrossOrigin(origins="http://localhost:3000")
public class RoleController {
    @Autowired
    RoleRepository roleRepository;

    @PostMapping("/create")
    public Role createRole(@RequestBody Role role){
        return roleRepository.save(role);
    }
    @GetMapping
    public List<Role> findAllRoles(){
        return roleRepository.findAll();
    }
    @GetMapping("/{id}")
    public Role findRoleById(@PathVariable Long id){
        return roleRepository.findById(id).orElseThrow(()->new RuntimeException());
    }
    @PutMapping("/{id}/update")
    public Role updateRole(@PathVariable Long id, @RequestBody Role role){
        Role updateR=this.findRoleById(id);
        updateR.setName(role.getName());
        updateR.setPersons(role.getPersons());
        return roleRepository.save(updateR);
    }
    @DeleteMapping("/{id}/delete")
    public void deleteRole(@PathVariable Long id){
        roleRepository.deleteById(id);
    }

    @GetMapping("/{offset}/{pageSize}/{field}")
    public Page<Role> findRolesPagination(@PathVariable int offset, @PathVariable int pageSize, @PathVariable String field){
        return roleRepository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(field)));
    }
}
