package controller;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import database.DatabaseConnection;

public class TestRunController {

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

	public TestRunController() {
		columnNames = new Vector();
		data = new Vector();
	}

	public void showRuns(DatabaseConnection databaseConnection, int id) {
		String selectRuns = "SELECT runid, executiondate, result, testername  from testrun where caseid= " + id
				+ " ORDER BY ID ";
		resultSet = databaseConnection.queryTable(selectRuns);

		columnNames.addElement("Run Id");
		columnNames.addElement("Execution Date");
		columnNames.addElement("Result");
		columnNames.addElement("Tester Name");

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
			resultSet.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

}
