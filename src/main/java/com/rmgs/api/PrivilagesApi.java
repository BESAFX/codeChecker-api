package com.rmgs.api;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rmgs.dao.PrivilegeDao;
import com.rmgs.dao.UserDao;
import com.rmgs.model.Privilege;

/**
 * 
 * @author rehab.sayed
 *
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/api/privilege")
public class PrivilagesApi {
	static final Logger logger = LogManager.getLogger(PrivilagesApi.class);

	@Autowired
	PrivilegeDao privDao;
	@Autowired
	UserDao userDao;

	@CrossOrigin
	@RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<Privilege>> getAllPrivilage() {
		try {
			ArrayList<Privilege> test = (ArrayList<Privilege>) privDao.findAll();
			return new ResponseEntity<ArrayList<Privilege>>(test, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

}
