package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.MainPageController;
import database.ContextQueries;
import database.DatabaseConnection;
import database.EffectQueries;
import database.ModelConstraintQueries;
import database.ModelQueries;
import database.OptimizeQueries;
import database.PriorityQueries;
import database.ProbabilityQueries;
import database.RCUTypeQueries;
import model.Context;
import model.Effect;
import model.ExperimentScheduling;
import model.ModelConstraint;
import model.RCUType;
import model.TestCase;

public class TestCaseSelectionPage {

	public DatabaseConnection databaseConnection;

	public JFrame frame;
	private JTextField textTime;

	public PriorityQueries priorityQueries;
	public ProbabilityQueries probabilityQueries;
	public EffectQueries effectQueries;
	public ContextQueries contextQueries;
	private ModelQueries modelQueries;
	private ModelConstraintQueries modelConstraintQueries;
	private RCUTypeQueries rcuTypeQueries;
	private OptimizeQueries optimizeQueries;

	private JButton btnCancel, btnDefaultConfiguration, btnOptimizeCases, btnOptimize, btnCalculateRisk;
	private JComboBox comboBoxEffect, comboBoxContext, comboBoxModel, comboBoxComponent, comboBoxModelConstraint;
	private JSlider sliderPriority, sliderProbability, sliderConsequence, sliderEPriority, sliderEProbability,
			sliderEConsequence, sliderRisk;
	private JCheckBox checkboxPriority, checkBoxProbability, checkBoxConsequence, checkBoxRisk, checkBoxEPriority,
			checkBoxEProbabilty, checkBoxEConsequence;

	final int MIN = 0;
	final int MAX = 100;
	final int FPS_INIT = 0;

	private MainPageController mainPageController;
	private SelectionTestCasesPage selectionTestCasesPage;
	private ListTestCasesPage listTestCasesPage;
	private ExperimentScheduling experimentScheduling;

	private JLabel lblConsequence;
	private JLabel lblEfficiencyPriority;
	private JLabel lblEfficiencyProbability;
	private JLabel lblEfficiencyConsequence;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestCaseSelectionPage window = new TestCaseSelectionPage();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TestCaseSelectionPage() {
		initComponents();

		databaseConnection.connect();
		initialize();

		this.fillComboBoxes();
		setUpListeners();
	}

	public void initComponents() {
		databaseConnection = new DatabaseConnection();
		priorityQueries = new PriorityQueries();
		probabilityQueries = new ProbabilityQueries();
		effectQueries = new EffectQueries();
		contextQueries = new ContextQueries();
		modelQueries = new ModelQueries();
		rcuTypeQueries = new RCUTypeQueries();
		modelConstraintQueries = new ModelConstraintQueries();
		optimizeQueries = new OptimizeQueries();

		mainPageController = new MainPageController();

		listTestCasesPage = new ListTestCasesPage();
		experimentScheduling = new ExperimentScheduling();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1212, 766);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Test Case Selection");

		JLabel lblExecutionTime = new JLabel("Execution Time");
		lblExecutionTime.setBounds(59, 70, 101, 14);
		frame.getContentPane().add(lblExecutionTime);

		textTime = new JTextField();
		textTime.setBounds(210, 67, 180, 20);
		frame.getContentPane().add(textTime);
		textTime.setColumns(10);

		JLabel lblHours = new JLabel("hours");
		lblHours.setBounds(400, 70, 46, 14);
		frame.getContentPane().add(lblHours);

		JLabel lblSelectTestCases = new JLabel("Select test cases based on the following choices:");
		lblSelectTestCases.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSelectTestCases.setBounds(59, 20, 375, 14);
		frame.getContentPane().add(lblSelectTestCases);

		JLabel label = new JLabel("Context");
		label.setBounds(757, 116, 46, 14);
		frame.getContentPane().add(label);

		comboBoxContext = new JComboBox();

		comboBoxContext.setBounds(956, 113, 172, 20);
		frame.getContentPane().add(comboBoxContext);

		JLabel label_1 = new JLabel("Priority");
		label_1.setBounds(58, 116, 46, 14);
		frame.getContentPane().add(label_1);

		JLabel label_2 = new JLabel("Probability");
		label_2.setBounds(59, 183, 75, 14);
		frame.getContentPane().add(label_2);

		JLabel label_3 = new JLabel("Component Under Test");
		label_3.setBounds(757, 260, 140, 14);
		frame.getContentPane().add(label_3);

		comboBoxComponent = new JComboBox();
		comboBoxComponent.setBounds(953, 257, 175, 20);
		frame.getContentPane().add(comboBoxComponent);

		JLabel label_4 = new JLabel("Feature");
		label_4.setBounds(757, 341, 97, 14);
		frame.getContentPane().add(label_4);

		comboBoxModelConstraint = new JComboBox();
		comboBoxModelConstraint.setBounds(953, 338, 175, 20);
		frame.getContentPane().add(comboBoxModelConstraint);

		btnCancel = new JButton("Cancel");

		btnCancel.setBounds(227, 674, 120, 23);
		frame.getContentPane().add(btnCancel);
		Font font = new Font("Serif", Font.ITALIC, 15);

		sliderPriority = new JSlider(JSlider.HORIZONTAL, MIN, MAX, FPS_INIT);
		sliderPriority.setEnabled(false);
		sliderPriority.setBounds(220, 100, 369, 77);
		frame.getContentPane().add(sliderPriority);

		sliderPriority.setMajorTickSpacing(25);
		sliderPriority.setMinorTickSpacing(5);
		sliderPriority.setPaintTicks(true);
		sliderPriority.setPaintLabels(true);
		sliderPriority.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		sliderPriority.setFont(font);

		Dictionary dictionaryPriority = new Hashtable();
		for (int i = 0; i <= 100; i += 25) {
			if (i / 25 == 0) {
				dictionaryPriority.put(i, new JLabel("Very Low"));
			} else if (i / 25 == 1) {
				dictionaryPriority.put(i, new JLabel("Low"));
			} else if (i / 25 == 2) {
				dictionaryPriority.put(i, new JLabel("Medium"));
			} else if (i / 25 == 3) {
				dictionaryPriority.put(i, new JLabel("High"));
			} else if (i / 25 == 4) {
				dictionaryPriority.put(i, new JLabel("Very High"));
			}
		}
		sliderPriority.setLabelTable(dictionaryPriority);

		sliderProbability = new JSlider(JSlider.HORIZONTAL, MIN, MAX, FPS_INIT);
		sliderProbability.setEnabled(false);
		sliderProbability.setBounds(227, 165, 362, 85);
		frame.getContentPane().add(sliderProbability);

		sliderProbability.setMajorTickSpacing(25);
		sliderProbability.setMinorTickSpacing(5);
		sliderProbability.setPaintTicks(true);
		sliderProbability.setPaintLabels(true);
		sliderProbability.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		sliderProbability.setFont(font);
		Dictionary dictionaryProbability = new Hashtable();
		for (int i = 0; i <= 100; i += 50) {
			if (i == 0) {
				dictionaryProbability.put(i, new JLabel("Low"));
			} else if (i / 50 == 1) {
				dictionaryProbability.put(i, new JLabel("Medium"));
			} else if (i / 50 == 2) {
				dictionaryProbability.put(i, new JLabel("High"));
			}
		}
		sliderProbability.setLabelTable(dictionaryProbability);

		btnDefaultConfiguration = new JButton("Default configuration");
		btnDefaultConfiguration.setBounds(442, 674, 175, 23);
		frame.getContentPane().add(btnDefaultConfiguration);

		JLabel lblEffect = new JLabel("Type of Tests");
		lblEffect.setBounds(757, 183, 127, 14);
		frame.getContentPane().add(lblEffect);

		comboBoxEffect = new JComboBox();
		comboBoxEffect.setBounds(953, 180, 175, 20);
		frame.getContentPane().add(comboBoxEffect);

		lblConsequence = new JLabel("Consequence");
		lblConsequence.setBounds(59, 260, 95, 14);
		frame.getContentPane().add(lblConsequence);

		sliderConsequence = new JSlider(JSlider.HORIZONTAL, MIN, MAX, FPS_INIT);
		sliderConsequence.setEnabled(false);
		sliderConsequence.setBounds(222, 235, 369, 96);
		frame.getContentPane().add(sliderConsequence);

		sliderConsequence.setMajorTickSpacing(25);
		sliderConsequence.setMinorTickSpacing(5);
		sliderConsequence.setPaintTicks(true);
		sliderConsequence.setPaintLabels(true);
		sliderConsequence.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		sliderConsequence.setFont(font);
		sliderConsequence.setLabelTable(dictionaryPriority);

		btnOptimizeCases = new JButton("Optimize cases");
		btnOptimizeCases.setBounds(631, 426, 140, 23);
		// frame.getContentPane().add(btnOptimizeCases);

		btnOptimize = new JButton("Optimize");
		btnOptimize.setBounds(24, 674, 120, 23);
		frame.getContentPane().add(btnOptimize);

		JLabel lblRisk = new JLabel("Risk");
		lblRisk.setBounds(59, 337, 46, 14);
		frame.getContentPane().add(lblRisk);

		sliderEPriority = new JSlider(SwingConstants.HORIZONTAL, MIN, MAX, FPS_INIT);
		sliderEPriority.setEnabled(false);
		sliderEPriority.setPaintTicks(true);
		sliderEPriority.setPaintLabels(true);
		sliderEPriority.setMinorTickSpacing(5);
		sliderEPriority.setMajorTickSpacing(25);
		sliderEPriority.setFont(new Font("Serif", Font.ITALIC, 15));
		sliderEPriority.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		sliderEPriority.setBounds(227, 404, 362, 77);
		frame.getContentPane().add(sliderEPriority);
		sliderEPriority.setLabelTable(dictionaryPriority);

		sliderEProbability = new JSlider(SwingConstants.HORIZONTAL, MIN, MAX, FPS_INIT);
		sliderEProbability.setEnabled(false);
		sliderEProbability.setPaintTicks(true);
		sliderEProbability.setPaintLabels(true);
		sliderEProbability.setMinorTickSpacing(5);
		sliderEProbability.setMajorTickSpacing(25);
		sliderEProbability.setFont(new Font("Serif", Font.ITALIC, 15));
		sliderEProbability.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		sliderEProbability.setBounds(230, 492, 359, 77);
		frame.getContentPane().add(sliderEProbability);
		sliderEProbability.setLabelTable(dictionaryProbability);

		sliderEConsequence = new JSlider(SwingConstants.HORIZONTAL, MIN, MAX, FPS_INIT);
		sliderEConsequence.setEnabled(false);
		sliderEConsequence.setPaintTicks(true);
		sliderEConsequence.setPaintLabels(true);
		sliderEConsequence.setMinorTickSpacing(5);
		sliderEConsequence.setMajorTickSpacing(25);
		sliderEConsequence.setFont(new Font("Serif", Font.ITALIC, 15));
		sliderEConsequence.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		sliderEConsequence.setBounds(227, 563, 362, 96);
		frame.getContentPane().add(sliderEConsequence);
		sliderEConsequence.setLabelTable(dictionaryPriority);

		lblEfficiencyPriority = new JLabel("Efficiency priority");
		lblEfficiencyPriority.setBounds(59, 417, 115, 14);
		frame.getContentPane().add(lblEfficiencyPriority);

		lblEfficiencyProbability = new JLabel("Efficiency probability");
		lblEfficiencyProbability.setBounds(59, 505, 127, 14);
		frame.getContentPane().add(lblEfficiencyProbability);

		lblEfficiencyConsequence = new JLabel("Efficiency consequence");
		lblEfficiencyConsequence.setBounds(59, 586, 140, 14);
		frame.getContentPane().add(lblEfficiencyConsequence);

		sliderRisk = new JSlider(SwingConstants.HORIZONTAL, MIN, MAX, FPS_INIT);
		sliderRisk.setEnabled(false);
		sliderRisk.setPaintTicks(true);
		sliderRisk.setPaintLabels(true);
		sliderRisk.setMinorTickSpacing(5);
		sliderRisk.setMajorTickSpacing(25);
		sliderRisk.setFont(new Font("Serif", Font.ITALIC, 15));
		sliderRisk.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		sliderRisk.setBounds(227, 312, 362, 96);
		frame.getContentPane().add(sliderRisk);
		sliderRisk.setLabelTable(dictionaryProbability);

		checkboxPriority = new JCheckBox("");
		checkboxPriority.setBounds(192, 110, 38, 23);
		frame.getContentPane().add(checkboxPriority);

		checkBoxEPriority = new JCheckBox("");
		checkBoxEPriority.setBounds(192, 410, 38, 23);
		frame.getContentPane().add(checkBoxEPriority);

		checkBoxProbability = new JCheckBox("");
		checkBoxProbability.setBounds(192, 175, 38, 23);
		frame.getContentPane().add(checkBoxProbability);

		checkBoxEProbabilty = new JCheckBox("");
		checkBoxEProbabilty.setBounds(192, 499, 38, 23);
		frame.getContentPane().add(checkBoxEProbabilty);

		checkBoxConsequence = new JCheckBox("");
		checkBoxConsequence.setBounds(192, 253, 38, 23);
		frame.getContentPane().add(checkBoxConsequence);

		checkBoxEConsequence = new JCheckBox("");
		checkBoxEConsequence.setBounds(192, 583, 38, 23);
		frame.getContentPane().add(checkBoxEConsequence);

		checkBoxRisk = new JCheckBox("");
		checkBoxRisk.setBounds(192, 333, 38, 23);
		frame.getContentPane().add(checkBoxRisk);

		btnCalculateRisk = new JButton("Calculate risk");
		btnCalculateRisk.setBounds(274, 570, 172, 23);
		// frame.getContentPane().add(btnCalculateRisk);

	}

	public void fillComboBoxes() {

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
	}

	public void checkBoxListeners() {
		checkboxPriority.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (checkboxPriority.isSelected())
					sliderPriority.setEnabled(true);
				else
					sliderPriority.setEnabled(false);
			}
		});

		checkBoxProbability.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (checkBoxProbability.isSelected())
					sliderProbability.setEnabled(true);
				else
					sliderProbability.setEnabled(false);
			}
		});

		checkBoxConsequence.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (checkBoxConsequence.isSelected())
					sliderConsequence.setEnabled(true);
				else
					sliderConsequence.setEnabled(false);
			}
		});

		checkBoxRisk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (checkBoxRisk.isSelected())
					sliderRisk.setEnabled(true);
				else
					sliderRisk.setEnabled(false);
			}
		});

		checkBoxEPriority.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (checkBoxEPriority.isSelected())
					sliderEPriority.setEnabled(true);
				else
					sliderEPriority.setEnabled(false);
			}
		});

		checkboxPriority.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (checkboxPriority.isSelected())
					sliderPriority.setEnabled(true);
				else
					sliderPriority.setEnabled(false);
			}
		});

		checkBoxEProbabilty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (checkBoxEProbabilty.isSelected())
					sliderEProbability.setEnabled(true);
				else
					sliderEProbability.setEnabled(false);
			}
		});

		checkBoxEConsequence.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (checkBoxEConsequence.isSelected())
					sliderEConsequence.setEnabled(true);
				else
					sliderEConsequence.setEnabled(false);
			}
		});

	}

	public void setUpListeners() {
		checkBoxListeners();

		btnOptimize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				double time, priority, probability, consequence, risk, epriority, eprobability, econsequence;
				String context, component, constraint, effect;

				context = comboBoxContext.getSelectedItem().toString();
				component = comboBoxComponent.getSelectedItem().toString();
				constraint = comboBoxModelConstraint.getSelectedItem().toString();
				effect = comboBoxEffect.getSelectedItem().toString();

				if (textTime.getText().equals(""))
					time = 0;
				else
					time = Integer.parseInt(textTime.getText());
				// context = sliderContext.getValue();
				if (sliderPriority.isVisible() == true)
					priority = sliderPriority.getValue();
				else
					priority = 0;
				if (sliderProbability.isVisible() == true)
					probability = sliderProbability.getValue();
				else
					probability = 0;
				if (sliderConsequence.isVisible() == true)
					consequence = sliderConsequence.getValue();
				else
					consequence = 0;
				if (sliderRisk.isVisible() == true)
					risk = sliderRisk.getValue();
				else
					risk = 0;
				if (sliderEPriority.isVisible() == true)
					epriority = sliderEPriority.getValue();
				else
					epriority = 0;
				if (sliderEProbability.isVisible() == true)
					eprobability = sliderEProbability.getValue();
				else
					eprobability = 0;
				if (sliderEConsequence.isVisible() == true)
					econsequence = sliderEConsequence.getValue();
				else
					econsequence = 0;

				double total = priority + probability + consequence + risk + epriority + eprobability + econsequence;
				priority = priority / total;
				probability = probability / total;
				consequence = consequence / total;
				risk = risk / total;
				epriority = epriority / total;
				eprobability = eprobability / total;
				econsequence = econsequence / total;

				ArrayList<TestCase> tempCaseList = new ArrayList<TestCase>();
				frame.dispose();
				experimentScheduling.setDatabaseConnection(databaseConnection);
				experimentScheduling.setMaxTime(time);
				// experimentScheduling.setContext(context);
				experimentScheduling.setPriority(priority);
				experimentScheduling.setProbability(probability);
				experimentScheduling.setConsequence(consequence);
				experimentScheduling.setContext(context);
				experimentScheduling.setComponent(component);
				experimentScheduling.setConstraint(constraint);
				experimentScheduling.setEffect(effect);
				experimentScheduling.setTotal(total);

				experimentScheduling.setRisk(risk);
				experimentScheduling.setEpriority(epriority);
				experimentScheduling.setEprobability(eprobability);
				experimentScheduling.setEconsequence(econsequence);

				tempCaseList = experimentScheduling.run();

				listTestCasesPage.listCostCases(tempCaseList);
				listTestCasesPage.frame.setVisible(true);
			}
		});

		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				mainPageController.runMainPage();

			}
		});

		btnDefaultConfiguration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				sliderPriority.setEnabled(true);
				sliderProbability.setEnabled(true);
				sliderConsequence.setEnabled(true);
				sliderRisk.setEnabled(true);
				sliderEPriority.setEnabled(true);
				sliderEProbability.setEnabled(true);
				sliderEConsequence.setEnabled(true);

				sliderPriority.setValue(100);
				sliderProbability.setValue(100);
				sliderConsequence.setValue(100);
				sliderRisk.setValue(100);
				sliderEPriority.setValue(100);
				sliderEProbability.setValue(100);
				sliderEConsequence.setValue(100);

				textTime.setText("40");
			}
		});

		btnOptimizeCases.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				optimizeQueries.calculate(databaseConnection);

			}
		});

		btnCalculateRisk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				optimizeQueries.calculateRisk(databaseConnection);

			}
		});
	}
}
