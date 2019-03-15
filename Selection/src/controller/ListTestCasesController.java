package controller;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import database.DatabaseConnection;

public class ListTestCasesController {

	public ResultSet resultSet;
	public ResultSetMetaData md;
	private Vector<String> columnNames;
	private Vector data;

	public Vector<String> getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(Vector columnNames) {
		this.columnNames = columnNames;
	}

	public Vector getData() {
		return data;
	}

	public void setData(Vector data) {
		this.data = data;
	}

	public ListTestCasesController() {
		columnNames = new Vector();
		data = new Vector();
	}

	public void showAllCases(DatabaseConnection databaseConnection) {
		String selectCases = "SELECT id, name, purpose, priority, probability, consequence, risk, executiontime, model, componentname, modelconstraint from testcase ORDER BY ID";
		resultSet = databaseConnection.queryTable(selectCases);

		showCases(resultSet);
	}

	public void showAllResultCases(DatabaseConnection databaseConnection) {
		String selectCases = "SELECT id, name, purpose, priority, probability, consequence,risk, executiontime, result from testcase ORDER BY ID";
		resultSet = databaseConnection.queryTable(selectCases);

		showResultCases(resultSet);
	}

	public void showContextCases(DatabaseConnection databaseConnection, String context) {
		String selectCases = "SELECT id, name, purpose, priority, probability, consequence,executiontime, model, componentname, modelconstraint from testcase where context = '"
				+ context + "' ORDER BY ID";
		resultSet = databaseConnection.queryTable(selectCases);

		showCases(resultSet);
	}

	public void showPriorityCases(DatabaseConnection databaseConnection, String priority) {
		String selectCases = "SELECT id, name, purpose, priority, probability, consequence,executiontime, model, componentname, modelconstraint from testcase where priority = '"
				+ priority + "' ORDER BY ID";
		resultSet = databaseConnection.queryTable(selectCases);

		showCases(resultSet);
	}

	public void showProbabiltityCases(DatabaseConnection databaseConnection, String probability) {
		String selectCases = "SELECT id, name, purpose, priority, probability, consequence,executiontime, model, componentname, modelconstraint from testcase where probability = '"
				+ probability + "' ORDER BY ID";
		resultSet = databaseConnection.queryTable(selectCases);

		showCases(resultSet);
	}

	public void showConsequenceCases(DatabaseConnection databaseConnection, String consequence) {
		String selectCases = "SELECT id, name, purpose, priority, probability, consequence,executiontime, model, componentname, modelconstraint from testcase where consequence = '"
				+ consequence + "' ORDER BY ID";
		resultSet = databaseConnection.queryTable(selectCases);

		showCases(resultSet);
	}

	public void showRiskCases(DatabaseConnection databaseConnection, String risk) {
		String selectCases = "SELECT id, name, purpose, priority, probability, consequence,executiontime, model, componentname, modelconstraint from testcase where risk = '"
				+ risk + "' ORDER BY ID";
		resultSet = databaseConnection.queryTable(selectCases);

		showCases(resultSet);
	}

	public void showComponentCases(DatabaseConnection databaseConnection, String componentname) {
		String selectCases = "SELECT id, name, purpose, priority, probability, consequence,executiontime, model, componentname, modelconstraint from testcase where componentname = '"
				+ componentname + "' ORDER BY ID";
		resultSet = databaseConnection.queryTable(selectCases);

		showCases(resultSet);
	}

	public void showModelConstraintCases(DatabaseConnection databaseConnection, String modelconstraint) {
		String selectCases = "SELECT id, name, purpose, priority, probability, consequence,executiontime, model, componentname, modelconstraint from testcase where modelconstraint = '"
				+ modelconstraint + "' ORDER BY ID";
		resultSet = databaseConnection.queryTable(selectCases);

		showCases(resultSet);
	}

	public void showCases(ResultSet resultSet) {
		columnNames.addElement("Id");
		columnNames.addElement("Key");
		columnNames.addElement("Name");
		columnNames.addElement("Goal");
		columnNames.addElement("Priority");
		columnNames.addElement("Probability");
		columnNames.addElement("Consequence");
		columnNames.addElement("Risk");
		columnNames.addElement("Execution Time");
		columnNames.addElement("Model");
		columnNames.addElement("Component");
		columnNames.addElement("Model Constraint");

		// Get column names
		try {
			md = resultSet.getMetaData();
			int columns = md.getColumnCount();
			int j = 0;
			// Get row data
			while (resultSet.next()) {
				j++;
				Vector row = new Vector(columns);
				for (int i = 0; i <= columns; i++) {
					if (i == 0)
						row.add(j);
					else
						row.addElement(resultSet.getObject(i));
				}
				data.addElement(row);
			}
			resultSet.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());

		}
	}

	public void showResultCases(ResultSet resultSet) {
		columnNames.addElement("Id");
		columnNames.addElement("Key");
		columnNames.addElement("Name");
		columnNames.addElement("Goal");
		columnNames.addElement("Priority");
		columnNames.addElement("Probability");
		columnNames.addElement("Consequence");
		columnNames.addElement("Risk");
		columnNames.addElement("Execution Time");
		columnNames.addElement("Result");

		// Get column names
		try {
			md = resultSet.getMetaData();
			int columns = md.getColumnCount();
			int j = 0;
			// Get row data
			while (resultSet.next()) {
				j++;
				Vector row = new Vector(columns);
				for (int i = 0; i <= columns; i++) {
					if (i == 0)
						row.add(j);
					else
						row.addElement(resultSet.getObject(i));
				}
				data.addElement(row);
			}
			resultSet.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());

		}
	}

}
