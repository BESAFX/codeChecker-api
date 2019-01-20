package com.rmgs.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rmgs.model.UserDetails;

/**
 * 
 * @author rehab.sayed
 *
 */
public interface UserDetailsDao extends JpaRepository<UserDetails, Long> {
	
}
