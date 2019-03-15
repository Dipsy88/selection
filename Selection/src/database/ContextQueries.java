package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Context;

public class ContextQueries {

	public List<Context> context = new ArrayList<Context>();
	public Context[] contextTemp;

	public List<Context> getContext() {
		return context;
	}

	public void setContext(List<Context> context) {
		this.context = context;
	}

	public ResultSet resultSet;

	public ContextQueries() {
		contextTemp = new Context[10];

	}

	public void addContext(DatabaseConnection databaseConnection) {

		String selectContext = "SELECT unnest(enum_range(NULL::context))";

		int i = 0;
		resultSet = databaseConnection.queryTable(selectContext);
		try {
			while (resultSet.next()) {
				contextTemp[i] = new Context();
				contextTemp[i].setName(resultSet.getString("unnest"));

				getContext().add(i, contextTemp[i]);

				i++;
			}
			resultSet.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());

		}

	}

}
