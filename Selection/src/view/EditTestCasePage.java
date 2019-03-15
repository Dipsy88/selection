package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;

import controller.AddNewTestRunController;
import controller.EditTestCaseController;
import controller.MainPageController;
import controller.TestRunController;
import controller.TestStepQueries;
import database.ConsequenceQueries;
import database.ContextQueries;
import database.DatabaseConnection;
import database.EffectQueries;
import database.ModelConstraintQueries;
import database.ModelQueries;
import database.PriorityQueries;
import database.ProbabilityQueries;
import database.RCUTypeQueries;
import model.Consequence;
import model.Context;
import model.Effect;
import model.Model;
import model.ModelConstraint;
import model.MyTableModel;
import model.Priority;
import model.Probability;
import model.RCUType;
import model.Result;
import model.TestCase;
import model.TestStep;

public class EditTestCasePage implements ActionListener {

	public DatabaseConnection databaseConnection;

	public Priority priority;
	public Probability probability;
	public Effect effect;
	public Context context;
	private TestCase testCase;
	private TestRunController testRunController;
	private RCUType rcuType;
	private ModelConstraint modelConstraint;
	private Consequence consequence;

	private MyTableModel tableModelDescription;

	public TestCase getTestCase() {
		return testCase;
	}

	public void setTestCase(TestCase testCase) {
		this.testCase = testCase;
	}

	public PriorityQueries priorityQueries;
	public ProbabilityQueries probabilityQueries;
	public EffectQueries effectQueries;
	public ContextQueries contextQueries;
	private EditTestCaseController editTestCaseController;
	private ModelQueries modelQueries;
	private ModelConstraintQueries modelConstraintQueries;
	private RCUTypeQueries rcuTypeQueries;
	public ConsequenceQueries consequenceQueries;

	private TestStepQueries testStepQueries;

	public EditTestCaseController getEditTestCaseController() {
		return editTestCaseController;
	}

	public void setEditTestCaseController(EditTestCaseController editTestCaseController) {
		this.editTestCaseController = editTestCaseController;
	}

	public JFrame frame;
	private JTextField textCaseName, textTime;
	private JTextArea textAreaGoal;

	public JLabel lblTestCaseNumber;

	private JButton btnSaveMain, btnSelectResource;
	private JComboBox comboBoxPriority, comboBoxProbability, comboBoxEffect, comboBoxContext, comboBoxModel,
			comboBoxComponent, comboBoxConsequence, comboBoxModelConstraint;
	private JLabel lblTestCaseNumber_1, lblConsequence;

	private MainPageController mainPageController;
	private AddNewTestRunController addNewTestRunController;

	private JTabbedPane tabbedPane;
	private JPanel panelHome;
	private JPanel panelDescription;
	private JLabel lblTestSteps;
	private JPanel panelTestRun;
	private JButton btnHome;
	private JButton btnNewResult;
	private JButton btnHome_tab;

	private JButton btnCheckTestCases;

	private JButton btnSave_2;

	private JTable tableDescription;
	private JTable tableResult;
	private JButton btnCancel;

	private JPopupMenu popupMenu;

	private JMenuItem menuItemAdd;
	private JMenuItem menuItemRemove;
	private JMenuItem menuItemRemoveAll;
	private JLabel lblExecutionTime;
	private JLabel label;
	private JTextArea textArea;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */

	public void initComponents() {
		databaseConnection = new DatabaseConnection();
		priorityQueries = new PriorityQueries();
		probabilityQueries = new ProbabilityQueries();
		effectQueries = new EffectQueries();
		contextQueries = new ContextQueries();
		editTestCaseController = new EditTestCaseController();
		modelQueries = new ModelQueries();
		rcuTypeQueries = new RCUTypeQueries();
		modelConstraintQueries = new ModelConstraintQueries();
		consequenceQueries = new ConsequenceQueries();

		testRunController = new TestRunController();
		addNewTestRunController = new AddNewTestRunController();
		testStepQueries = new TestStepQueries();

		tableDescription = new JTable();
		tableResult = new JTable();

		lblTestCaseNumber = new JLabel();
		lblTestCaseNumber.setBounds(191, 37, 25, 14);

		textCaseName = new JTextField();
		textCaseName.setBounds(191, 77, 899, 20);
		mainPageController = new MainPageController();

		popupMenu = new JPopupMenu();
	}

	public EditTestCasePage() {
		testCase = new TestCase();
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	public void run() {
		initComponents();

		databaseConnection.connect();
		this.fillTable();
		this.filllabel();
		createPopUpMenu();
		initialize();

		this.fillComboBoxes();

		addItem();
		setUpListeners();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1234, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Edit test case");
		frame.getContentPane().setLayout(null);

		panelHome = new JPanel();

		panelHome.setBounds(100, 100, 894, 671);
		panelHome.setLayout(null);
		JLabel lblTestCaseName = new JLabel("Test case name");
		lblTestCaseName.setBounds(24, 80, 92, 14);
		panelHome.add(lblTestCaseName);
		panelHome.add(textCaseName);
		textCaseName.setColumns(10);

		JLabel lblPriority = new JLabel("Priority");
		lblPriority.setBounds(24, 333, 46, 14);
		panelHome.add(lblPriority);

		comboBoxConsequence = new JComboBox();
		comboBoxConsequence.setBounds(188, 625, 226, 20);
		panelHome.add(comboBoxConsequence);

		btnSelectResource = new JButton("Select resource");
		btnSelectResource.setBounds(861, 624, 229, 23);
		panelHome.add(btnSelectResource);

		btnSaveMain = new JButton("Save");

		btnSaveMain.setBounds(24, 686, 89, 23);
		panelHome.add(btnSaveMain);

		JLabel lblProbability = new JLabel("Probability");
		lblProbability.setBounds(24, 407, 75, 14);
		panelHome.add(lblProbability);

		JLabel lblEffect = new JLabel("Effect");
		lblEffect.setBounds(24, 495, 46, 14);
		panelHome.add(lblEffect);

		JLabel lblGoal = new JLabel("Goal");
		lblGoal.setBounds(24, 136, 46, 14);
		panelHome.add(lblGoal);

		JLabel lblContext = new JLabel("Context");
		lblContext.setBounds(24, 560, 46, 14);
		panelHome.add(lblContext);
		panelHome.add(lblTestCaseNumber);

		lblTestCaseNumber_1 = new JLabel("Test case number");
		lblTestCaseNumber_1.setBounds(24, 37, 108, 14);
		panelHome.add(lblTestCaseNumber_1);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 1228, 761);
		frame.getContentPane().add(tabbedPane);

		tabbedPane.addTab("Main", null, panelHome, null);

		textAreaGoal = new JTextArea();
		textAreaGoal.setBounds(191, 131, 899, 48);
		panelHome.add(textAreaGoal);

		comboBoxPriority = new JComboBox();
		comboBoxPriority.setBounds(191, 327, 223, 20);
		panelHome.add(comboBoxPriority);
		comboBoxProbability = new JComboBox();
		comboBoxProbability.setBounds(191, 404, 223, 20);
		panelHome.add(comboBoxProbability);
		comboBoxEffect = new JComboBox();
		comboBoxEffect.setBounds(191, 477, 223, 20);
		panelHome.add(comboBoxEffect);
		comboBoxContext = new JComboBox();
		comboBoxContext.setBounds(191, 557, 223, 20);
		panelHome.add(comboBoxContext);
		JLabel lblModel = new JLabel("Model");
		lblModel.setBounds(671, 333, 46, 14);
		panelHome.add(lblModel);

		comboBoxModel = new JComboBox();
		comboBoxModel.setBounds(867, 330, 223, 20);
		panelHome.add(comboBoxModel);

		JLabel lblTestCaseComponent = new JLabel("Test case component");
		lblTestCaseComponent.setBounds(671, 407, 155, 14);
		panelHome.add(lblTestCaseComponent);

		comboBoxComponent = new JComboBox();
		comboBoxComponent.setBounds(867, 404, 223, 20);
		panelHome.add(comboBoxComponent);

		JLabel lblModelConstraint = new JLabel("Model constraint");
		lblModelConstraint.setBounds(671, 480, 97, 14);
		panelHome.add(lblModelConstraint);

		comboBoxModelConstraint = new JComboBox();
		comboBoxModelConstraint.setBounds(861, 477, 223, 20);
		panelHome.add(comboBoxModelConstraint);

		btnHome = new JButton("Home");

		btnHome.setBackground(Color.GREEN);
		btnHome.setBounds(24, 3, 89, 23);
		panelHome.add(btnHome);

		btnCancel = new JButton("Cancel");

		btnCancel.setBounds(191, 686, 89, 23);
		panelHome.add(btnCancel);

		lblExecutionTime = new JLabel("Execution time");
		lblExecutionTime.setBounds(671, 549, 130, 14);
		panelHome.add(lblExecutionTime);

		textTime = new JTextField();
		textTime.setBounds(861, 546, 115, 20);
		panelHome.add(textTime);
		textTime.setColumns(10);

		JLabel lblHr = new JLabel("hr");
		lblHr.setBounds(986, 549, 46, 14);
		panelHome.add(lblHr);

		lblConsequence = new JLabel("Consequence");
		lblConsequence.setBounds(24, 628, 92, 14);
		panelHome.add(lblConsequence);

		btnCheckTestCases = new JButton("Check test cases");
		btnCheckTestCases.setBackground(Color.GREEN);

		btnCheckTestCases.setBounds(114, 3, 166, 23);
		panelHome.add(btnCheckTestCases);

		label = new JLabel("Precondition");
		label.setBounds(24, 229, 75, 14);
		panelHome.add(label);

		textArea = new JTextArea();
		textArea.setBounds(191, 212, 997, 48);
		panelHome.add(textArea);

		panelDescription = new JPanel();
		tabbedPane.addTab("Description", null, panelDescription, null);
		panelDescription.setLayout(new BorderLayout(0, 0));

		lblTestSteps = new JLabel("Test steps");
		panelDescription.add(lblTestSteps);

		panelTestRun = new JPanel();
		tabbedPane.addTab("Test runs", null, panelTestRun, null);
		panelTestRun.setLayout(new BorderLayout(0, 0));

		JPanel jPanelDescription = new JPanel();
		btnSave_2 = new JButton("Save");

		btnSave_2.setBackground(Color.GREEN);
		btnSave_2.setPreferredSize(new Dimension(70, 20));
		jPanelDescription.add(btnSave_2);

		TableColumn columnDescription = null;
		for (int i = 0; i < 6; i++) {
			columnDescription = tableDescription.getColumnModel().getColumn(i);
			if (i == 0) {
				columnDescription.setPreferredWidth(4); // third column is
														// bigger
			} else {
				columnDescription.setPreferredWidth(200);
			}
		}

		JScrollPane scrollPaneDescription = new JScrollPane(tableDescription);
		tableDescription.setFillsViewportHeight(true);
		panelDescription.add(jPanelDescription, BorderLayout.NORTH);

		// table.addMouseListener(new TableMouseListener(table));
		panelDescription.add(scrollPaneDescription, BorderLayout.CENTER);
		tableDescription.setComponentPopupMenu(popupMenu);

		JPanel jPanelRuns = new JPanel();

		btnHome_tab = new JButton("Home");

		btnHome_tab.setBackground(Color.GREEN);
		btnHome_tab.setPreferredSize(new Dimension(70, 20));
		jPanelRuns.add(btnHome_tab);

		JScrollPane scrollPaneResult = new JScrollPane(tableResult);
		tableResult.setFillsViewportHeight(true);
		panelTestRun.add(jPanelRuns, BorderLayout.NORTH);

		TableColumn columnResult = null;
		for (int i = 0; i < 4; i++) {
			columnResult = tableResult.getColumnModel().getColumn(i);
			if (i == 0) {
				columnResult.setPreferredWidth(4); // third column is bigger
			} else {
				columnResult.setPreferredWidth(200);
			}
		}

		panelTestRun.add(scrollPaneResult, BorderLayout.CENTER);

		btnNewResult = new JButton("Add  new test result");
		btnNewResult.setBackground(Color.GREEN);
		btnNewResult.setPreferredSize(new Dimension(180, 20));
		jPanelRuns.add(btnNewResult);
		panelTestRun.add(jPanelRuns, BorderLayout.NORTH);
	}

	// Add test runs
	public void fillTable() {
		try {
			this.testStepQueries.showDescription(databaseConnection, testCase.getId());
			tableModelDescription = new MyTableModel(testStepQueries.getData(), testStepQueries.getColumnNames());
			this.tableDescription = new JTable(tableModelDescription);

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			this.testRunController.showRuns(databaseConnection, testCase.getId());
			this.tableResult = new JTable(testRunController.getData(), testRunController.getColumnNames());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void createPopUpMenu() {
		menuItemAdd = new JMenuItem("Add New Row");
		menuItemRemove = new JMenuItem("Remove Current Row");
		menuItemRemoveAll = new JMenuItem("Remove All Rows");

		menuItemAdd.addActionListener(this);
		menuItemRemove.addActionListener(this);
		menuItemRemoveAll.addActionListener(this);

		popupMenu.add(menuItemAdd);
		popupMenu.add(menuItemRemove);
		popupMenu.add(menuItemRemoveAll);

	}

	// Fill the combo boxes
	public void fillComboBoxes() {
		priority = testCase.getPriority();
		probability = testCase.getProbability();
		effect = testCase.getEffect();
		context = testCase.getContext();
		rcuType = testCase.getRcuType();
		modelConstraint = testCase.getModelConstraint();
		consequence = testCase.getConsequence();

		int index = 0;
		boolean itemCheck = true;
		if (priority.getName() == null) {
			this.comboBoxPriority.addItem("");
			itemCheck = false;
		}
		try {
			this.priorityQueries.addPriority(databaseConnection);
			Iterator<Priority> priorityIterator = this.priorityQueries.getPriority().iterator();
			while (priorityIterator.hasNext()) {
				String tempPriority = priorityIterator.next().getName();

				if (itemCheck == true) {
					if (priority.getName().equalsIgnoreCase(tempPriority))
						index = comboBoxPriority.getItemCount();
				}

				this.comboBoxPriority.addItem(tempPriority);
				priorityIterator.remove();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (index != 0) {
			this.comboBoxPriority.setSelectedIndex(index);
			index = 0;
		}
		// this.comboBoxPriority.removeItem(priority.getName());
		itemCheck = true;
		if (probability.getName() == null) {
			this.comboBoxProbability.addItem("");
			itemCheck = false;
		}
		try {
			this.probabilityQueries.addProbability(databaseConnection);
			Iterator<Probability> probabilityIterator = this.probabilityQueries.getProbability().iterator();

			while (probabilityIterator.hasNext()) {
				String tempProbability = probabilityIterator.next().getName();

				if (itemCheck == true) {
					if (probability.getName().equalsIgnoreCase(tempProbability))
						index = comboBoxProbability.getItemCount();
				}
				this.comboBoxProbability.addItem(tempProbability);
				probabilityIterator.remove();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (index != 0) {
			this.comboBoxProbability.setSelectedIndex(index);
			index = 0;
		}

		// consequence
		itemCheck = true;
		if (consequence.getName() == null) {
			this.comboBoxConsequence.addItem("");
			itemCheck = false;
		}
		try {
			this.consequenceQueries.addConsequence(databaseConnection);
			Iterator<Consequence> consequenceIterator = this.consequenceQueries.getConsequence().iterator();

			while (consequenceIterator.hasNext()) {
				String tempConsequence = consequenceIterator.next().getName();

				if (itemCheck == true) {
					if (consequence.getName().equalsIgnoreCase(tempConsequence))
						index = comboBoxConsequence.getItemCount();
				}
				this.comboBoxConsequence.addItem(tempConsequence);
				consequenceIterator.remove();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (index != 0) {
			this.comboBoxConsequence.setSelectedIndex(index);
			index = 0;
		}

		itemCheck = true;
		if (effect.getName() == null) {
			this.comboBoxEffect.addItem("");
			itemCheck = false;
		}
		try {
			this.effectQueries.addEffect(databaseConnection);
			Iterator<Effect> effectIterator = this.effectQueries.getEffect().iterator();
			while (effectIterator.hasNext()) {
				String tempEffect = effectIterator.next().getName();
				if (itemCheck == true) {
					if (itemCheck == true) {
						if (effect.getName().equalsIgnoreCase(tempEffect))
							index = comboBoxEffect.getItemCount();
					}
				}
				this.comboBoxEffect.addItem(tempEffect);
				effectIterator.remove();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (index != 0) {
			this.comboBoxEffect.setSelectedIndex(index);
			index = 0;
		}

		itemCheck = true;
		if (context.getName() == null) {
			this.comboBoxContext.addItem("");
			itemCheck = false;
		}
		try {
			this.contextQueries.addContext(databaseConnection);
			Iterator<Context> contextIterator = this.contextQueries.getContext().iterator();

			while (contextIterator.hasNext()) {
				String tempContext = contextIterator.next().getName();
				if (itemCheck == true) {
					if (context.getName().equalsIgnoreCase(tempContext))
						index = comboBoxContext.getItemCount();
				}
				this.comboBoxContext.addItem(tempContext);
				contextIterator.remove();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (index != 0) {
			this.comboBoxContext.setSelectedIndex(index);
			index = 0;
		}

		try {
			this.modelQueries.addModel(databaseConnection);
			Iterator<Model> modelIterator = this.modelQueries.getModel().iterator();

			while (modelIterator.hasNext()) {
				this.comboBoxModel.addItem(modelIterator.next().getName());
				modelIterator.remove();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		itemCheck = true;
		if (rcuType.getName() == null) {
			this.comboBoxComponent.addItem("");
			itemCheck = false;
		}
		try {
			this.rcuTypeQueries.addRCUType(databaseConnection);
			Iterator<RCUType> rcuTypeIterator = this.rcuTypeQueries.getRCUType().iterator();

			while (rcuTypeIterator.hasNext()) {
				String tempRCUType = rcuTypeIterator.next().getName();
				if (itemCheck == true) {
					if (rcuType.getName().equalsIgnoreCase(tempRCUType))
						index = comboBoxComponent.getItemCount();
				}
				this.comboBoxComponent.addItem(tempRCUType);
				rcuTypeIterator.remove();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (index != 0) {
			this.comboBoxComponent.setSelectedIndex(index);
			index = 0;
		}

		itemCheck = true;
		if (modelConstraint.getName() == null) {
			this.comboBoxModelConstraint.addItem("");
			itemCheck = false;
		}
		try {
			this.modelConstraintQueries.addModelConstraint(databaseConnection);
			Iterator<ModelConstraint> modelConstraintIterator = this.modelConstraintQueries.getModelConstraint()
					.iterator();

			while (modelConstraintIterator.hasNext()) {
				String tempModelConstraint = modelConstraintIterator.next().getName();
				if (itemCheck == true) {
					if (modelConstraint.getName() != null
							&& modelConstraint.getName().equalsIgnoreCase(tempModelConstraint))
						index = comboBoxComponent.getItemCount();
				}
				this.comboBoxModelConstraint.addItem(tempModelConstraint);
				modelConstraintIterator.remove();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// if (index != 0) {
		// this.comboBoxModelConstraint.setSelectedIndex(index);
		// index = 0;
		// }

	}

	public void filllabel() {
		this.lblTestCaseNumber.setText(Integer.toString(testCase.getId()));

	}

	public void addItem() {
		// Add priority
		Priority priorityTemp = new Priority();
		priorityTemp.setName(comboBoxPriority.getSelectedItem().toString());
		testCase.setPriority(priorityTemp);

		// Add probability
		Probability probabilityTemp = new Probability();
		probabilityTemp.setName(comboBoxProbability.getSelectedItem().toString());
		testCase.setProbability(probabilityTemp);

		// Add Effect
		Effect effectTemp = new Effect();
		effectTemp.setName(comboBoxEffect.getSelectedItem().toString());
		testCase.setEffect(effectTemp);

		// Add context
		Context contextTemp = new Context();
		contextTemp.setName(comboBoxContext.getSelectedItem().toString());
		testCase.setContext(contextTemp);

		// Get data from text field
		textCaseName.setText(testCase.getCaseName());

		String caseNameTemp = textCaseName.getText();
		testCase.setCaseName(caseNameTemp);

		// Get data from text area
		textAreaGoal.setText(testCase.getGoal());
		String goalTemp = textAreaGoal.getText();
		testCase.setGoal(goalTemp);

		// Get execution time
		textTime.setText(String.valueOf(testCase.getTimeExecution()));
		String timeTemp = textTime.getText();
		try {
			testCase.setTimeExecution(Double.parseDouble(timeTemp));
		} catch (Exception e) {

		}
	}

	public void setUpListeners() {
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				mainPageController.runMainPage();
			}
		});

		btnHome_tab.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				mainPageController.runMainPage();
			}
		});

		btnNewResult.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addNewTestRunController.setDatabaseConnection(databaseConnection);
				addNewTestRunController.runAddNewTestRun();
			}
		});

		btnSaveMain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});

		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				mainPageController.runMainPage();
			}
		});

		btnSave_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int length = addDescription().length;
				TestStep[] testSteps = new TestStep[length];
				testSteps = addDescription();

			}
		});

		btnCheckTestCases.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				mainPageController.runListCasesPage();

			}
		});

	}

	public void actionPerformed(ActionEvent event) {
		JMenuItem menu = (JMenuItem) event.getSource();
		if (menu == menuItemAdd) {
			addNewRow();
		} else if (menu == menuItemRemove) {
			removeCurrentRow();
		} else if (menu == menuItemRemoveAll) {
			removeAllRows();
		}
	}

	private void addNewRow() {
		tableModelDescription.addRow(new String[0]);

	}

	private void removeCurrentRow() {
		int selectedRow = tableDescription.getSelectedRow();
		tableModelDescription.removeRow(selectedRow);
	}

	private void removeAllRows() {
		int rowCount = tableDescription.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			tableModelDescription.removeRow(0);
		}
	}

	private TestStep[] addDescription() {
		TestStep[] testStep;

		int numberOfRows = tableDescription.getRowCount();

		testStep = new TestStep[numberOfRows];
		System.out.println(numberOfRows);
		for (int i = 0; i < numberOfRows; i++) {
			Result result = new Result();

			testStep[i] = new TestStep();
			testStep[i].setStepId(Integer.parseInt((tableDescription.getValueAt(i, 0).toString())));
			testStep[i].setDescription(tableDescription.getValueAt(i, 1).toString());
			testStep[i].setExpectedResult(tableDescription.getValueAt(i, 2).toString());
			result.setName(tableDescription.getValueAt(i, 3).toString());
			testStep[i].setResult(result);
			Object object = new Object();
			object = new String("");
			// System.out.println("Printing" + tableDescription.getValueAt(i,
			// 4).equals(object));
			if (!tableDescription.getValueAt(i, 4).equals(object))
				testStep[i].setResultValue(Double.parseDouble(tableDescription.getValueAt(i, 4).toString()));
			testStep[i].setComment(tableDescription.getValueAt(i, 5).toString());

		}
		return testStep;

	}
}
