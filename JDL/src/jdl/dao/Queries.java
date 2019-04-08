package jdl.dao;

import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.TableModel;

import jdl.model.Client;
import jdl.model.Transaction;
import jdl.model.User;
import net.proteanit.sql.DbUtils;

public class Queries 
{
	static databaseProperties dP = new databaseProperties();
	public static User loginDAO(String username, String password)
	{
		User user = null;
		try (Connection con = DriverManager.getConnection(dP.url, dP.username, dP.password)) 
		{	
			PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE user_username = ? AND user_password = ?");
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				user = new User();
				user.setUser_id(rs.getInt("user_id"));
				user.setUser_username(rs.getString("user_username"));
				user.setUser_password(rs.getString("user_password"));
				user.setUser_ifAdmin(rs.getInt("user_ifAdmin"));
			}
			con.close();
		} catch (SQLException e) 
		{
			e.printStackTrace();
			return null;
		}
		return user;		
	}
	public static ArrayList<Client> getClients()
	{
		ArrayList<Client> lists = new ArrayList<Client>();
		try (Connection con = DriverManager.getConnection(dP.url, dP.username, dP.password)) 
		{
			PreparedStatement ps = con.prepareStatement("SELECT * FROM jdl_accounts.clients");
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{        
				Client c = new Client();
				c.setClient_id(rs.getInt(1));
				c.setClient_lastname(rs.getString(2));
				c.setClient_firstname(rs.getString(3));
				c.setClient_nationality(rs.getString(4));
				c.setClient_birthdate(rs.getDate(5));
				c.setClient_gender(rs.getString(6));
				c.setClient_company(rs.getString(7));
				c.setClient_position(rs.getString(8));
				c.setClient_alias(rs.getString(9));
				c.setClient_contact(rs.getString(10));
				c.setClient_email(rs.getString(11));
				lists.add(c);
			}
		} catch (SQLException e) 
		{
			e.printStackTrace();
			if(lists.isEmpty())
				return null;
		}
		return lists;
	}
	public static TableModel getTransactions(int id)
	{
		ResultSet rs = null;
		try (Connection con = DriverManager.getConnection(dP.url, dP.username, dP.password)) 
		{
			PreparedStatement ps = con.prepareStatement("SELECT client_id AS 'Client ID',"
					+ "trans_transId AS 'Transaction ID'" +
					",trans_passportNo AS 'Passport No' "+ 
					", trans_tinID AS 'TIN ID' " + 
					", trans_visaType AS 'Visa Type' " + 
					", trans_visaStartDate AS 'Visa Start Date' " + 
					", trans_visaEndDate AS 'Visa Expiry Date' " + 
					", trans_permitType AS 'Permit Type' " + 
					", trans_permitStartDate AS 'Permit Start Date' " + 
					", trans_permitEndDate AS 'Permit Expiry Date' " + 
					", trans_aepID AS 'AEP ID' " + 
					", trans_aepStartDate AS 'AEP Start Date' " + 
					", trans_aepEndDate AS 'AEP Expiry Date' " + 
					" FROM transactions WHERE client_id = ? ORDER BY trans_transId DESC");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			return DbUtils.resultSetToTableModel(rs);
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	public static boolean insertTransaction(Transaction t)
	{
		try (Connection con = DriverManager.getConnection(dP.url, dP.username, dP.password)) 
		{
			PreparedStatement ps = con.prepareStatement("INSERT INTO jdl_accounts.transactions (trans_passportNo, trans_tinID, trans_visaType, trans_visaStartDate, trans_visaEndDate, trans_permitType, trans_permitStartDate, trans_permitEndDate, trans_aepID, "
					+ "trans_aepStartDate, trans_aepEndDate, client_id, trans_transTimestamp, trans_transAuthor) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			ps.setString(1, t.getPassportNo());
			ps.setString(2, t.getTinID());
			ps.setString(3, t.getVisaType());
			ps.setDate(4, t.getVisaStartDate());
			ps.setDate(5, t.getVisaEndDate());
			ps.setString(6, t.getPermitType());
			ps.setDate(7, t.getPermitStartDate());
			ps.setDate(8, t.getPermitEndDate());
			ps.setString(9, t.getAepID());
			ps.setDate(10, t.getAepStartDate());
			ps.setDate(11, t.getAepEndDate());
			ps.setInt(12, t.getClient_id());
			ps.setDate(13, t.getTransTimestamp());
			ps.setString(14, t.getTransAuthor());
			ps.executeUpdate();
		} catch (SQLException e) 
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public static ArrayList<User> getUsers()
	{
		ArrayList<User> lists = new ArrayList<User>();
		try (Connection con = DriverManager.getConnection(dP.url, dP.username, dP.password)) 
		{
			PreparedStatement ps = con.prepareStatement("SELECT * FROM jdl_accounts.clients");
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{        
				User u = new User();
				u.setUser_id(rs.getInt("user_id"));
				u.setUser_username(rs.getString("user_username"));
				u.setUser_password(rs.getString("password"));
				u.setUser_ifAdmin(rs.getInt("user_ifAdmin"));
				lists.add(u);
			}
		} catch (SQLException e) 
		{
			e.printStackTrace();
			if(lists.isEmpty())
				return null;
		}
		return lists;
	}
}
