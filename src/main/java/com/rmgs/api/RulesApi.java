package com.rmgs.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rmgs.client.SonarClient;
import com.rmgs.dto.CodingRules;
import com.rmgs.dto.Details;
import com.rmgs.dto.Rule;


/**
 * 
 * @author rehab.sayed
 *
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/api/rules")
public class RulesApi {
	static final Logger logger = LogManager.getLogger(RulesApi.class);

	
	
	@Autowired
	private SonarClient sonarClient;
	
	@CrossOrigin
	@RequestMapping(value="/coding_rules" , method = RequestMethod.GET , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CodingRules> getProjects ()
	{
		CodingRules temp = sonarClient.getRules(1);
		int pagesSize=(int) Math.ceil((double)temp.getTotal()/500.0d);
		for(int i=2;i<=pagesSize;i++){
			temp.getRules().addAll(sonarClient.getRules(i).getRules());
		}
		return new ResponseEntity<CodingRules>(temp,HttpStatus.OK);
	}
	@CrossOrigin
	@RequestMapping(value="/coding_rules/{qProfile}/{activation}" , method = RequestMethod.GET , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CodingRules> getProjects (@PathVariable String qProfile,@PathVariable String activation)
	{
		CodingRules temp = sonarClient.getProfileRules(1, activation, qProfile);
		int pagesSize=(int) Math.ceil((double)temp.getTotal()/500.0d);
		for(int i=2;i<=pagesSize;i++){
			temp.getRules().addAll(sonarClient.getProfileRules(i, activation, qProfile).getRules());
		}
		return new ResponseEntity<CodingRules>(temp,HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(value="/{ruleKey}" , method = RequestMethod.GET , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Details> getRuleDetails (@PathVariable String ruleKey)
	{
		return sonarClient.getRuleDetails(ruleKey);
	}
	
	@CrossOrigin
	@RequestMapping(value="/" , method = RequestMethod.PUT, consumes =MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<Rule> editRule(@RequestBody Details rule)
	{
	
		//sonarClient.updateRule(rule);
		
		return new ResponseEntity<Rule>(sonarClient.updateRule(rule), HttpStatus.OK);
	}
	
	
}
