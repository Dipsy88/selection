package controller;

import java.awt.EventQueue;
import java.sql.ResultSet;

import database.DatabaseConnection;
import database.EditTestCaseQueries;
import model.TestCase;
import view.EditTestCasePage;

public class EditTestCaseController {
	public ResultSet resultSet;
	private TestCase testCase;
	private DatabaseConnection databaseConnection;
	private int id;
	public EditTestCaseQueries editTestCaseQueries;

	public EditTestCasePage editTestCasePage;

	public EditTestCaseController() {
		testCase = new TestCase();
		editTestCaseQueries = new EditTestCaseQueries();

	}

	public void runEditTestCasePage() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					editTestCasePage = new EditTestCasePage();

					editTestCaseQueries.getTestCaseContents(id);
					editTestCasePage.setTestCase(editTestCaseQueries.getTestCase());

					editTestCasePage.run();
					editTestCasePage.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
