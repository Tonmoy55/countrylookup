package com.tonmoy.countrylookup.service;

import com.tonmoy.countrylookup.dao.RoleDao;
import com.tonmoy.countrylookup.dao.UserDao;
import com.tonmoy.countrylookup.entity.Role;
import com.tonmoy.countrylookup.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerNewUser(User user) {

        Role role = roleDao.findById("User").get();
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRole(roles);
        user.setUserPassword(getEncodedPassword(user.getUserPassword()));
        return userDao.save(user);
    }

    public void initializeRoleAndUser() {
        Role adminRole = new Role();
        adminRole.setRoleName("Admin");
        adminRole.setRoleDescription("Admin role for the System");
        roleDao.save(adminRole);

        Role userRole = new Role();
        userRole.setRoleName("User");
        userRole.setRoleDescription("User role for the System");
        roleDao.save(userRole);

        User adminUser = new User();
        adminUser.setUserName("admin123");
        adminUser.setUserFirstName("admin");
        adminUser.setUserLastName("user");
        adminUser.setUserPassword(getEncodedPassword("password"));
        Set<Role> adminRolesSet = new HashSet<>();
        adminRolesSet.add(adminRole);
        adminUser.setRole(adminRolesSet);
        userDao.save(adminUser);

        /*User user = new User();
        user.setUserName("tonmoy123");
        user.setUserFirstName("Tonmoy");
        user.setUserLastName("Sikder");
        user.setUserPassword(getEncodedPassword("password"));
        Set<Role> userRolesSet = new HashSet<>();
        userRolesSet.add(userRole);
        user.setRole(userRolesSet);
        userDao.save(user);*/
    }

    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
