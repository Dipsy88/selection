package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Priority;

public class PriorityQueries {

	public List<Priority> priority = new ArrayList<Priority>();
	public Priority[] priorityTemp;

	public List<Priority> getPriority() {
		return priority;
	}

	public void setPriority(List<Priority> priority) {
		this.priority = priority;
	}

	public ResultSet resultSet;

	public PriorityQueries() {
		priorityTemp = new Priority[10];

	}

	public void addPriority(DatabaseConnection databaseConnection) {

		String selectPriority = "SELECT unnest(enum_range(NULL::priority))";

		int i = 0;
		resultSet = databaseConnection.queryTable(selectPriority);
		try {
			while (resultSet.next()) {
				priorityTemp[i] = new Priority();
				priorityTemp[i].setName(resultSet.getString("unnest"));

				getPriority().add(i, priorityTemp[i]);

				i++;
			}
			resultSet.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());

		}

	}

}
