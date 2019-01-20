package com.rmgs.api;

import java.util.ArrayList;

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

import com.rmgs.dao.UserDao;
import com.rmgs.dao.UserDetailsDao;
import com.rmgs.exceptions.DuplicatedRecordException;
import com.rmgs.exceptions.UserNotFoundException;
import com.rmgs.model.User;

/**
 * 
 * @author rehab.sayed
 *
 */
@RestController
@RequestMapping(value = "/api/user")
public class UserApi {
	
	final static Logger logger = LogManager.getLogger(UserApi.class);
	@Autowired UserDao userDao;
	@Autowired UserDetailsDao userDetailsDao;
	
	@CrossOrigin
	@RequestMapping(value="/" , method = RequestMethod.GET , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<User>> getAllUsers ()
	{
		ArrayList<User> test=(ArrayList<User>) userDao.findAll();
		return new ResponseEntity<ArrayList<User>>(test, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{userId}" , method = RequestMethod.DELETE )
	public ResponseEntity<String> deleteUser(@PathVariable long userId)
	{
		
		if(!userDao.exists(userId))
		{
			logger.error("User with id : "+ userId +" Not Found !!");
			throw new UserNotFoundException();
		}
		User user=userDao.getOne(userId);
		
		try {
			userDao.delete(user);
			
		} catch (Exception e) {
			
			logger.error(e);
			throw e;
		}
		 
		 
		return new ResponseEntity<String>("Deleted !! ", HttpStatus.OK);
	}
	
	
	
	@RequestMapping(value="/" , method = RequestMethod.PUT, consumes =MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<User> editUser(@RequestBody User user)
	{
		
		if(!userDao.exists(user.getId()) )
		{
			logger.error("User with id : "+ user.getId() + " Not Found !!");
			throw new UserNotFoundException();
		}
		User fetchedUser=userDao.findOneByUsername(user.getUsername());
	
		if ( fetchedUser !=null && fetchedUser.getId().longValue()!=user.getId().longValue())
		{
			logger.error("can`t update User : "+fetchedUser.getId() + " with this User name : "+fetchedUser.getUsername() + " because this is already User with the same user name !!");
			throw new DuplicatedRecordException();
		}
		
		userDao.save(user);
		
	
		
		
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	
	@CrossOrigin
	@RequestMapping(value="/" , method = RequestMethod.POST, consumes =MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<User> addUser(@RequestBody User user)
	{
		User fetchedRole=userDao.findOneByUsername(user.getUsername());
		if(fetchedRole!=null )
		{
			logger.error("User with name : "+ user.getUsername() + " Found !!");
			throw new DuplicatedRecordException();
		}	
		user.setId(null);
		user=userDao.save(user);
		
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
		
}
