package com.tonmoy.countrylookup.dao;

import com.tonmoy.countrylookup.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends CrudRepository<User, String> {
}
