package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import database.DatabaseConnection;
import database.RCUTypeQueries;
import database.ResultQueries;
import model.RCUType;
import model.Result;

public class SelectResourcePage {

	private DatabaseConnection databaseConnection;

	public DatabaseConnection getDatabaseConnection() {
		return databaseConnection;
	}

	public void setDatabaseConnection(DatabaseConnection databaseConnection) {
		this.databaseConnection = databaseConnection;
	}

	public JFrame frame;
	private JTextField textField;
	private JTextField textTesterName;

	private ResultQueries resultQueries;

	private JButton btnSave;
	private JButton btnCancel;

	private JComboBox comboBoxResult, comboBoxComponent;
	private JLabel label;
	private RCUTypeQueries rcuTypeQueries;

	/**
	 * Launch the application.
	 */
	public SelectResourcePage() {

	}

	public void run() {
		initComponents();
		initialize();

		fillComboBoxes();

		setUpListeners();

	}

	/**
	 * Create the application.
	 */

	public void initComponents() {
		resultQueries = new ResultQueries();
		rcuTypeQueries = new RCUTypeQueries();
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @wbp.parser.entryPoint
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 820, 455);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Select resource");

		JLabel lblExecutionDate = new JLabel("Execution Date");
		lblExecutionDate.setBounds(23, 30, 108, 14);
		frame.getContentPane().add(lblExecutionDate);

		JLabel lblResult = new JLabel("Result");
		lblResult.setBounds(22, 76, 46, 14);
		frame.getContentPane().add(lblResult);

		JLabel lblTesterName = new JLabel("Tester Name");
		lblTesterName.setBounds(23, 161, 89, 14);
		frame.getContentPane().add(lblTesterName);

		textField = new JTextField();
		textField.setBounds(158, 27, 159, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		comboBoxResult = new JComboBox();
		comboBoxResult.setBounds(159, 73, 158, 20);
		frame.getContentPane().add(comboBoxResult);

		textTesterName = new JTextField();
		textTesterName.setBounds(158, 158, 159, 20);
		frame.getContentPane().add(textTesterName);
		textTesterName.setColumns(10);

		JButton btnSave = new JButton("Save");

		btnSave.setBounds(23, 218, 89, 23);
		frame.getContentPane().add(btnSave);

		btnCancel = new JButton("Cancel");

		btnCancel.setBounds(158, 218, 89, 23);
		frame.getContentPane().add(btnCancel);

		label = new JLabel("Test case component");
		label.setBounds(23, 118, 138, 14);
		frame.getContentPane().add(label);

		comboBoxComponent = new JComboBox();
		comboBoxComponent.setBounds(158, 115, 159, 20);
		frame.getContentPane().add(comboBoxComponent);
	}

	public void setUpListeners() {
		// btnSave.addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent e) {
		// }
		// });
		//
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
	}

	public void fillComboBoxes() {
		try {
			this.resultQueries.addResult(databaseConnection);
			Iterator<Result> resultIterator = this.resultQueries.getResult().iterator();

			while (resultIterator.hasNext()) {
				this.comboBoxResult.addItem(resultIterator.next().getName());
				resultIterator.remove();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			this.rcuTypeQueries.addRCUType(databaseConnection);
			Iterator<RCUType> rcuTypeIterator = this.rcuTypeQueries.getRCUType().iterator();

			while (rcuTypeIterator.hasNext()) {
				String tempRCUType = rcuTypeIterator.next().getName();

				this.comboBoxComponent.addItem(tempRCUType);
				rcuTypeIterator.remove();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
