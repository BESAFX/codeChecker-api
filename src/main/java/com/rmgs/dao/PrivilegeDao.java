package com.rmgs.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import com.rmgs.model.Privilege;


/**
 * 
 * @author rehab.sayed
 *
 */
public interface PrivilegeDao extends JpaRepository<Privilege, Long> {
	

}
