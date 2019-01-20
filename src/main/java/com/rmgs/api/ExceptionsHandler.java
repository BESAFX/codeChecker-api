package com.rmgs.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.rmgs.exceptions.DuplicatedRecordException;
import com.rmgs.exceptions.RoleAssignedException;
import com.rmgs.exceptions.RoleNotFoundException;
import com.rmgs.exceptions.UserNotFoundException;

/**
 * 
 * @author rehab.sayed
 *
 */
@ControllerAdvice
public class ExceptionsHandler {
	final static Logger logger = LogManager.getLogger(ExceptionsHandler.class);
	
	@ResponseStatus(value=HttpStatus.NOT_FOUND , reason="Role Not Found !!")  // 404
    @ExceptionHandler(RoleNotFoundException.class)
    public void handleRoleNotFoundException(Exception ex) {
	  logger.error("RoleNotFoundException with code:404");
	  logger.error(ex);
	  ex.printStackTrace();
    }
	
	@ResponseStatus(value=HttpStatus.METHOD_NOT_ALLOWED , reason="METHOD_NOT_ALLOWED !!")  // 405
    @ExceptionHandler(RoleAssignedException.class)
    public void handleRoleAssignedException(Exception ex) {
	  logger.error("RoleAssignedException with code:404");
	  logger.error(ex);
	  ex.printStackTrace();
    }

	@ResponseStatus(value=HttpStatus.BAD_REQUEST , reason="unique constraint violated !!")  // 400
    @ExceptionHandler(DuplicatedRecordException.class)
    public void handleDuplicatedRecordException(Exception ex) {
		logger.error("DuplicatedRecordException with code:400");
		logger.error(ex);
		ex.printStackTrace();
	}
	
	@ResponseStatus(value=HttpStatus.NOT_FOUND , reason="User Not Found !!")  // 404
    @ExceptionHandler(UserNotFoundException.class)
    public void handleUserNotFoundException(Exception ex) {
	  logger.error("UserNotFoundException with code:404");
	  logger.error(ex);
	  ex.printStackTrace();
    }
}
