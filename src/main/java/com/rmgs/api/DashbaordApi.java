package com.rmgs.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rmgs.client.SonarClient;
import com.rmgs.dto.DashbaordDto;
import com.rmgs.dto.Measures;


/**
 * 
 * @author rehab.sayed
 *
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/api/dashbaord")
public class DashbaordApi {
	static final Logger logger = LogManager.getLogger(DashbaordApi.class);

	
	
	@Autowired
	private SonarClient sonarClient;
	
	@CrossOrigin
	@RequestMapping(value="/search_projects/{favourite}" , method = RequestMethod.GET , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DashbaordDto> getProjects (@PathVariable long favourite)
	{
		return sonarClient.getProjects(favourite);
	}
	
	
	@CrossOrigin
	@RequestMapping(value="/measures/search" , method = RequestMethod.GET , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Measures> getMeasures (@RequestParam String projectKeys)
	{
		return sonarClient.getMeasures(projectKeys);
	}
	
	
}
