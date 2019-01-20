package com.rmgs.api;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rmgs.client.SonarClient;
import com.rmgs.dao.ProjectDao;
import com.rmgs.dto.Component;
import com.rmgs.dto.DashbaordDto;
import com.rmgs.dto.Measures;
import com.rmgs.exceptions.DuplicatedRecordException;
import com.rmgs.exceptions.ProjectNotFoundException;
import com.rmgs.exceptions.UserNotFoundException;
import com.rmgs.model.Project;
import com.rmgs.model.User;


/**
 * 
 * @author rehab.sayed
 *
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/api/project")
public class ProjectApi {
	static final Logger logger = LogManager.getLogger(ProjectApi.class);

	
	
	@Autowired
	private SonarClient sonarClient;
	
	
	@Autowired
	private ProjectDao projectDao;
	
	
	@GetMapping(value="/getAll")
	public ResponseEntity<List<Project>> getProjects()
	{
		List <Project> fetchedProjects= projectDao.findAll();
		
	
		if (fetchedProjects==null)
		{
			logger.error("Project not Found");
			throw new ProjectNotFoundException();
		}
		return new ResponseEntity<List<Project>>(fetchedProjects, HttpStatus.OK);
	}
	
	
	@GetMapping(value="/getProject/{projectKey}"  )
	public ResponseEntity<Project> getProject(@PathVariable String projectKey)
	{
		Project fetchedProject= projectDao.findOneByProjKey(projectKey);
		
	
		if (fetchedProject==null)
		{
			logger.error("Project not Found");
			return null;
		}
		return new ResponseEntity<Project>(fetchedProject, HttpStatus.OK);
	}
	
	@RequestMapping(value="/" , method = RequestMethod.PUT, consumes =MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<Project> editProject(@RequestBody Project project)
	{
		Project fetchedProject= projectDao.findOneByName(project.getName());
		
	
		if (fetchedProject==null)
		{
			logger.error("Project not Found");
			throw new ProjectNotFoundException();
		}
		fetchedProject.setPassword(project.getPassword());
		fetchedProject.setPath(project.getPath());
		fetchedProject.setUsername(project.getUsername());		
		projectDao.save(fetchedProject);
		return new ResponseEntity<Project>(project, HttpStatus.OK);
	}
	
	
	
	@CrossOrigin
	@RequestMapping(value="/" , method = RequestMethod.POST, consumes =MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<Project> addProject(@RequestBody Project project)
	{
		Project fetchedProject= projectDao.findOneByName(project.getName());
		
		
		if (fetchedProject!=null)
		{
			logger.error("Project name exist");
			throw new DuplicatedRecordException();
		}	
		
		fetchedProject= projectDao.findOneByProjKey(project.getName());
		
		
		if (fetchedProject!=null)
		{
			logger.error("Project Key exist");
			throw new DuplicatedRecordException();
		}	
		sonarClient.createProject(project);
		project.setId(null);
		projectDao.save(project);
		return new ResponseEntity<Project>(project, HttpStatus.OK);
	}
		
}
