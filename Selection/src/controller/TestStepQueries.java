package controller;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import database.DatabaseConnection;
import model.TestStep;

public class TestStepQueries {

	public ResultSet resultSet;
	public ResultSetMetaData md;
	private Vector<String> columnNames;
	private Vector data;
	public int[] id = new int[30];
	private int i = 0;

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

	public TestStepQueries() {
		columnNames = new Vector();
		data = new Vector();
	}

	public void showDescription(DatabaseConnection databaseConnection, int id) {
		String selectRuns = "SELECT stepid, description, expectedresult, result, resultvalue,comment  from teststep where caseid= "
				+ id + " ORDER BY ID ";
		resultSet = databaseConnection.queryTable(selectRuns);

		columnNames.addElement("Id");
		columnNames.addElement("Description");
		columnNames.addElement("Expected Result");
		columnNames.addElement("Result");
		columnNames.addElement("Value");
		columnNames.addElement("Comment");

		// Get column names
		try {
			md = resultSet.getMetaData();
			int columns = md.getColumnCount();
			for (int i = 1; i <= columns; i++) {
				// columnNames.addElement(md.getColumnName(i));
			}

			// Get row data
			while (resultSet.next()) {
				Vector row = new Vector(columns);

				for (int i = 1; i <= columns; i++) {
					row.addElement(resultSet.getObject(i));
				}

				data.addElement(row);
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		// Save description info
		String selectIds = "SELECT id from teststep where caseid= " + id + " ORDER BY ID ";

		resultSet = databaseConnection.queryTable(selectIds);
		try {
			while (resultSet.next()) {
				this.id[i] = resultSet.getInt("id");
				i++;
			}
			resultSet.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());

		}

	}

	public void saveDescription(DatabaseConnection databaseConnection, TestStep[] testStep) {
		int testStepSize = testStep.length;
		int j;
		for (j = 0; j < i; j++) {
			String updateRows = "UPDATE teststep SET stepid=" + testStep[j].getStepId() + "description="
					+ testStep[j].getDescription() + "expectedresult=" + testStep[j].getExpectedResult() + "result="
					+ testStep[j].getResult() + "resultvalue=" + testStep[j].getResultValue() + "comment="
					+ testStep[j].getComment() + "where id= " + id[j];
			resultSet = databaseConnection.queryTable(updateRows);
		}
		while (j <= testStepSize) {

		}

	}

}
