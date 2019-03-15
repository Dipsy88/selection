package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.ModelConstraint;

public class ModelConstraintQueries {
	public List<ModelConstraint> modelConstraint = new ArrayList<ModelConstraint>();
	public ModelConstraint[] modelConstraintTemp;

	public List<ModelConstraint> getModelConstraint() {
		return modelConstraint;
	}

	public void setModelConstraint(List<ModelConstraint> modelConstraint) {
		this.modelConstraint = modelConstraint;
	}

	public ResultSet resultSet;

	public ModelConstraintQueries() {
		modelConstraintTemp = new ModelConstraint[50];
	}

	public void addModelConstraint(DatabaseConnection databaseConnection) {
		String selectContext = "SELECT unnest(enum_range(NULL::ModelConstraint))";

		int i = 0;
		resultSet = databaseConnection.queryTable(selectContext);
		try {
			while (resultSet.next()) {
				modelConstraintTemp[i] = new ModelConstraint();
				modelConstraintTemp[i].setName(resultSet.getString("unnest"));

				getModelConstraint().add(i, modelConstraintTemp[i]);

				i++;
			}
			resultSet.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}
}
