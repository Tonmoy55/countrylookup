package com.tonmoy.countrylookup.dao;

import com.tonmoy.countrylookup.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends CrudRepository<Role, String> {
}
