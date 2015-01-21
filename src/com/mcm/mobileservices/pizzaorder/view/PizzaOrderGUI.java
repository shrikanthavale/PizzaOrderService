package com.mcm.mobileservices.pizzaorder.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
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

public class PizzaOrderGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtTelephoneNumber;
	private JTextField txtNameOfPerson;

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
		setTitle("Hagenberg - Pizza Order Service");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 661, 438);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		tabbedPane.addTab("Contact Details", null, panel, null);
		panel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblViewUpdate = new JLabel("View & Update Contact Details");
		lblViewUpdate.setHorizontalAlignment(SwingConstants.CENTER);
		lblViewUpdate.setFont(new Font("Tahoma", Font.BOLD, 30));
		panel.add(lblViewUpdate, BorderLayout.NORTH);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Contact Details", TitledBorder.LEADING, TitledBorder.TOP, null, null));
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
		
		JTextArea txtrAddress = new JTextArea();
		txtrAddress.setColumns(50);
		txtrAddress.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtrAddress.setRows(6);
		txtrAddress.setText("Address of Person");
		panel_3.add(txtrAddress);
		
		JPanel panel_4 = new JPanel();
		panel.add(panel_4, BorderLayout.SOUTH);
		panel_4.setLayout(new GridLayout(0, 5, 0, 0));
		
		JButton btnSearchDetails = new JButton("Search Details");
		panel_4.add(btnSearchDetails);
		
		JButton btnSaveDetails = new JButton("Save Details");
		panel_4.add(btnSaveDetails);
		
		JButton btnCancelDetails = new JButton("Cancel Details");
		panel_4.add(btnCancelDetails);
		
		JButton btnResetDetails = new JButton("Reset Details");
		panel_4.add(btnResetDetails);
		
		JButton btnDeletePerson = new JButton("Delete Details");
		panel_4.add(btnDeletePerson);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Pizza Details", null, panel_1, null);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		tabbedPane.addTab("Pizza Toppings", null, panel_2, null);
	}

}
