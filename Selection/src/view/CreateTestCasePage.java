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

import controller.MainPageController;
import controller.TestStepQueries;
import database.ConsequenceQueries;
import database.ContextQueries;
import database.CreateTestCaseQueries;
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
import model.TestCase;
import model.TestStep;

public class CreateTestCasePage implements ActionListener {

	public DatabaseConnection databaseConnection;

	public Priority priority;
	public Probability probability;
	public Effect effect;
	public Context context;
	public TestCase testCase;

	public PriorityQueries priorityQueries;
	public ProbabilityQueries probabilityQueries;
	public EffectQueries effectQueries;
	public ContextQueries contextQueries;
	public CreateTestCaseQueries createTestCaseQueries;
	private ModelQueries modelQueries;
	private ModelConstraintQueries modelConstraintQueries;
	private RCUTypeQueries rcuTypeQueries;
	private ConsequenceQueries consequenceQueries;

	private TestStepQueries testStepQueries;

	public JFrame frame;
	private JTextField textCaseName;
	private JTextArea textAreaGoal;

	public JLabel lblTestCaseNumber;

	private JButton btnSaveMain;
	private JComboBox comboBoxPriority, comboBoxProbability, comboBoxEffect, comboBoxContext, comboBoxModel,
			comboBoxComponent, comboBoxModelConstraint, comboBoxConsequence;
	private JLabel lblTestCaseNumber_1;

	private MainPageController mainPageController;
	private JTabbedPane tabbedPane;
	private JPanel panelHome;
	private JPanel panelDescription;
	private JLabel lblTestSteps;
	private JButton btnHome;
	private JButton btnAddNewStep;
	private JButton btnSaveDescription;

	private int caseId;

	private JTable table;

	private JPopupMenu popupMenu;

	private JMenuItem menuItemAdd;
	private JMenuItem menuItemRemove;
	private JMenuItem menuItemRemoveAll;

	private MyTableModel tableModel;

	private TestStep testStep;
	private JButton btnCancel;
	private JLabel lblPrecondition;
	private JTextArea textAreaPrecondition;
	private JLabel label;
	private JTextField textFieldTime;
	private JLabel label_1;
	private JLabel lblConsequence;
	private JButton btnSelectResource;

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
		createTestCaseQueries = new CreateTestCaseQueries();
		modelQueries = new ModelQueries();
		rcuTypeQueries = new RCUTypeQueries();
		modelConstraintQueries = new ModelConstraintQueries();
		consequenceQueries = new ConsequenceQueries();

		testStepQueries = new TestStepQueries();

		testCase = new TestCase();
		table = new JTable();

		lblTestCaseNumber = new JLabel();
		lblTestCaseNumber.setBounds(191, 43, 25, 14);

		textCaseName = new JTextField();
		textCaseName.setBounds(191, 76, 997, 23);
		mainPageController = new MainPageController();

		popupMenu = new JPopupMenu();
	}

	public CreateTestCasePage() {
		initComponents();

		databaseConnection.connect();
		this.fillLabel();

		fillTable();
		createPopUpMenu();
		initialize();

		this.fillComboBoxes();
		setUpListeners();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1234, 800);
		frame.setTitle("Create test case");

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		panelHome = new JPanel();
		// panelHome.setBounds(100, 100, 894, 671);
		panelHome.setLayout(null);
		JLabel lblTestCaseName = new JLabel("Test case name");
		lblTestCaseName.setBounds(24, 80, 92, 14);
		panelHome.add(lblTestCaseName);
		panelHome.add(textCaseName);
		textCaseName.setColumns(10);

		JLabel lblPriority = new JLabel("Priority");
		lblPriority.setBounds(24, 336, 46, 14);
		panelHome.add(lblPriority);

		btnSaveMain = new JButton("Save");

		btnSaveMain.setBounds(24, 686, 89, 23);
		panelHome.add(btnSaveMain);

		JLabel lblProbability = new JLabel("Probability");
		lblProbability.setBounds(24, 412, 75, 14);
		panelHome.add(lblProbability);

		JLabel lblEffect = new JLabel("Effect");
		lblEffect.setBounds(24, 492, 46, 14);
		panelHome.add(lblEffect);

		JLabel lblGoal = new JLabel("Goal");
		lblGoal.setBounds(24, 146, 46, 14);
		panelHome.add(lblGoal);

		JLabel lblContext = new JLabel("Context");
		lblContext.setBounds(24, 568, 46, 14);
		panelHome.add(lblContext);
		panelHome.add(lblTestCaseNumber);

		lblTestCaseNumber_1 = new JLabel("Test case number");
		lblTestCaseNumber_1.setBounds(24, 43, 108, 14);
		panelHome.add(lblTestCaseNumber_1);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 1228, 761);
		frame.getContentPane().add(tabbedPane);

		tabbedPane.addTab("Main", null, panelHome, null);

		textAreaGoal = new JTextArea();
		textAreaGoal.setBounds(191, 131, 997, 48);
		panelHome.add(textAreaGoal);

		comboBoxPriority = new JComboBox();
		comboBoxPriority.setBounds(191, 333, 223, 20);
		panelHome.add(comboBoxPriority);
		comboBoxProbability = new JComboBox();
		comboBoxProbability.setBounds(191, 409, 223, 20);
		panelHome.add(comboBoxProbability);
		comboBoxEffect = new JComboBox();
		comboBoxEffect.setBounds(191, 489, 223, 20);
		panelHome.add(comboBoxEffect);
		comboBoxContext = new JComboBox();
		comboBoxContext.setBounds(191, 565, 223, 20);
		panelHome.add(comboBoxContext);

		btnHome = new JButton("Home");
		btnHome.setBackground(Color.GREEN);
		btnHome.setBounds(10, 0, 89, 23);
		panelHome.add(btnHome);

		btnCancel = new JButton("Cancel");

		btnCancel.setBounds(191, 686, 89, 23);
		panelHome.add(btnCancel);

		JLabel lblModel = new JLabel("Model");
		lblModel.setBounds(658, 336, 46, 14);
		panelHome.add(lblModel);

		comboBoxModel = new JComboBox();
		comboBoxModel.setBounds(857, 333, 223, 20);
		panelHome.add(comboBoxModel);

		JLabel lblTestCaseComponent = new JLabel("Test case component");
		lblTestCaseComponent.setBounds(658, 412, 155, 14);
		panelHome.add(lblTestCaseComponent);

		comboBoxComponent = new JComboBox();
		comboBoxComponent.setBounds(857, 409, 223, 20);
		panelHome.add(comboBoxComponent);

		JLabel lblModelConstraint = new JLabel("Model constraint");
		lblModelConstraint.setBounds(658, 492, 97, 14);
		panelHome.add(lblModelConstraint);

		comboBoxModelConstraint = new JComboBox();
		comboBoxModelConstraint.setBounds(857, 489, 223, 20);
		panelHome.add(comboBoxModelConstraint);

		lblPrecondition = new JLabel("Precondition");
		lblPrecondition.setBounds(24, 238, 75, 14);
		panelHome.add(lblPrecondition);

		textAreaPrecondition = new JTextArea();
		textAreaPrecondition.setBounds(191, 214, 997, 48);
		panelHome.add(textAreaPrecondition);

		label = new JLabel("Execution time");
		label.setBounds(660, 568, 130, 14);
		panelHome.add(label);

		textFieldTime = new JTextField();
		textFieldTime.setText("0.0");
		textFieldTime.setColumns(10);
		textFieldTime.setBounds(857, 565, 115, 20);
		panelHome.add(textFieldTime);

		label_1 = new JLabel("hr");
		label_1.setBounds(982, 568, 46, 14);
		panelHome.add(label_1);

		lblConsequence = new JLabel("Consequence");
		lblConsequence.setBounds(24, 628, 92, 14);
		panelHome.add(lblConsequence);

		comboBoxConsequence = new JComboBox();
		comboBoxConsequence.setBounds(188, 625, 226, 20);
		panelHome.add(comboBoxConsequence);

		btnSelectResource = new JButton("Select resource");
		btnSelectResource.setBounds(851, 624, 229, 23);
		panelHome.add(btnSelectResource);

		JPanel jPanel = new JPanel();
		btnSaveDescription = new JButton("Save");
		btnSaveDescription.setBackground(Color.GREEN);
		btnSaveDescription.setPreferredSize(new Dimension(70, 20));
		jPanel.add(btnSaveDescription);

		panelDescription = new JPanel();
		tabbedPane.addTab("Description", null, panelDescription, null);
		panelDescription.setLayout(new BorderLayout());

		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		panelDescription.add(jPanel, BorderLayout.NORTH);

		TableColumn column = null;
		for (int i = 0; i < 6; i++) {
			column = table.getColumnModel().getColumn(i);
			if (i == 0) {
				column.setPreferredWidth(4); // third column is bigger
			} else {
				column.setPreferredWidth(200);
			}
		}
		// table.addMouseListener(new TableMouseListener(table));
		panelDescription.add(scrollPane, BorderLayout.CENTER);
		table.setComponentPopupMenu(popupMenu);
	}

	// Add test runs
	public void fillTable() {
		try {
			this.testStepQueries.showDescription(databaseConnection, this.caseId);
			tableModel = new MyTableModel(testStepQueries.getData(), testStepQueries.getColumnNames());
			this.table = new JTable(tableModel);

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

	public void fillComboBoxes() {
		try {
			this.priorityQueries.addPriority(databaseConnection);
			Iterator<Priority> priorityIterator = this.priorityQueries.getPriority().iterator();
			this.comboBoxPriority.addItem("");
			while (priorityIterator.hasNext()) {
				this.comboBoxPriority.addItem(priorityIterator.next().getName());
				priorityIterator.remove();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			this.probabilityQueries.addProbability(databaseConnection);
			Iterator<Probability> probabilityIterator = this.probabilityQueries.getProbability().iterator();
			this.comboBoxProbability.addItem("");
			while (probabilityIterator.hasNext()) {
				this.comboBoxProbability.addItem(probabilityIterator.next().getName());
				probabilityIterator.remove();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			this.effectQueries.addEffect(databaseConnection);
			Iterator<Effect> effectIterator = this.effectQueries.getEffect().iterator();
			this.comboBoxEffect.addItem("");
			while (effectIterator.hasNext()) {
				this.comboBoxEffect.addItem(effectIterator.next().getName());
				effectIterator.remove();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			this.contextQueries.addContext(databaseConnection);
			Iterator<Context> contextIterator = this.contextQueries.getContext().iterator();
			this.comboBoxContext.addItem("");
			while (contextIterator.hasNext()) {
				this.comboBoxContext.addItem(contextIterator.next().getName());
				contextIterator.remove();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			this.modelQueries.addModel(databaseConnection);
			Iterator<Model> modelIterator = this.modelQueries.getModel().iterator();
			this.comboBoxModel.addItem("");
			while (modelIterator.hasNext()) {
				this.comboBoxModel.addItem(modelIterator.next().getName());
				modelIterator.remove();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			this.rcuTypeQueries.addRCUType(databaseConnection);
			Iterator<RCUType> rcuTypeIterator = this.rcuTypeQueries.getRCUType().iterator();
			this.comboBoxComponent.addItem("");
			while (rcuTypeIterator.hasNext()) {
				this.comboBoxComponent.addItem(rcuTypeIterator.next().getName());
				rcuTypeIterator.remove();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			this.modelConstraintQueries.addModelConstraint(databaseConnection);
			Iterator<ModelConstraint> modelConstraintIterator = this.modelConstraintQueries.getModelConstraint()
					.iterator();
			this.comboBoxModelConstraint.addItem("");
			while (modelConstraintIterator.hasNext()) {
				this.comboBoxModelConstraint.addItem(modelConstraintIterator.next().getName());
				modelConstraintIterator.remove();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			this.consequenceQueries.addConsequence(databaseConnection);
			Iterator<Consequence> consequenceIterator = this.consequenceQueries.getConsequence().iterator();
			this.comboBoxConsequence.addItem("");
			while (consequenceIterator.hasNext()) {
				this.comboBoxConsequence.addItem(consequenceIterator.next().getName());
				consequenceIterator.remove();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void fillLabel() {
		try {
			this.createTestCaseQueries.addLastCaseNumber(databaseConnection);

			caseId = createTestCaseQueries.lastCaseNumber + 1;
			this.lblTestCaseNumber.setText(Integer.toString(caseId));

		} catch (Exception e) {
			e.printStackTrace();
		}

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
		String caseNameTemp = textCaseName.getText();
		System.out.println(caseNameTemp);
		testCase.setCaseName(caseNameTemp);

		// Get data from text area
		String goalTemp = textAreaGoal.getText();
		testCase.setGoal(goalTemp);

		// Add consequence
		Consequence consequenceTemp = new Consequence();
		consequenceTemp.setName(comboBoxConsequence.getSelectedItem().toString());
		testCase.setConsequence(consequenceTemp);

		// Add model
		Model modelTemp = new Model();
		modelTemp.setName(comboBoxModel.getSelectedItem().toString());
		testCase.setModel(modelTemp);

		// Add componentTemp
		RCUType componentTemp = new RCUType();
		componentTemp.setName(comboBoxComponent.getSelectedItem().toString());
		testCase.setRcuType(componentTemp);

		// Add modelconstraint
		ModelConstraint modelConstraintTemp = new ModelConstraint();
		modelConstraintTemp.setName(comboBoxModelConstraint.getSelectedItem().toString());
		testCase.setModelConstraint(modelConstraintTemp);

		// Get data from text field
		String timeTemp = textFieldTime.getText();
		testCase.setTimeExecution(Double.parseDouble(timeTemp));

	}

	private void addDescription() {
		TestStep[] testStep;

		int numberOfRows = table.getRowCount();

		testStep = new TestStep[numberOfRows];
		System.out.println(numberOfRows);
		for (int i = 0; i < numberOfRows; i++) {
			testStep[i] = new TestStep();
			testStep[i].setStepId(Integer.parseInt((table.getValueAt(i, 0).toString())));

			System.out.println(tableModel.getValueAt(0, 0).toString());
			// System.out.println((table.getModel().getValueAt(0, 2).toString()));
		}

	}

	public void setUpListeners() {
		btnSaveMain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addItem();
				createTestCaseQueries.savePage(databaseConnection, testCase);
				frame.dispose();
				mainPageController.runMainPage();

			}
		});

		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				mainPageController.runMainPage();
			}
		});

		// Save for the description tab
		btnSaveDescription.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Save the first page first
				// addItem();
				// createTestCaseQueries.savePage(databaseConnection, testCase);

				// Save the description page now
				addDescription();
				// testStepQueries.saveDescription(databaseConnection, testStep, caseId );
			}
		});

		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				mainPageController.runMainPage();
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
		tableModel.addRow(new String[0]);
	}

	private void removeCurrentRow() {
		int selectedRow = table.getSelectedRow();
		tableModel.removeRow(selectedRow);
	}

	private void removeAllRows() {
		int rowCount = tableModel.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			tableModel.removeRow(0);
		}
	}
}
