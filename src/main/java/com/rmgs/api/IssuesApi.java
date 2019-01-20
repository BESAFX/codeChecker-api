package com.rmgs.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rmgs.client.SonarClient;
import com.rmgs.dto.IssuesDto;

/**
 * 
 * @author rehab.sayed
 *
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/api/issues")
public class IssuesApi {
	static final Logger logger = LogManager.getLogger(IssuesApi.class);

	
	
	@Autowired
	private SonarClient sonarClient;
	
	@CrossOrigin
	@RequestMapping(value="/search" , method = RequestMethod.GET , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<IssuesDto> getProjects ()
	{
		return sonarClient.getIssues();
	}
	
	
}
