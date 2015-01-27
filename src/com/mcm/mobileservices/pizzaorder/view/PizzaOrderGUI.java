package com.mcm.mobileservices.pizzaorder.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.mcm.mobileservices.pizzaorder.controller.PizzaOrderController;
import com.mcm.mobileservices.pizzaorder.entities.PizzaDetails;
import com.mcm.mobileservices.pizzaorder.entities.UserDetails;

public class PizzaOrderGUI extends JFrame implements Observer {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtTelephoneNumber;
	private JTextField txtNameOfPerson;
	private JTextArea txtrAddress;
	private PizzaOrderController pizzaOrderController;
	private JLabel lblUserDetailsMessageLabel;
	private JButton btnSaveDetails;
	private JButton btnSearchDetails;
	private JButton btnUpdateDetails;
	private JTextField txtPizzaName;
	private JTextArea txtrPizzaDescription;
	private JTextArea txtrPizzaContents;
	private JCheckBox chckbxPizzaActive;
	private JButton btnSearchPizza;
	private JButton btnSavePizza;
	private JButton btnUpdatePizza;
	private JLabel lblPizzaDetailsMessageLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PizzaOrderGUI frame = new PizzaOrderGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PizzaOrderGUI() {

		pizzaOrderController = new PizzaOrderController();
		pizzaOrderController.addObserver(this);

		setTitle("Hagenberg - Pizza Order Service");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 661, 438);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null,
				null, null));
		contentPane.add(tabbedPane, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null,
				null));
		tabbedPane.addTab("User Details", null, panel, null);
		panel.setLayout(new BorderLayout(0, 0));

		JLabel lblViewUpdateUserDetails = new JLabel(
				"View & Update User Details");
		lblViewUpdateUserDetails.setHorizontalAlignment(SwingConstants.CENTER);
		lblViewUpdateUserDetails.setFont(new Font("Tahoma", Font.BOLD, 30));
		panel.add(lblViewUpdateUserDetails, BorderLayout.NORTH);

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(new EtchedBorder(
				EtchedBorder.LOWERED, null, null), "User Details",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new GridLayout(3, 2, 10, 10));

		JLabel lblTelephoneNumber = new JLabel("Telephone Number");
		panel_3.add(lblTelephoneNumber);

		txtTelephoneNumber = new JTextField();
		txtTelephoneNumber.setText("Telephone Number");
		panel_3.add(txtTelephoneNumber);
		txtTelephoneNumber.setColumns(10);

		JLabel lblPersonName = new JLabel("Person Name");
		panel_3.add(lblPersonName);

		txtNameOfPerson = new JTextField();
		txtNameOfPerson.setText("Name of person");
		panel_3.add(txtNameOfPerson);
		txtNameOfPerson.setColumns(10);

		JLabel lblAddress = new JLabel("Address");
		panel_3.add(lblAddress);

		txtrAddress = new JTextArea();
		txtrAddress.setLineWrap(true);
		txtrAddress.setWrapStyleWord(true);
		txtrAddress.setColumns(10);
		txtrAddress.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtrAddress.setRows(6);
		txtrAddress.setText("Address of Person");
		panel_3.add(txtrAddress);

		JPanel panel_4 = new JPanel();
		panel.add(panel_4, BorderLayout.SOUTH);
		GridBagLayout gbl_panel_4 = new GridBagLayout();
		gbl_panel_4.columnWidths = new int[] { 207, 207, 207, 0 };
		gbl_panel_4.rowHeights = new int[] { 23, 23, 0 };
		gbl_panel_4.columnWeights = new double[] { 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_panel_4.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		panel_4.setLayout(gbl_panel_4);

		btnSaveDetails = new JButton("Save Details");
		btnSaveDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final UserDetails userDetails = new UserDetails();
				userDetails.setTelephoneNumber(txtTelephoneNumber.getText());
				userDetails.setUserName(txtNameOfPerson.getText());
				userDetails.setUserAddress(txtrAddress.getText());
				new Thread() {
					public void run() {
						pizzaOrderController.submitUserDetails(userDetails);
					}
				}.start();
				lblUserDetailsMessageLabel
						.setText("Please Wait .. User is getting registered");
				btnSaveDetails.setEnabled(false);
				btnSearchDetails.setEnabled(false);
				btnUpdateDetails.setEnabled(false);
			}
		});

		btnSearchDetails = new JButton("Search Details");
		btnSearchDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread() {
					public void run() {
						pizzaOrderController
								.searchUserDetails(txtTelephoneNumber.getText());
					}
				}.start();
				lblUserDetailsMessageLabel
						.setText("Please Wait .. User is getting searched");
				btnSearchDetails.setEnabled(false);
				btnSaveDetails.setEnabled(false);
				btnUpdateDetails.setEnabled(false);
			}
		});
		GridBagConstraints gbc_btnSearchDetails = new GridBagConstraints();
		gbc_btnSearchDetails.fill = GridBagConstraints.BOTH;
		gbc_btnSearchDetails.insets = new Insets(0, 0, 5, 5);
		gbc_btnSearchDetails.gridx = 0;
		gbc_btnSearchDetails.gridy = 0;
		panel_4.add(btnSearchDetails, gbc_btnSearchDetails);
		GridBagConstraints gbc_btnSaveDetails = new GridBagConstraints();
		gbc_btnSaveDetails.fill = GridBagConstraints.BOTH;
		gbc_btnSaveDetails.insets = new Insets(0, 0, 5, 5);
		gbc_btnSaveDetails.gridx = 1;
		gbc_btnSaveDetails.gridy = 0;
		panel_4.add(btnSaveDetails, gbc_btnSaveDetails);

		btnUpdateDetails = new JButton("Update Details");
		btnUpdateDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final UserDetails userDetails = new UserDetails();
				userDetails.setTelephoneNumber(txtTelephoneNumber.getText());
				userDetails.setUserName(txtNameOfPerson.getText());
				userDetails.setUserAddress(txtrAddress.getText());
				new Thread() {
					public void run() {
						pizzaOrderController.updateUserDetails(userDetails);
					}
				}.start();
				lblUserDetailsMessageLabel
						.setText("Please Wait .. User Details are getting updated");
				btnSearchDetails.setEnabled(false);
				btnSaveDetails.setEnabled(false);
				btnUpdateDetails.setEnabled(false);
			}
		});
		GridBagConstraints gbc_btnUpdateDetails = new GridBagConstraints();
		gbc_btnUpdateDetails.fill = GridBagConstraints.BOTH;
		gbc_btnUpdateDetails.insets = new Insets(0, 0, 5, 0);
		gbc_btnUpdateDetails.gridx = 2;
		gbc_btnUpdateDetails.gridy = 0;
		panel_4.add(btnUpdateDetails, gbc_btnUpdateDetails);

		lblUserDetailsMessageLabel = new JLabel("");
		lblUserDetailsMessageLabel
				.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblUserDetailsMessageLabel = new GridBagConstraints();
		gbc_lblUserDetailsMessageLabel.gridwidth = 3;
		gbc_lblUserDetailsMessageLabel.fill = GridBagConstraints.BOTH;
		gbc_lblUserDetailsMessageLabel.insets = new Insets(0, 0, 0, 5);
		gbc_lblUserDetailsMessageLabel.gridx = 0;
		gbc_lblUserDetailsMessageLabel.gridy = 1;
		panel_4.add(lblUserDetailsMessageLabel, gbc_lblUserDetailsMessageLabel);

		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Pizza Details", null, panel_1, null);
		panel_1.setLayout(new BorderLayout(0, 0));

		JLabel lblViewUpdatePizzaDetails = new JLabel(
				"View & Update Pizza Details");
		lblViewUpdatePizzaDetails.setHorizontalAlignment(SwingConstants.CENTER);
		lblViewUpdatePizzaDetails.setFont(new Font("Tahoma", Font.BOLD, 30));
		panel_1.add(lblViewUpdatePizzaDetails, BorderLayout.NORTH);

		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new TitledBorder(null, "Pizza Details",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.add(panel_5, BorderLayout.CENTER);
		panel_5.setLayout(new GridLayout(4, 2, 10, 10));

		JLabel lblNewLabel = new JLabel("Pizza Name");
		panel_5.add(lblNewLabel);

		txtPizzaName = new JTextField();
		txtPizzaName.setText("Pizza Name");
		panel_5.add(txtPizzaName);
		txtPizzaName.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Pizza Description");
		panel_5.add(lblNewLabel_1);

		txtrPizzaDescription = new JTextArea();
		txtrPizzaDescription.setWrapStyleWord(true);
		txtrPizzaDescription.setLineWrap(true);
		txtrPizzaDescription.setText("Pizza Description");
		panel_5.add(txtrPizzaDescription);

		JLabel lblPizzaContents = new JLabel("Pizza Contents");
		panel_5.add(lblPizzaContents);

		txtrPizzaContents = new JTextArea();
		txtrPizzaContents.setLineWrap(true);
		txtrPizzaContents.setWrapStyleWord(true);
		txtrPizzaContents.setText("Pizza Contents");
		panel_5.add(txtrPizzaContents);

		JLabel lblPizzaActive = new JLabel("Pizza Active");
		panel_5.add(lblPizzaActive);

		chckbxPizzaActive = new JCheckBox("");
		panel_5.add(chckbxPizzaActive);

		JPanel panel_6 = new JPanel();
		panel_1.add(panel_6, BorderLayout.SOUTH);
		GridBagLayout gbl_panel_6 = new GridBagLayout();
		gbl_panel_6.columnWidths = new int[] { 207, 207, 207, 0 };
		gbl_panel_6.rowHeights = new int[] { 23, 23, 0 };
		gbl_panel_6.columnWeights = new double[] { 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_panel_6.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		panel_6.setLayout(gbl_panel_6);

		btnSearchPizza = new JButton("Search Pizza");
		btnSearchPizza.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread() {
					public void run() {
						pizzaOrderController.searchPizzaDetails(txtPizzaName
								.getText());
					}
				}.start();
				lblPizzaDetailsMessageLabel
						.setText("Please Wait .. Pizza is getting searched");
				btnSearchPizza.setEnabled(false);
				btnUpdatePizza.setEnabled(false);
				btnSavePizza.setEnabled(false);
			}
		});
		GridBagConstraints gbc_btnSearchPizza = new GridBagConstraints();
		gbc_btnSearchPizza.fill = GridBagConstraints.BOTH;
		gbc_btnSearchPizza.insets = new Insets(0, 0, 5, 5);
		gbc_btnSearchPizza.gridx = 0;
		gbc_btnSearchPizza.gridy = 0;
		panel_6.add(btnSearchPizza, gbc_btnSearchPizza);

		btnSavePizza = new JButton("Save Pizza");
		btnSavePizza.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final PizzaDetails pizzaDetails = new PizzaDetails();
				pizzaDetails.setPizzaName(txtPizzaName.getText());
				pizzaDetails.setPizzaDescription(txtrPizzaDescription.getText());
				pizzaDetails.setPizzaContent(txtrPizzaContents.getText());
				pizzaDetails.setActive(chckbxPizzaActive.isSelected());
				new Thread() {
					public void run() {
						pizzaOrderController.submitPizzaDetails(pizzaDetails);
					}
				}.start();
				lblPizzaDetailsMessageLabel
						.setText("Please Wait .. Pizza is getting added to database");
				btnSavePizza.setEnabled(false);
				btnSearchPizza.setEnabled(false);
				btnUpdatePizza.setEnabled(false);
			}
		});
		GridBagConstraints gbc_btnSavePizza = new GridBagConstraints();
		gbc_btnSavePizza.fill = GridBagConstraints.BOTH;
		gbc_btnSavePizza.insets = new Insets(0, 0, 5, 5);
		gbc_btnSavePizza.gridx = 1;
		gbc_btnSavePizza.gridy = 0;
		panel_6.add(btnSavePizza, gbc_btnSavePizza);

		btnUpdatePizza = new JButton("Update Pizza");
		btnUpdatePizza.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final PizzaDetails pizzaDetails = new PizzaDetails();
				pizzaDetails.setPizzaName(txtPizzaName.getText());
				pizzaDetails.setPizzaDescription(txtrPizzaDescription.getText());
				pizzaDetails.setPizzaContent(txtrPizzaContents.getText());
				pizzaDetails.setActive(chckbxPizzaActive.isSelected());
				new Thread() {
					public void run() {
						pizzaOrderController.updatePizzaDetails(pizzaDetails);
					}
				}.start();
				lblPizzaDetailsMessageLabel
						.setText("Please Wait .. Pizza Details are getting updated");
				btnSavePizza.setEnabled(false);
				btnSearchPizza.setEnabled(false);
				btnUpdatePizza.setEnabled(false);
			}
		});
		GridBagConstraints gbc_btnUpdatePizza = new GridBagConstraints();
		gbc_btnUpdatePizza.fill = GridBagConstraints.BOTH;
		gbc_btnUpdatePizza.insets = new Insets(0, 0, 5, 0);
		gbc_btnUpdatePizza.gridx = 2;
		gbc_btnUpdatePizza.gridy = 0;
		panel_6.add(btnUpdatePizza, gbc_btnUpdatePizza);

		lblPizzaDetailsMessageLabel = new JLabel("");
		lblPizzaDetailsMessageLabel
				.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblPizzaDetailsMessageLabel = new GridBagConstraints();
		gbc_lblPizzaDetailsMessageLabel.insets = new Insets(0, 0, 0, 5);
		gbc_lblPizzaDetailsMessageLabel.fill = GridBagConstraints.BOTH;
		gbc_lblPizzaDetailsMessageLabel.gridwidth = 3;
		gbc_lblPizzaDetailsMessageLabel.gridx = 0;
		gbc_lblPizzaDetailsMessageLabel.gridy = 1;
		panel_6.add(lblPizzaDetailsMessageLabel,
				gbc_lblPizzaDetailsMessageLabel);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null,
				null, null));
		tabbedPane.addTab("Pizza Toppings", null, panel_2, null);
	}

	@Override
	public void update(Observable o, Object arg) {

		if (arg instanceof UserDetails) {

			UserDetails userDetails = (UserDetails) arg;
			txtTelephoneNumber.setText(userDetails.getTelephoneNumber());
			txtNameOfPerson.setText(userDetails.getUserName());
			txtrAddress.setText(userDetails.getUserAddress());
			lblUserDetailsMessageLabel.setText(userDetails.getMessage());
			btnSaveDetails.setEnabled(true);
			btnSearchDetails.setEnabled(true);
			btnUpdateDetails.setEnabled(true);

		} else if (arg instanceof PizzaDetails) {

			PizzaDetails pizzaDetails = (PizzaDetails) arg;
			txtPizzaName.setText(pizzaDetails.getPizzaName());
			txtrPizzaDescription.setText(pizzaDetails.getPizzaDescription());
			txtrPizzaContents.setText(pizzaDetails.getPizzaContent());
			chckbxPizzaActive
					.setSelected(pizzaDetails.isActive() != null ? pizzaDetails
							.isActive() : false);
			lblPizzaDetailsMessageLabel.setText(pizzaDetails.getMessage());
			btnSearchPizza.setEnabled(true);
			btnSavePizza.setEnabled(true);
			btnUpdatePizza.setEnabled(true);

		}
	}
}
