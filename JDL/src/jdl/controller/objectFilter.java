package jdl.controller;

import java.util.ArrayList;

import jdl.dao.Queries;
import jdl.model.Client;
import jdl.model.User;

public class objectFilter 
{
	public static String[] getClientList()
	{
		ArrayList<Client> cl = Queries.getClients();
		ArrayList<String> clist = new ArrayList<String>();
		clist.add("Click to see the list of registered client");
		for(Client c: cl)
		{
			clist.add(c.getClient_lastname()+", "+c.getClient_firstname()+": "+c.getClient_id());
		}
		return clist.toArray(new String[clist.size()]);
	}
	public static boolean containsDigit(String s) {
	    boolean containsDigit = false;

	    if (s != null && !s.isEmpty()) {
	        for (char c : s.toCharArray()) {
	            if (containsDigit = Character.isDigit(c)) {
	                break;
	            }
	        }
	    }

	    return containsDigit;
	}
	public static boolean containsAlpha(String name) {
	    char[] chars = name.toCharArray();
	    for (char c : chars) {
	        if(Character.isLetter(c)) 
	        {
	            return true;
	        }
	    }
	    return false;
	}
	public static String[] getUsernames() {
	    ArrayList<User> ulist = new ArrayList<User>();
	    String[] ul = new String[ulist.size()];
	    for(int i = 0; i < ul.length;i++)
	    {
	    	ul[i] = ulist.get(i).getUser_username();
	    }
	    return ul;
	}
}
