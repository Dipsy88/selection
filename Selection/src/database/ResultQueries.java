package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Result;

public class ResultQueries {

	public List<Result> result = new ArrayList<Result>();
	public Result[] ResultTemp;

	public List<Result> getResult() {
		return result;
	}

	public void setResult(List<Result> Result) {
		this.result = Result;
	}

	public ResultSet resultSet;

	public ResultQueries() {
		ResultTemp = new Result[10];

	}

	public void addResult(DatabaseConnection databaseConnection) {
		String selectResult = "SELECT unnest(enum_range(NULL::Result))";

		int i = 0;
		resultSet = databaseConnection.queryTable(selectResult);
		try {
			while (resultSet.next()) {
				ResultTemp[i] = new Result();
				ResultTemp[i].setName(resultSet.getString("unnest"));

				getResult().add(i, ResultTemp[i]);
				i++;
			}
			resultSet.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());

		}

	}
}
