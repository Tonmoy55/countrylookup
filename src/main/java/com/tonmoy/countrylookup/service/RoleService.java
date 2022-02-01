package com.tonmoy.countrylookup.service;

import com.tonmoy.countrylookup.dao.RoleDao;
import com.tonmoy.countrylookup.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleDao roleDao;

    public Role createNewRole(Role role) {
        return roleDao.save(role);
    }
}
