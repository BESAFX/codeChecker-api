package com.rmgs.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import com.rmgs.model.Role;



/**
 * 
 * @author rehab.sayed
 *
 */
public interface RoleDao extends JpaRepository<Role, Long> {
	Role findOneByName(String name);
	

}
