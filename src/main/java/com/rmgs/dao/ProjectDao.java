package com.rmgs.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.rmgs.model.Project;

/**
 * 
 * @author rehab.sayed
 *
 */
public interface ProjectDao extends JpaRepository<Project, Long> {

	Project findOneByName(String name);
	Project findOneByProjKey(String key);
	List<Project> findAll();

    @SuppressWarnings("unchecked")
	Project save(Project project);

}
