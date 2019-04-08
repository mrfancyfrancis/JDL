package jdl.controller;
import jdl.model.User;
import jdl.view.*;
public class Runner 
{
	private static User user = null;
	private static Splash s;
	private static Login l;
	private static OptionList ol;
	private static ViewAdministratorAccount vaa;
	private static Tables t;
	private static AccountManagement am;
	private static TablesUpdateTransactions tut;
    public static void main(String[] args) 
    {
        openSplash();
    }
    public static void openSplash()
    {
    	s = new Splash();
    	s.loadProgressBar();
    }
    public static void destroySplash()
    {
        s.setVisible(false);
        s.dispose();    
    }
    public static void openLogin()
    {   
        l = new Login();
    }
    public static void destroyLogin()
    {
    	l.setVisible(false);
    	l.dispose();
    }
    public static void openOptionList()
    {
    	ol = new OptionList();
    }
    public static void destroyOptionList()
    {
    	ol.setVisible(false);
    	ol.dispose();
    }
    public static void openViewAdminAcc()
    {
    	vaa = new ViewAdministratorAccount();
    }
    public static void destroyViewAdminAcc()
    {
    	vaa.setVisible(false);
    	vaa.dispose();
    }
    public static void openTables()
    {
    	t = new Tables();
    }
    public static void destroyTables()
    {
    	t.setVisible(false);
    	t.dispose();
    }
    public static void openAccountManagement()
    {
    	am = new AccountManagement();
    }
    public static void destroyAccountManagement()
    {
    	am.setVisible(false);
    	am.dispose();
    }
    public static void openTUT()
    {
    	tut = new TablesUpdateTransactions();
    }
    public static void destroyTUT()
    {
    	tut.setVisible(false);
    	tut.dispose();
    }
	public static User getUser() {
		return user;
	}
	public static void setUser(User user) {
		Runner.user = user;
	}
    
}
