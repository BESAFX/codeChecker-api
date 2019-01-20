package com.rmgs.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import com.rmgs.model.Role;
import com.rmgs.model.User;



/**
 * 
 * @author rehab.sayed
 *
 */
public interface UserDao extends JpaRepository<User, Long> {
	Long countByRoles(Role role);
	User findOneByUsername(String userName);
	

}
