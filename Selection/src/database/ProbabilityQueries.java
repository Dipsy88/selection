package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Probability;

public class ProbabilityQueries {

	public List<Probability> probability = new ArrayList<Probability>();
	public Probability[] probabilityTemp;

	public List<Probability> getProbability() {
		return probability;
	}

	public void setProbability(List<Probability> probability) {
		this.probability = probability;
	}

	public ResultSet resultSet;

	public ProbabilityQueries() {
		probabilityTemp = new Probability[10];
	}

	public void addProbability(DatabaseConnection databaseConnection) {
		String selectContext = "SELECT unnest(enum_range(NULL::faultprobability))";

		int i = 0;
		resultSet = databaseConnection.queryTable(selectContext);
		try {
			while (resultSet.next()) {
				probabilityTemp[i] = new Probability();
				probabilityTemp[i].setName(resultSet.getString("unnest"));

				getProbability().add(i, probabilityTemp[i]);

				i++;
			}
			resultSet.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}
}
