package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Consequence;

public class ConsequenceQueries {

	public List<Consequence> Consequence = new ArrayList<Consequence>();
	public Consequence[] consequenceTemp;

	public List<Consequence> getConsequence() {
		return Consequence;
	}

	public void setConsequence(List<Consequence> Consequence) {
		this.Consequence = Consequence;
	}

	public ResultSet resultSet;

	public ConsequenceQueries() {
		consequenceTemp = new Consequence[10];

	}

	public void addConsequence(DatabaseConnection databaseConnection) {

		String selectConsequence = "SELECT unnest(enum_range(NULL::Consequence))";

		int i = 0;
		resultSet = databaseConnection.queryTable(selectConsequence);
		try {
			while (resultSet.next()) {
				consequenceTemp[i] = new Consequence();
				consequenceTemp[i].setName(resultSet.getString("unnest"));

				getConsequence().add(i, consequenceTemp[i]);

				i++;
			}
			resultSet.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());

		}

	}

}
