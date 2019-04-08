package jdl.controller;

import jdl.dao.Queries;
import jdl.model.User;

public class loginFunction 
{
	public static User attemptLogin(String username, String password)
	{
		return Queries.loginDAO(username, password);
	}
}
