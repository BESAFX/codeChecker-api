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

import com.rmgs.dao.RoleDao;
import com.rmgs.dao.UserDao;
import com.rmgs.exceptions.DuplicatedRecordException;
import com.rmgs.exceptions.RoleAssignedException;
import com.rmgs.exceptions.RoleNotFoundException;
import com.rmgs.model.Role;

/**
 * 
 * @author rehab.sayed
 *
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/api/role")
public class RolesApi {
	static final Logger logger = LogManager.getLogger(RolesApi.class);

	@Autowired RoleDao roleDao;
	@Autowired UserDao userDao;
	
	@CrossOrigin
	@RequestMapping(value="/" , method = RequestMethod.GET , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<Role>> getAllUsers ()
	{
		try{
			ArrayList<Role> test =  (ArrayList<Role>)roleDao.findAll();
			return new ResponseEntity<ArrayList<Role>>(test, HttpStatus.OK);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping(value="/{roleId}" , method = RequestMethod.DELETE )
	public ResponseEntity<String> deleteRole(@PathVariable long roleId)
	{
		
		if(!roleDao.exists(roleId))
		{
			logger.error("Role with id : "+ roleId +" Not Found !!");
			throw new RoleNotFoundException();
		}
		Role role=roleDao.getOne(roleId);
		
		try {
			Long num=userDao.countByRoles(role);
			if(num==null || num==0){
				roleDao.delete(role);
			}else{
				throw new RoleAssignedException();
			}
			
		} catch (Exception e) {
			
			logger.error(e);
			throw e;
		}
		 
		 
		return new ResponseEntity<String>("Deleted !! ", HttpStatus.OK);
	}
	
	
	
	@RequestMapping(value="/" , method = RequestMethod.PUT, consumes =MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<Role> editRole(@RequestBody Role role)
	{
		
		if(!roleDao.exists(role.getId()) )
		{
			logger.error("Role with id : "+ role.getId() + " Not Found !!");
			throw new RoleNotFoundException();
		}
		Role fetchedRole=roleDao.findOneByName(role.getName());
	
		if ( fetchedRole !=null && fetchedRole.getId().longValue()!=role.getId().longValue())
		{
			logger.error("can`t update Role : "+fetchedRole.getId() + " with this code : "+fetchedRole.getName() + " because this is already Role with the same Name !!");
			throw new DuplicatedRecordException();
		}
		
		roleDao.save(role);
		
	
		
		
		return new ResponseEntity<Role>(role, HttpStatus.OK);
	}
	
	
	@CrossOrigin
	@RequestMapping(value="/" , method = RequestMethod.POST, consumes =MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<Role> addRole(@RequestBody Role role)
	{
		Role fetchedRole=roleDao.findOneByName(role.getName());
		if(fetchedRole!=null )
		{
			logger.error("Role with name : "+ role.getName() + " Found !!");
			throw new DuplicatedRecordException();
		}	
		role.setId(null);
		role=roleDao.save(role);
		
		return new ResponseEntity<Role>(role, HttpStatus.OK);
	}
}
