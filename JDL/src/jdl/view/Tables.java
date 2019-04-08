package jdl.view;

import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import jdl.controller.AutoCompletion;
import jdl.controller.DateLabelFormatter;
import jdl.controller.Runner;
import jdl.controller.TableColumnAdjuster;
import jdl.controller.objectFilter;
import jdl.dao.Queries;
import jdl.model.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import java.util.Properties;

import net.proteanit.sql.DbUtils;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.JComboBox;

public class Tables extends JFrame{
	private JTextField tables_passportNoTxt;
	private JTextField tables_tinIdTxt;
	private JTextField tables_visaTypeTxt;
	private JTextField tables_permitTypeTxt;
	private JTextField tables_aepIdTxt;
	private String clientSelectedName;
	private JTable table_1;
	private int client_id = 1;

	public static boolean DateCheck(String date1, String date2) {
	 	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		boolean approved = false;
		if((date1.isEmpty()) && (date1.isEmpty())) {
			return approved = true;
		}
		else if(!date1.isEmpty() && date2.isEmpty()) {
			JOptionPane.showMessageDialog(null, "<html><font color = #ffffff>End date must not be empty</font color = #ffffff></html>", "Detected an error in date fields", JOptionPane.ERROR_MESSAGE);
			return approved = false;
		}
		else if(date1.isEmpty() && !date2.isEmpty()) {
			JOptionPane.showMessageDialog(null, "<html><font color = #ffffff>Start date must not be empty</font color = #ffffff></html>", "Detected an error in date fields", JOptionPane.ERROR_MESSAGE);
			return approved = false;
		}
		else if (!date1.isEmpty() && !date2.isEmpty()){
			try {
				Date datex = sdf.parse(date1);
				Date datey = sdf.parse(date2);
				if (datex.compareTo(datey) > 0) {
					//System.out.println("Date1 is after Date2"); FALSE
					JOptionPane.showMessageDialog(null, "<html><font color = #ffffff>Start date must be before expiry date</font color = #ffffff></html>", "Detected an error in date fields", JOptionPane.ERROR_MESSAGE);
					approved = false;
				} else if (datex.compareTo(datey) < 0) {
					//System.out.println("Date1 is before Date2");TRUE
					approved = true;
				} else if (datex.compareTo(datey) == 0) {
					//System.out.println("Date1 is equal to Date2"); FALSE
					JOptionPane.showMessageDialog(null, "<html><font color = #ffffff>Start date cannot be equal to expiry date</font color = #ffffff></html>", "Detected an error in date fields", JOptionPane.ERROR_MESSAGE);
					approved = false;
				}
				
			}
			catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		return approved;
	}

	/**
	 * Create the application.
	 */
	public Tables() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Tables.class.getResource("/jdl/Assets/login_small.png")));	
		
		//Main Panel
	
		setTitle("JDL: Transactions");
		setResizable(false);
		setUndecorated(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setMinimumSize(new Dimension(1550, 870));
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		getContentPane().setBackground(new Color(90, 103, 115));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setSize(1040, 299);
		scrollPane_1.setLocation(493, 198);
		
		table_1 = new JTable();
		table_1.setFont(new Font("Calibri", Font.PLAIN, 16));
		table_1.setRowHeight(32);
		table_1.setBorder(null);
		table_1.setBounds(492, 217, 1040, 138);

		
		JTableHeader header_1 = table_1.getTableHeader();
		header_1.setFont(new Font("Segoe UI Semibold", Font.BOLD, 13));
	    header_1.setBackground(new Color(155, 177, 166));
	    header_1.setForeground(Color.WHITE);
		scrollPane_1.setViewportView(table_1);
		
		
		UIDefaults defaults = UIManager.getLookAndFeelDefaults();
		if (defaults.get("Table.alternateRowColor") == null)
		    defaults.put("Table.alternateRowColor", new Color(155, 177, 166));
		
		//Input Section (Declaration of Panel) and Client_id Textfield
		
		JPanel tables_inputPanel = new JPanel();
		tables_inputPanel.setBounds(25, 152, 450, 678);
		tables_inputPanel.setBackground(new Color(255,255,255,60));
		tables_inputPanel.setLayout(null);
		
		//Buttons
		
		JComboBox tables_comboBox = new JComboBox(objectFilter.getClientList());
		
		tables_comboBox.setFont(new Font("Microsoft New Tai Lue", Font.BOLD, 14));
		tables_comboBox.setBounds(20, 79, 400, 29);
		AutoCompletion.enable(tables_comboBox);
		tables_inputPanel.add(tables_comboBox);
		
		tables_tinIdTxt = new JTextField();
		tables_tinIdTxt.setBorder(new EmptyBorder(0, 0, 0, 0));
		tables_tinIdTxt.setFont(new Font("Microsoft New Tai Lue", Font.BOLD, 15));
		tables_tinIdTxt.setColumns(10);
		tables_tinIdTxt.setBounds(20, 236, 400, 23);
		tables_inputPanel.add(tables_tinIdTxt);
		
		JButton tables_reloadBtn = new JButton("Reload");
		tables_reloadBtn.setBounds(1393, 148, 138, 38);
		tables_reloadBtn.setForeground(new Color(255, 255, 255));
		tables_reloadBtn.setIcon(new ImageIcon(Tables.class.getResource("/jdl/Assets/main_refresh.png")));
		tables_reloadBtn.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String d = null;
				if(client_id != 0)
					d = objectFilter.getClientList()[client_id].split(":")[1].trim();
				else
					d = objectFilter.getClientList()[1].split(":")[1].trim();
				TableModel md = Queries.getTransactions(Integer.parseInt(d));
				table_1.setModel(md);
				table_1.setFont(new Font("Calibri", Font.PLAIN, 16));
				table_1.setRowHeight(32);
				table_1.setBorder(null);
				table_1.setBounds(492, 217, 1040, 138);
				table_1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				table_1.setVisible(true);
				TableColumnAdjuster tca1 = new TableColumnAdjuster(table_1);
				tca1.adjustColumns();
				if(md != null)
				{
					if(md.getRowCount() > 0 && tables_comboBox.getSelectedIndex() != 0)
					{
						tables_tinIdTxt.setText(md.getValueAt(0, 3).toString());
						tables_tinIdTxt.setEditable(false);
						//System.out.println(md.getValueAt(0, 3));		
					}
					else
						tables_tinIdTxt.setEditable(true);
				}
				else
					tables_tinIdTxt.setEditable(true);
			}
		});
		
		tables_reloadBtn.doClick();		
		tables_reloadBtn.setBackground(new Color(0, 102, 102));
		tables_reloadBtn.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		tables_reloadBtn.setBorder(null);
		tables_reloadBtn.setBorder(null);
		
		//Input Section (Labels and Associated Textfields)
		
		JLabel tables_inputSectionLbl = new JLabel("Input Section");
		tables_inputSectionLbl.setBounds(25, 111, 255, 41);
		tables_inputSectionLbl.setForeground(new Color(255, 255, 255));
		tables_inputSectionLbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
		
		
		JLabel tables_passportNoLbl = new JLabel("Passport Number:");
		tables_passportNoLbl.setForeground(new Color(255, 255, 255));
		tables_passportNoLbl.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 13));
		tables_passportNoLbl.setBounds(20, 143, 204, 41);
		tables_inputPanel.add(tables_passportNoLbl);
		
		tables_passportNoTxt = new JTextField();
		tables_passportNoTxt.setBorder(new EmptyBorder(0, 0, 0, 0));
		tables_passportNoTxt.setFont(new Font("Microsoft New Tai Lue", Font.BOLD, 15));
		tables_passportNoTxt.setBounds(20, 180, 400, 23);
		tables_inputPanel.add(tables_passportNoTxt);
		tables_passportNoTxt.setColumns(10);
		
		JLabel tables_tinIdLbl = new JLabel("TIN ID:");
		tables_tinIdLbl.setForeground(new Color(255, 255, 255));
		tables_tinIdLbl.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 13));
		tables_tinIdLbl.setBounds(20, 207, 197, 29);
		tables_inputPanel.add(tables_tinIdLbl);
		
		JLabel tables_visaLbl = new JLabel("Visa Type:");
		tables_visaLbl.setForeground(new Color(255, 255, 255));
		tables_visaLbl.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 13));
		tables_visaLbl.setBounds(20, 263, 190, 29);
		tables_inputPanel.add(tables_visaLbl);
		
		tables_visaTypeTxt = new JTextField();
		tables_visaTypeTxt.setBorder(new EmptyBorder(0, 0, 0, 0));
		tables_visaTypeTxt.setFont(new Font("Microsoft New Tai Lue", Font.BOLD, 15));
		tables_visaTypeTxt.setColumns(10);
		tables_visaTypeTxt.setBounds(20, 294, 400, 23);
		tables_inputPanel.add(tables_visaTypeTxt);
		
		JLabel tables_visaStartLbl = new JLabel("Visa Start Date:");
		tables_visaStartLbl.setForeground(new Color(255, 255, 255));
		tables_visaStartLbl.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 13));
		tables_visaStartLbl.setBounds(20, 323, 190, 29);
		tables_inputPanel.add(tables_visaStartLbl);
		
		//Date Input
		
		// Start Dates
	
		//VISA
		UtilDateModel visaModel = new UtilDateModel();
		Properties visa = new Properties();
		visa.put("text.today", "Date Today");
		visa.put("text.month", "Month");
		visa.put("text.year", "Year");
		
		JDatePanelImpl visaDatePanel = new JDatePanelImpl(visaModel, visa);

		JDatePickerImpl visaStartPick = new JDatePickerImpl(visaDatePanel, new DateLabelFormatter());

		visaStartPick.setLocation(230, 354);
		visaStartPick.getJFormattedTextField().setBorder(UIManager.getBorder("TextField.border"));
		visaStartPick.getJFormattedTextField().setBackground(new Color(255, 255, 255));
		visaStartPick.getJFormattedTextField().setForeground(new Color(220, 20, 60));
		visaStartPick.getJFormattedTextField().setFont(new Font("Microsoft New Tai Lue", Font.BOLD, 15));
		visaStartPick.setSize(190, 23);

		tables_inputPanel.add(visaStartPick);
		
		//PERMIT
		UtilDateModel permitModel = new UtilDateModel();
		Properties permit = new Properties();
		permit.put("text.today", "Date Today");
		permit.put("text.month", "Month");
		permit.put("text.year", "Year");
		
		JDatePanelImpl permitDatePanel = new JDatePanelImpl(permitModel, visa);

		JDatePickerImpl permitStartPick = new JDatePickerImpl(permitDatePanel, new DateLabelFormatter());

		permitStartPick.setLocation(20, 463);
		permitStartPick.getJFormattedTextField().setBorder(UIManager.getBorder("TextField.border"));
		permitStartPick.getJFormattedTextField().setBackground(new Color(255, 255, 255));
		permitStartPick.getJFormattedTextField().setForeground(new Color(220, 20, 60));
		permitStartPick.getJFormattedTextField().setFont(new Font("Microsoft New Tai Lue", Font.BOLD, 15));
		permitStartPick.setSize(190, 23);

		tables_inputPanel.add(permitStartPick);
		
		//AEP
		UtilDateModel aepModel = new UtilDateModel();
		Properties aep = new Properties();
		aep.put("text.today", "Date Today");
		aep.put("text.month", "Month");
		aep.put("text.year", "Year");
		
		JDatePanelImpl aepDatePanel = new JDatePanelImpl(aepModel, visa);

		JDatePickerImpl aepStartPick = new JDatePickerImpl(aepDatePanel, new DateLabelFormatter());

		aepStartPick.setLocation(20, 572);
		aepStartPick.getJFormattedTextField().setBorder(UIManager.getBorder("TextField.border"));
		aepStartPick.getJFormattedTextField().setBackground(new Color(255, 255, 255));
		aepStartPick.getJFormattedTextField().setForeground(new Color(220, 20, 60));
		aepStartPick.getJFormattedTextField().setFont(new Font("Microsoft New Tai Lue", Font.BOLD, 15));
		aepStartPick.setSize(190, 23);
		
		tables_inputPanel.add(aepStartPick);
		
		// End Dates
		
		//VISA
		UtilDateModel visaModel1 = new UtilDateModel();
		Properties visa1 = new Properties();
		visa1.put("text.today", "Date Today");
		visa1.put("text.month", "Month");
		visa1.put("text.year", "Year");

		JDatePanelImpl visaDatePanel1 = new JDatePanelImpl(visaModel1, visa1);

		JDatePickerImpl visaEndPick = new JDatePickerImpl(visaDatePanel1, new DateLabelFormatter());

		visaEndPick.setLocation(20, 354);
		visaEndPick.getJFormattedTextField().setBorder(UIManager.getBorder("TextField.border"));
		visaEndPick.getJFormattedTextField().setBackground(new Color(255, 255, 255));
		visaEndPick.getJFormattedTextField().setForeground(new Color(220, 20, 60));
		visaEndPick.getJFormattedTextField().setFont(new Font("Microsoft New Tai Lue", Font.BOLD, 15));
		visaEndPick.setSize(190, 23);
		
		tables_inputPanel.add(visaEndPick);
		
		//PERMIT
		UtilDateModel permitModel1 = new UtilDateModel();
		Properties permit1 = new Properties();
		permit1.put("text.today", "Date Today");
		permit1.put("text.month", "Month");
		permit1.put("text.year", "Year");
		
		JDatePanelImpl permitDatePanel1 = new JDatePanelImpl(permitModel1, permit1);

		JDatePickerImpl permitEndPick = new JDatePickerImpl(permitDatePanel1, new DateLabelFormatter());

		permitEndPick.setLocation(230, 463);
		permitEndPick.getJFormattedTextField().setBorder(UIManager.getBorder("TextField.border"));
		permitEndPick.getJFormattedTextField().setBackground(new Color(255, 255, 255));
		permitEndPick.getJFormattedTextField().setForeground(new Color(220, 20, 60));
		permitEndPick.getJFormattedTextField().setFont(new Font("Microsoft New Tai Lue", Font.BOLD, 15));
		permitEndPick.setSize(192, 23);
		
		tables_inputPanel.add(permitEndPick);
		
		//AEP
		UtilDateModel aepModel1 = new UtilDateModel();
		Properties aep1 = new Properties();
		aep1.put("text.today", "Date Today");
		aep1.put("text.month", "Month");
		aep1.put("text.year", "Year");
		
		JDatePanelImpl aepDatePanel1 = new JDatePanelImpl(aepModel1, aep1);

		JDatePickerImpl aepEndPick = new JDatePickerImpl(aepDatePanel1, new DateLabelFormatter());

		aepEndPick.setLocation(228, 572);
		aepEndPick.getJFormattedTextField().setBorder(UIManager.getBorder("TextField.border"));
		aepEndPick.getJFormattedTextField().setBackground(new Color(255, 255, 255));
		aepEndPick.getJFormattedTextField().setForeground(new Color(220, 20, 60));
		aepEndPick.getJFormattedTextField().setFont(new Font("Microsoft New Tai Lue", Font.BOLD, 15));
		aepEndPick.setSize(192, 23);
		
		tables_inputPanel.add(aepEndPick);
		
		//Input Section (Labels)
		
		JLabel tables_visaExpireLbl = new JLabel("Visa Expiry Date:");
		tables_visaExpireLbl.setForeground(Color.WHITE);
		tables_visaExpireLbl.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 13));
		tables_visaExpireLbl.setBounds(230, 323, 192, 29);
		tables_inputPanel.add(tables_visaExpireLbl);
		
		JLabel tables_permitLbl = new JLabel("Permit Type:");
		tables_permitLbl.setForeground(Color.WHITE);
		tables_permitLbl.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 13));
		tables_permitLbl.setBounds(20, 378, 190, 29);
		tables_inputPanel.add(tables_permitLbl);
		
		tables_permitTypeTxt = new JTextField();
		tables_permitTypeTxt.setBorder(new EmptyBorder(0, 0, 0, 0));
		tables_permitTypeTxt.setFont(new Font("Microsoft New Tai Lue", Font.BOLD, 15));
		tables_permitTypeTxt.setColumns(10);
		tables_permitTypeTxt.setBounds(20, 409, 400, 23);
		tables_inputPanel.add(tables_permitTypeTxt);
		
		JLabel tables_permitStartLbl = new JLabel("Permit Start Date:");
		tables_permitStartLbl.setForeground(Color.WHITE);
		tables_permitStartLbl.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 13));
		tables_permitStartLbl.setBounds(20, 435, 190, 29);
		tables_inputPanel.add(tables_permitStartLbl);
		
		JLabel tables_permitExpireLbl = new JLabel("Permit Expiry Date:");
		tables_permitExpireLbl.setForeground(Color.WHITE);
		tables_permitExpireLbl.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 13));
		tables_permitExpireLbl.setBounds(230, 435, 192, 29);
		tables_inputPanel.add(tables_permitExpireLbl);
		
		JLabel tables_aepIdLbl = new JLabel("AEP ID:");
		tables_aepIdLbl.setForeground(Color.WHITE);
		tables_aepIdLbl.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 13));
		tables_aepIdLbl.setBounds(20, 487, 197, 29);
		tables_inputPanel.add(tables_aepIdLbl);
		
		tables_aepIdTxt = new JTextField();
		tables_aepIdTxt.setFont(new Font("Microsoft New Tai Lue", Font.BOLD, 15));
		tables_aepIdTxt.setColumns(10);
		tables_aepIdTxt.setBorder(new EmptyBorder(0, 0, 0, 0));
		tables_aepIdTxt.setBounds(20, 518, 400, 23);
		tables_inputPanel.add(tables_aepIdTxt);
		
		JLabel tables_aepStartDateLbl = new JLabel("AEP Start Date:");
		tables_aepStartDateLbl.setForeground(Color.WHITE);
		tables_aepStartDateLbl.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 13));
		tables_aepStartDateLbl.setBounds(20, 543, 190, 29);
		tables_inputPanel.add(tables_aepStartDateLbl);
		
		JLabel tables_aepEndDateLbl = new JLabel("AEP Expiry Date:");
		tables_aepEndDateLbl.setForeground(Color.WHITE);
		tables_aepEndDateLbl.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 13));
		tables_aepEndDateLbl.setBounds(228, 543, 192, 29);
		tables_inputPanel.add(tables_aepEndDateLbl);
		
		JLabel tables_chooseLbl = new JLabel("Choose a Client:");
		tables_chooseLbl.setForeground(Color.WHITE);
		tables_chooseLbl.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 13));
		tables_chooseLbl.setBounds(20, 40, 190, 41);
		tables_inputPanel.add(tables_chooseLbl);
		
		JLabel tables_lastnameLbl = new JLabel("Lastname:");
		tables_lastnameLbl.setForeground(Color.WHITE);
		tables_lastnameLbl.setFont(new Font("Segoe UI Semibold", Font.BOLD, 17));
		tables_lastnameLbl.setBounds(624, 570, 418, 31);
		getContentPane().add(tables_lastnameLbl);

		JLabel tables_firstnameLbl = new JLabel("Firstname:");
		tables_firstnameLbl.setForeground(Color.WHITE);
		tables_firstnameLbl.setFont(new Font("Segoe UI Semibold", Font.BOLD, 17));
		tables_firstnameLbl.setBounds(624, 604, 414, 31);
		getContentPane().add(tables_firstnameLbl);
		
		JLabel tables_aliasLbl = new JLabel("Alias:");
		tables_aliasLbl.setForeground(Color.WHITE);
		tables_aliasLbl.setFont(new Font("Segoe UI Semibold", Font.BOLD, 17));
		tables_aliasLbl.setBounds(624, 634, 418, 37);
		getContentPane().add(tables_aliasLbl);
		
		JLabel tables_nationalityLbl = new JLabel("Nationality:");
		tables_nationalityLbl.setForeground(Color.WHITE);
		tables_nationalityLbl.setFont(new Font("Segoe UI Semibold", Font.BOLD, 17));
		tables_nationalityLbl.setBounds(624, 715, 418, 31);
		getContentPane().add(tables_nationalityLbl);
		
		JLabel tables_birthdateLbl = new JLabel("Birthdate:");
		tables_birthdateLbl.setForeground(Color.WHITE);
		tables_birthdateLbl.setFont(new Font("Segoe UI Semibold", Font.BOLD, 17));
		tables_birthdateLbl.setBounds(624, 749, 414, 31);
		getContentPane().add(tables_birthdateLbl);
		
		JLabel tables_genderLbl = new JLabel("Gender:");
		tables_genderLbl.setForeground(Color.WHITE);
		tables_genderLbl.setFont(new Font("Segoe UI Semibold", Font.BOLD, 17));
		tables_genderLbl.setBounds(624, 779, 418, 37);
		getContentPane().add(tables_genderLbl);
		
		JLabel tables_companyLbl = new JLabel("Company:");
		tables_companyLbl.setForeground(Color.WHITE);
		tables_companyLbl.setFont(new Font("Segoe UI Semibold", Font.BOLD, 17));
		tables_companyLbl.setBounds(1173, 725, 358, 37);
		getContentPane().add(tables_companyLbl);
		
		JLabel tables_emaiLbl = new JLabel("Email:");
		tables_emaiLbl.setForeground(Color.WHITE);
		tables_emaiLbl.setFont(new Font("Segoe UI Semibold", Font.BOLD, 17));
		tables_emaiLbl.setBounds(1170, 570, 361, 37);
		getContentPane().add(tables_emaiLbl);
		
		JLabel tables_contactLbl = new JLabel("Contact No.:");
		tables_contactLbl.setForeground(Color.WHITE);
		tables_contactLbl.setFont(new Font("Segoe UI Semibold", Font.BOLD, 17));
		tables_contactLbl.setBounds(1170, 604, 361, 37);
		getContentPane().add(tables_contactLbl);
		
		JLabel tables_companyPositionLbl = new JLabel("Company Position:");
		tables_companyPositionLbl.setForeground(Color.WHITE);
		tables_companyPositionLbl.setFont(new Font("Segoe UI Semibold", Font.BOLD, 17));
		tables_companyPositionLbl.setBounds(1173, 760, 358, 31);
		getContentPane().add(tables_companyPositionLbl);
		
		JButton tables_registerBtn = new JButton("Register Info");
		tables_registerBtn.setBorder(null);
		tables_registerBtn.setForeground(new Color(255, 255, 255));	

		tables_registerBtn.setEnabled(false);
		
		tables_comboBox.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				
				tables_passportNoTxt.setText("");
				tables_tinIdTxt.setText("");
				
				if(tables_comboBox.getSelectedIndex() == 0) {
					tables_passportNoTxt.setEnabled(false);
					tables_tinIdTxt.setEnabled(false);
					tables_visaTypeTxt.setEnabled(false);
					visaStartPick.setEnabled(false);
					visaEndPick.setEnabled(false);
					tables_permitTypeTxt.setEnabled(false);
					permitStartPick.setEnabled(false);
					permitEndPick.setEnabled(false);
					tables_aepIdTxt.setEnabled(false);
					aepStartPick.setEnabled(false);
					aepEndPick.setEnabled(false);
				}
				
				if(tables_comboBox.getSelectedIndex() != 0) 
				{
					tables_registerBtn.setEnabled(true);
					tables_passportNoTxt.setEnabled(true);
					tables_tinIdTxt.setEnabled(true);
					tables_visaTypeTxt.setEnabled(true);
					visaStartPick.setEnabled(true);
					visaEndPick.setEnabled(true);
					tables_permitTypeTxt.setEnabled(true);
					permitStartPick.setEnabled(true);
					permitEndPick.setEnabled(true);
					tables_aepIdTxt.setEnabled(true);
					aepStartPick.setEnabled(true);
					aepEndPick.setEnabled(true);
					
					UIManager.put("OptionPane.background",new ColorUIResource(90, 103, 115));
				 	UIManager.put("Panel.background",new ColorUIResource(90, 103, 115));
				 	UIManager.put("OptionPane.messageFont", new Font("Segoe UI Semibold", Font.BOLD, 14));
				 	UIManager.put("Button.background", Color.WHITE);
				 	UIManager.put("OptionPane.foreground",new ColorUIResource(90, 103, 115));
				 	
				 	
				 	ArrayList<Client> clist = Queries.getClients();
				 	Client c = clist.get(tables_comboBox.getSelectedIndex() - 1);
					tables_lastnameLbl.setText("Lastname: "+c.getClient_lastname());
					tables_firstnameLbl.setText("Firstname: "+c.getClient_firstname());
					tables_aliasLbl.setText("Alias: "+c.getClient_alias());
					tables_nationalityLbl.setText("Nationality: "+c.getClient_nationality());
					tables_birthdateLbl.setText("Birthdate: "+c.getClient_birthdate());
					tables_genderLbl.setText("Gender: "+c.getClient_gender());
					tables_companyLbl.setText("Company: "+c.getClient_company());
					tables_companyPositionLbl.setText("Company Position: "+c.getClient_position());
					tables_emaiLbl.setText("Email: "+c.getClient_email());
					tables_contactLbl.setText("Contact No.: "+c.getClient_contact());
					
					if (tables_passportNoTxt.getText().equals("") && tables_tinIdTxt.getText().equals("") ) {
						tables_passportNoTxt.setEditable(true);
						tables_tinIdTxt.setEditable(true);
					}
					else 
					{
						tables_passportNoTxt.setEditable(false);
						tables_tinIdTxt.setEditable(false);
					}
					if(tables_comboBox.getSelectedItem() == tables_comboBox.getItemAt(0)) 
						JOptionPane.showMessageDialog(null, "<html><font color = #ffffff>Please Select a Client</font color = #ffffff></html>", "Invalid Selection", JOptionPane.ERROR_MESSAGE);
					else
					{
						client_id = tables_comboBox.getSelectedIndex();
						tables_reloadBtn.doClick();
					}
				}
			}});
		
		JLabel tables_clientInfo = new JLabel("------------------------ Client Transaction Details -----------------------");
		tables_clientInfo.setHorizontalAlignment(SwingConstants.LEFT);
		tables_clientInfo.setForeground(Color.WHITE);
		tables_clientInfo.setFont(new Font("Segoe UI Semibold", Font.BOLD, 13));
		tables_clientInfo.setBounds(20, 106, 400, 41);
		tables_inputPanel.add(tables_clientInfo);
		
		java.util.Date date=new java.util.Date();
		java.sql.Date sqlDate=new java.sql.Date(date.getTime());
		
		tables_registerBtn.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				
				
				UIManager.put("OptionPane.background",new ColorUIResource(90, 103, 115));
			 	UIManager.put("Panel.background",new ColorUIResource(90, 103, 115));
			 	UIManager.put("OptionPane.messageFont", new Font("Segoe UI Semibold", Font.BOLD, 14));
			 	UIManager.put("Button.background", Color.WHITE);
			 	UIManager.put("OptionPane.foreground",new ColorUIResource(90, 103, 115));
				
				String vs = visaStartPick.getJFormattedTextField().getText().toString();
				String ve = visaEndPick.getJFormattedTextField().getText().toString();
				String ps = permitStartPick.getJFormattedTextField().getText().toString();
				String pe = permitEndPick.getJFormattedTextField().getText().toString();
				String as = aepStartPick.getJFormattedTextField().getText().toString();
				String ae = aepEndPick.getJFormattedTextField().getText().toString();
				try {
					boolean visaValid = false;
					boolean permitValid = false;
					boolean aepValid = false;
					boolean ptntValid = false;
					if(tables_passportNoTxt.getText() != "") {
						if(tables_tinIdTxt.getText() != "") {
							if((!(tables_visaTypeTxt.getText().isEmpty()) && !(ve.isEmpty() && vs.isEmpty()) || (tables_visaTypeTxt.getText().isEmpty()) && (ve.isEmpty() && vs.isEmpty())) && DateCheck(ve,vs)) {
								visaValid = true;
							}
							else if((tables_visaTypeTxt.getText().isEmpty()) && !(ve.isEmpty() && vs.isEmpty())) {
								JOptionPane.showMessageDialog(null, "<html><font color = #ffffff>The VISA TYPE field must not be empty. Please specify one.</font color = #ffffff></html>", "Detected an empty Visa Type Field", JOptionPane.ERROR_MESSAGE);
							}
							else if(!(tables_visaTypeTxt.getText().isEmpty()) && (ve.isEmpty() && vs.isEmpty())){
								JOptionPane.showMessageDialog(null, "<html><font color = #ffffff>Please specify visa start date and expiry date.</font color = #ffffff></html>", "Detected an empty Visa Type Field", JOptionPane.ERROR_MESSAGE);
							}
							
							
							if((!(tables_permitTypeTxt.getText().isEmpty()) && !(pe.isEmpty() && ps.isEmpty()) || (tables_permitTypeTxt.getText().isEmpty()) && (pe.isEmpty() && ps.isEmpty())) && DateCheck(ps,pe)) {
								permitValid = true;
							}
							else if((tables_permitTypeTxt.getText().isEmpty()) && !(pe.isEmpty() && ps.isEmpty())) {
								JOptionPane.showMessageDialog(null, "<html><font color = #ffffff>The PERMIT TYPE field must not be empty. Please specify one.</font color = #ffffff></html>", "Detected an empty Visa Type Field", JOptionPane.ERROR_MESSAGE);
							}
							else if(!(tables_permitTypeTxt.getText().isEmpty()) && (pe.isEmpty() && ps.isEmpty())){
								JOptionPane.showMessageDialog(null, "<html><font color = #ffffff>Please specify permit start date and expiry date.</font color = #ffffff></html>", "Detected an empty Visa Type Field", JOptionPane.ERROR_MESSAGE);
							}
							
							
							if((!(tables_aepIdTxt.getText().isEmpty()) && !(ae.isEmpty() && as.isEmpty()) || (tables_aepIdTxt.getText().isEmpty()) && (ae.isEmpty() && as.isEmpty())) && DateCheck(as,ae)) {
								aepValid = true;
							}
							else if((tables_aepIdTxt.getText().isEmpty()) && !(ae.isEmpty() && as.isEmpty())) {
								JOptionPane.showMessageDialog(null, "<html><font color = #ffffff>The AEP ID field must not be empty. Please specify one.</font color = #ffffff></html>", "Detected an empty Visa Type Field", JOptionPane.ERROR_MESSAGE);
							}
							else if(!(tables_aepIdTxt.getText().isEmpty()) && (ae.isEmpty() && as.isEmpty())){
								JOptionPane.showMessageDialog(null, "<html><font color = #ffffff>Please specify AEP start date and expiry date.</font color = #ffffff></html>", "Detected an empty Visa Type Field", JOptionPane.ERROR_MESSAGE);
							}
							
							if((tables_passportNoTxt.getText().length() > 25) || (tables_tinIdTxt.getText().length() > 25))
								JOptionPane.showMessageDialog(null, "<html><font color = #ffffff>Please limit Passport No./TIN ID to 25 and less characters..</font color = #ffffff></html>", "Detected invalid length for Passport No./TIN ID", JOptionPane.ERROR_MESSAGE);
							
							if((tables_aepIdTxt.getText().length() > 25))
								JOptionPane.showMessageDialog(null, "<html><font color = #ffffff>Please limit AEP ID to 25 and less characters..</font color = #ffffff></html>", "Detected invalid length for AEP ID", JOptionPane.ERROR_MESSAGE);
							
							else
								ptntValid = true;
							if(visaValid && permitValid && aepValid && ptntValid) 
							{
								Register();
							}							
						}
						else {
							JOptionPane.showMessageDialog(null, "<html><font color = #ffffff>The TIN ID field must not be empty. Please specify one.</font color = #ffffff></html>", "Detected an empty TIN ID field", JOptionPane.ERROR_MESSAGE);
						}
					}
					else {
						JOptionPane.showMessageDialog(null, "<html><font color = #ffffff>The Passport No. field must not be empty. Please specify one.</font color = #ffffff></html>", "Detected an empty Passport No. field", JOptionPane.ERROR_MESSAGE);
					}
				}catch (Exception e3) {
					e3.printStackTrace();
				}
				
				tables_reloadBtn.doClick();
			}// end of action performed
			
		public void Register() 
		{			
			UIManager.put("OptionPane.background",new ColorUIResource(90, 103, 115));
		 	UIManager.put("Panel.background",new ColorUIResource(90, 103, 115));
		 	UIManager.put("OptionPane.messageFont", new Font("Segoe UI Semibold", Font.BOLD, 14));
		 	UIManager.put("Button.background", Color.WHITE);
		 	UIManager.put("OptionPane.foreground",new ColorUIResource(90, 103, 115));
			Transaction trans = new Transaction();
			trans.setPassportNo(tables_passportNoTxt.getText());
			trans.setTinID(tables_tinIdTxt.getText());
			trans.setVisaType(tables_visaTypeTxt.getText());				
			if(visaStartPick.getJFormattedTextField().getText().toString().equals(""))
				trans.setVisaStartDate(null);
			else
				trans.setVisaStartDate(java.sql.Date.valueOf(visaStartPick.getJFormattedTextField().getText().toString()));			
			if(visaEndPick.getJFormattedTextField().getText().toString().equals(""))
				trans.setVisaEndDate(null);
			else
				trans.setVisaEndDate(java.sql.Date.valueOf(visaEndPick.getJFormattedTextField().getText().toString()));
			trans.setPermitType(tables_permitTypeTxt.getText());
			if(permitStartPick.getJFormattedTextField().getText().toString().equals(""))
				trans.setPermitStartDate(null);
			else
				trans.setPermitStartDate(java.sql.Date.valueOf(permitStartPick.getJFormattedTextField().getText().toString()));
			if(permitEndPick.getJFormattedTextField().getText().toString().equals(""))
				trans.setPermitEndDate(null);
			else
				trans.setPermitEndDate(java.sql.Date.valueOf(permitEndPick.getJFormattedTextField().getText().toString()));
			trans.setAepID(tables_aepIdTxt.getText());
			if(aepStartPick.getJFormattedTextField().getText().toString().equals(""))
				trans.setAepStartDate(null);
			else
				trans.setAepEndDate(java.sql.Date.valueOf(aepStartPick.getJFormattedTextField().getText().toString()));		
			if(aepEndPick.getJFormattedTextField().getText().toString().equals(""))
				trans.setAepEndDate(null);
			else
				trans.setAepEndDate(java.sql.Date.valueOf(aepEndPick.getJFormattedTextField().getText().toString()));
			trans.setClient_id(Integer.parseInt(objectFilter.getClientList()[client_id].split(":")[1].trim()));
			Calendar calendar = Calendar.getInstance();
			java.sql.Date currentDate = new java.sql.Date(calendar.getTime().getTime());
			trans.setTransTimestamp(currentDate);
			trans.setTransAuthor(Runner.getUser().getUser_username());
			boolean c = Queries.insertTransaction(trans);
			if(c)
				JOptionPane.showMessageDialog(null, "<html><font color = #ffffff>Transaction inserted successfully.</font color = #ffffff></html>", "Transaction Created", JOptionPane.INFORMATION_MESSAGE);
			tables_passportNoTxt.setText("");
			tables_tinIdTxt.setText("");
			tables_visaTypeTxt.setText("");
			tables_permitTypeTxt.setText("");
			tables_aepIdTxt.setText("");
			visaStartPick.getJFormattedTextField().setText("");
			visaEndPick.getJFormattedTextField().setText("");
			aepStartPick.getJFormattedTextField().setText("");
			aepEndPick.getJFormattedTextField().setText("");
			permitStartPick.getJFormattedTextField().setText("");
			permitEndPick.getJFormattedTextField().setText("");
			tables_inputPanel.revalidate();
		}
	});//end of action listener
		
		tables_registerBtn.setBackground(new Color(0, 102, 102));
		tables_registerBtn.setFont(new Font("Segoe UI Semibold", Font.BOLD, 13));
		tables_registerBtn.setBounds(134, 628, 173, 35);
		tables_inputPanel.add(tables_registerBtn);
		
		JLabel tables_clientInfoLb = new JLabel("------------------------ Client Information Details -----------------------");
		tables_clientInfoLb.setHorizontalAlignment(SwingConstants.LEFT);
		tables_clientInfoLb.setForeground(Color.WHITE);
		tables_clientInfoLb.setFont(new Font("Segoe UI Semibold", Font.BOLD, 13));
		tables_clientInfoLb.setBounds(20, 11, 400, 41);
		tables_inputPanel.add(tables_clientInfoLb);
		
		JLabel tables_clientCreateTransactionLbl = new JLabel("Create New Transaction", SwingConstants.CENTER);
		tables_clientCreateTransactionLbl.setBounds(330, 48, 227, 37);
		tables_clientCreateTransactionLbl.setForeground(Color.WHITE);
		tables_clientCreateTransactionLbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
		
		JLabel tables_clientStatusTableLbl = new JLabel("Client Status Table", SwingConstants.CENTER);
		tables_clientStatusTableLbl.setBounds(929, 48, 243, 37);
		tables_clientStatusTableLbl.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				new TablesStatus().setVisible(true);
				dispose();
			}
		});
		tables_clientStatusTableLbl.setForeground(Color.LIGHT_GRAY);
		tables_clientStatusTableLbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
		
		JLabel tables_clientRemarksTableLbl = new JLabel("Client Remarks Table", SwingConstants.CENTER);
		tables_clientRemarksTableLbl.setBounds(1241, 48, 230, 37);
		tables_clientRemarksTableLbl.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				new TablesRemarks().setVisible(true);
				dispose();
			}
		});
		tables_clientRemarksTableLbl.setForeground(Color.LIGHT_GRAY);
		tables_clientRemarksTableLbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
		
		JLabel label = new JLabel("");
		label.setBounds(1178, 48, 57, 37);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Segoe UI Semibold", Font.BOLD, 15));
		
		JLabel tables_line = new JLabel("");
		tables_line.setBounds(412, 96, 57, 22);
		tables_line.setIcon(new ImageIcon(Tables.class.getResource("/jdl/Assets/line.png")));
		tables_line.setHorizontalAlignment(SwingConstants.CENTER);
		tables_line.setForeground(Color.WHITE);
		tables_line.setFont(new Font("Segoe UI Semibold", Font.BOLD, 15));
		
		JLabel tables_updateTransactionLbl = new JLabel("Update Transaction", SwingConstants.CENTER);
		tables_updateTransactionLbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				Runner.openTUT();
				setVisible(false);
			}
		});
		tables_updateTransactionLbl.setBounds(626, 48, 249, 37);
		tables_updateTransactionLbl.setForeground(Color.LIGHT_GRAY);
		tables_updateTransactionLbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
		
		JLabel tables_addClientLbl = new JLabel("Add New Client", SwingConstants.CENTER);
		tables_addClientLbl.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				new TablesAddClient().setVisible(true);
				dispose();
			}
		});
		tables_addClientLbl.setBounds(25, 48, 295, 37);
		tables_addClientLbl.setForeground(Color.LIGHT_GRAY);
		tables_addClientLbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
		
		JLabel tables_specificClientLbl = new JLabel("Client Transactions");
		tables_specificClientLbl.setBackground(new Color(0, 153, 153));
		tables_specificClientLbl.setBounds(491, 146, 268, 37);
		tables_specificClientLbl.setForeground(Color.WHITE);
		tables_specificClientLbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
		
		JLabel tables_clientInfoLbl = new JLabel("Client Information");
		tables_clientInfoLbl.setForeground(Color.WHITE);
		tables_clientInfoLbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
		tables_clientInfoLbl.setBounds(493, 513, 227, 37);
		getContentPane().add(tables_clientInfoLbl);
		
		JLabel tables_primaryInfoImg = new JLabel("");
		tables_primaryInfoImg.setIcon(new ImageIcon(Tables.class.getResource("/jdl/Assets/client_infoIcon.png")));
		tables_primaryInfoImg.setBounds(503, 561, 104, 115);
		getContentPane().add(tables_primaryInfoImg);
		
		JLabel tables_clientSecondaryInfoImg = new JLabel("");
		tables_clientSecondaryInfoImg.setIcon(new ImageIcon(Tables.class.getResource("/jdl/Assets/client_secondaryInfoIcon.png")));
		tables_clientSecondaryInfoImg.setBounds(503, 711, 104, 105);
		getContentPane().add(tables_clientSecondaryInfoImg);
		
		JLabel label_2 = new JLabel("");
		label_2.setIcon(new ImageIcon(Tables.class.getResource("/jdl/Assets/client_company1.png")));
		label_2.setBounds(1052, 711, 104, 105);
		getContentPane().add(label_2);

		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon(Tables.class.getResource("/jdl/Assets/client_emails.png")));
		label_1.setBounds(1048, 561, 112, 105);
		getContentPane().add(label_1);
		
		// Add to Panels 
		
		getContentPane().setLayout(null);
		
		//Images
		
		JLabel tables_minimize = new JLabel("");
		tables_minimize.setBounds(1515, 0, 35, 41);
		getContentPane().add(tables_minimize);
		tables_minimize.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				setState(ICONIFIED);
			}
		});
		tables_minimize.setIcon(new ImageIcon(Tables.class.getResource("/jdl/Assets/button_minimizer.png")));
		
		JLabel tables_seeTablesLbl = new JLabel("See Tables");
		tables_seeTablesLbl.setBounds(667, 4, 168, 37);
		getContentPane().add(tables_seeTablesLbl);
		tables_seeTablesLbl.setHorizontalAlignment(SwingConstants.CENTER);
		tables_seeTablesLbl.setForeground(Color.WHITE);
		tables_seeTablesLbl.setFont(new Font("Segoe UI Semibold", Font.BOLD, 15));
		
		JLabel tables_back = new JLabel("");
		tables_back.setBounds(0, 0, 57, 37);
		getContentPane().add(tables_back);
		tables_back.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) 
			{
				Runner.destroyTables();
				Runner.openOptionList();
			}
		});
		tables_back.setIcon(new ImageIcon(Tables.class.getResource("/jdl/Assets/button_back.png")));
		tables_back.setHorizontalAlignment(SwingConstants.CENTER);
		tables_back.setForeground(Color.WHITE);
		tables_back.setFont(new Font("Segoe UI Semibold", Font.BOLD, 15));
		getContentPane().add(tables_inputSectionLbl);
		getContentPane().add(tables_reloadBtn);
		getContentPane().add(tables_addClientLbl);
		getContentPane().add(tables_clientCreateTransactionLbl);
		getContentPane().add(tables_updateTransactionLbl);
		getContentPane().add(tables_clientStatusTableLbl);
		getContentPane().add(label);
		getContentPane().add(tables_clientRemarksTableLbl);
		getContentPane().add(tables_line);
		getContentPane().add(tables_inputPanel);
		getContentPane().add(scrollPane_1);
		getContentPane().add(tables_specificClientLbl);
		
		JLabel background_tables = new JLabel("");
		background_tables.setIcon(new ImageIcon(Tables.class.getResource("/jdl/Assets/background_tables4.jpg")));
		background_tables.setBounds(0, 0, 1550, 870);
		getContentPane().add(background_tables);

	}
}
