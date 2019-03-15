package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;

import controller.EditTestCaseController;
import controller.ListTestCasesController;
import controller.MainPageController;
import database.DatabaseConnection;
import database.EditTestCaseQueries;
import model.TestCase;

public class ListTestCasesResults {

	public DatabaseConnection databaseConnection;

	public JFrame frame;
	private JTable table;

	private JButton btnHome;
	private MainPageController mainPageController;

	public ListTestCasesController testCaseSelectionController;
	private EditTestCaseController editTestCaseController;

	public EditTestCaseQueries editTestCaseQueries;

	public EditTestCasePage editTestCasePage;

	/**
	 * Launch the application.
	 */

	public void initComponents() {
		databaseConnection = new DatabaseConnection();

		table = new JTable();

		testCaseSelectionController = new ListTestCasesController();
		mainPageController = new MainPageController();
		editTestCaseController = new EditTestCaseController();

		editTestCaseQueries = new EditTestCaseQueries();
		editTestCasePage = new EditTestCasePage();
	}

	/**
	 * Create the application.
	 */
	public ListTestCasesResults() {
		initComponents();

		databaseConnection.connect();
	}

	public void listAllCases() {
		this.fillTable();
		initialize();
		setUpListeners();
	}

	public void listContextCases(String context) {
		this.fillContextTable(context);
		initialize();
		setUpListeners();
	}

	public void listPriorityCases(String priority) {
		this.fillPriorityTable(priority);
		initialize();
		setUpListeners();
	}

	public void listProbabilityCases(String probability) {
		this.fillProbabilityTable(probability);
		initialize();
		setUpListeners();
	}

	public void listComponentCases(String rcutype) {
		this.fillComponentTable(rcutype);
		initialize();
		setUpListeners();
	}

	public void listModelConstraintCases(String modelconstraint) {
		this.fillModelConstraintTable(modelconstraint);
		initialize();
		setUpListeners();
	}

	public void listCostCases(ArrayList<TestCase> testCaseList) {
		this.draw(testCaseList);
		initialize();
		setUpListeners();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		frame.setTitle("Test case results");

		JPanel jPanel = new JPanel();

		btnHome = new JButton("Home");
		btnHome.setBackground(Color.GREEN);
		btnHome.setPreferredSize(new Dimension(70, 20));
		jPanel.add(btnHome);
		// jPanel.setSize(0, 40);
		frame.getContentPane().add(jPanel, BorderLayout.NORTH);
		// frame.getContentPane().setLayout(null);
		// frame.setSize(300, 150);

		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);

		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		frame.setBounds(20, 20, 1600, 1000);

		TableColumn column = null;
		int modelconstraint = table.getColumnCount();

		for (int i = 0; i < modelconstraint; i++) {
			column = table.getColumnModel().getColumn(i);
			if (i == 0) {
				column.setPreferredWidth(4); // third column is bigger
			} else if (i == 1) {
				column.setPreferredWidth(4);
			} else if (i == 2) {
				column.setPreferredWidth(300);
			} else if (i == 3) {
				column.setPreferredWidth(500);
			} else {
				column.setPreferredWidth(120);
			}
		}

		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				sendId();
			}
		});

	}

	public void fillTable() {
		try {
			this.testCaseSelectionController.showAllResultCases(databaseConnection);
			this.table = new JTable(testCaseSelectionController.getData(),
					testCaseSelectionController.getColumnNames());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void fillContextTable(String context) {
		try {
			this.testCaseSelectionController.showContextCases(databaseConnection, context);
			this.table = new JTable(testCaseSelectionController.getData(),
					testCaseSelectionController.getColumnNames());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void fillPriorityTable(String priority) {
		try {
			this.testCaseSelectionController.showPriorityCases(databaseConnection, priority);
			this.table = new JTable(testCaseSelectionController.getData(),
					testCaseSelectionController.getColumnNames());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void fillProbabilityTable(String probability) {
		try {
			this.testCaseSelectionController.showProbabiltityCases(databaseConnection, probability);
			this.table = new JTable(testCaseSelectionController.getData(),
					testCaseSelectionController.getColumnNames());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void fillComponentTable(String rcutype) {
		try {
			this.testCaseSelectionController.showComponentCases(databaseConnection, rcutype);
			this.table = new JTable(testCaseSelectionController.getData(),
					testCaseSelectionController.getColumnNames());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void fillModelConstraintTable(String modelconstraint) {
		try {
			this.testCaseSelectionController.showModelConstraintCases(databaseConnection, modelconstraint);
			this.table = new JTable(testCaseSelectionController.getData(),
					testCaseSelectionController.getColumnNames());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void fillCostTable(String modelconstraint) {
		try {
			this.testCaseSelectionController.showModelConstraintCases(databaseConnection, modelconstraint);
			this.table = new JTable(testCaseSelectionController.getData(),
					testCaseSelectionController.getColumnNames());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void setUpListeners() {
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				mainPageController.runMainPage();
			}
		});
	}

	public void sendId() {
		frame.dispose();
		editTestCaseController.editTestCaseQueries.setDatabaseConnection(databaseConnection);
		// editTestCaseQueries.setDatabaseConnection(databaseConnection);
		editTestCaseController.setId(Integer.parseInt(table.getValueAt(table.getSelectedRow(), 1).toString()));
		// System.out.println(editTestCaseQueries.getId());
		editTestCasePage.setEditTestCaseController(editTestCaseController);
		editTestCaseController.runEditTestCasePage();
	}

	public void draw(ArrayList<TestCase> testCaseList) {
		Vector<String> columnNames;

		Vector data;
		data = new Vector();
		columnNames = new Vector();

		columnNames.addElement("Id");
		columnNames.addElement("Key");
		columnNames.addElement("Name");
		columnNames.addElement("Results");
		columnNames.addElement("Priority");
		columnNames.addElement("Execution Time");
		columnNames.addElement("Model");
		columnNames.addElement("Component");
		columnNames.addElement("Model Constraint");
		for (int i = 0; i < testCaseList.size(); i++) {
			Vector row = new Vector(testCaseList.size());

			row.add(0, testCaseList.get(i).getId());
			row.add(1, i + 1);
			row.add(2, testCaseList.get(i).getCaseName());
			row.add(3, testCaseList.get(i).getGoal());
			row.add(4, testCaseList.get(i).getPriority().getName());
			row.add(5, testCaseList.get(i).getTimeExecution());
			row.add(6, testCaseList.get(i).getModel().getName());
			row.add(7, testCaseList.get(i).getRcuType().getName());
			row.add(7, testCaseList.get(i).getModelConstraint().getName());

			data.addElement(row);
		}
		table = new JTable(data, columnNames);
	}

}
