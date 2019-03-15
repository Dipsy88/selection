package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Effect;

public class EffectQueries {

	public List<Effect> effect = new ArrayList<Effect>();
	public Effect[] effectTemp;

	public List<Effect> getEffect() {
		return effect;
	}

	public void setEffect(List<Effect> effect) {
		this.effect = effect;
	}

	public ResultSet resultSet;

	public EffectQueries() {
		effectTemp = new Effect[10];
	}

	public void addEffect(DatabaseConnection databaseConnection) {
		String selectContext = "SELECT unnest(enum_range(NULL::effect))";

		int i = 0;
		resultSet = databaseConnection.queryTable(selectContext);
		try {
			while (resultSet.next()) {
				effectTemp[i] = new Effect();
				effectTemp[i].setName(resultSet.getString("unnest"));

				getEffect().add(i, effectTemp[i]);
				i++;
			}
			resultSet.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());

		}

	}
}
