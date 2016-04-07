/**
 * 
 */
package com.neu.msd.service;

import java.sql.SQLException;
import java.util.List;

import com.neu.msd.entities.AdminActivityAnswer;
import com.neu.msd.entities.Topic;
import com.neu.msd.entities.User;
import com.neu.msd.entities.User;
import com.neu.msd.exception.AdminException;
import com.neu.msd.exception.UserException;

/**
 * @author HP
 *
 */
public interface UserService {

	public List<AdminActivityAnswer> getDiagnosticQuestions() throws UserException, AdminException;

	public List<Topic> getTopicsOfUser(User user)throws UserException;


	void addscore(User user, double score) throws UserException;

	Integer[] getweigh() throws SQLException;

}
