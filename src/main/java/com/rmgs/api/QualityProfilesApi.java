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
import com.rmgs.dto.Profile;
import com.rmgs.dto.ProfileCopy;
import com.rmgs.dto.QualityProfiles;

/**
 * 
 * @author rehab.sayed
 *
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/api/profile")
public class QualityProfilesApi {
	static final Logger logger = LogManager.getLogger(QualityProfilesApi.class);

	@Autowired
	private SonarClient sonarClient;

	@CrossOrigin
	@RequestMapping(value = "/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<QualityProfiles> getQualityProfiles() {

		return new ResponseEntity<QualityProfiles>(sonarClient.getQualityProfiles(), HttpStatus.OK);
	}

	@RequestMapping(value = "/{profileKey}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteUser(@PathVariable String profileKey) {

		sonarClient.deleteProfile(profileKey);

		return new ResponseEntity<String>("Deleted !! ", HttpStatus.OK);
	}

	@CrossOrigin
	@RequestMapping(value = "/copy", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Profile> copyProfile(@RequestBody ProfileCopy profileCopy) {
		return new ResponseEntity<Profile>(sonarClient.copyProfile(profileCopy), HttpStatus.OK);
	}

	@CrossOrigin
	@RequestMapping(value = "/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Profile> updateProfile(@RequestBody Profile profile) {
		sonarClient.updateProfileName(profile);
		sonarClient.activateProfileRules(profile);
		sonarClient.deactivateProfileRules(profile);
		return new ResponseEntity<Profile>(profile, HttpStatus.OK);
	}

	@CrossOrigin
	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Profile> createProfile(@RequestBody Profile profile) {
		return new ResponseEntity<Profile>(sonarClient.createProfile(profile), HttpStatus.OK);
	}
}
