package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Model;

public class ModelQueries {
	public List<Model> model = new ArrayList<Model>();
	public Model[] modelTemp;

	public List<Model> getModel() {
		return model;
	}

	public void setModel(List<Model> model) {
		this.model = model;
	}

	public ResultSet resultSet;

	public ModelQueries() {
		modelTemp = new Model[50];
	}

	public void addModel(DatabaseConnection databaseConnection) {
		String selectContext = "SELECT unnest(enum_range(NULL::model))";

		int i = 0;
		resultSet = databaseConnection.queryTable(selectContext);
		try {
			while (resultSet.next()) {
				modelTemp[i] = new Model();
				modelTemp[i].setName(resultSet.getString("unnest"));

				getModel().add(i, modelTemp[i]);

				i++;
			}
			resultSet.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}
}
