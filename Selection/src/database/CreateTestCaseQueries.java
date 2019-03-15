package database;

import java.sql.ResultSet;
import java.sql.SQLException;

import model.TestCase;

public class CreateTestCaseQueries {
	public ResultSet resultSet;
	public int lastCaseNumber;

	public void addLastCaseNumber(DatabaseConnection databaseConnection) {
		String selectContext = "SELECT id from testcase ORDER BY ID desc limit 1";

		int i = 0;
		resultSet = databaseConnection.queryTable(selectContext);
		try {
			while (resultSet.next()) {
				lastCaseNumber = resultSet.getInt("id");
			}
			resultSet.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());

		}

	}

	public void savePage(DatabaseConnection databaseConnection, TestCase testCase) {
		String priority, probability, consequence, effect, context, name, purpose, model, componentname,
				modelconstraint, time;
		double executiontime;
		if ((testCase.getPriority().getName()) == "")
			priority = "default";
		else
			priority = "'" + testCase.getPriority().getName() + "'";
		if ((testCase.getProbability().getName()) == "")
			probability = "default";
		else
			probability = "'" + testCase.getProbability().getName() + "'";
		if ((testCase.getConsequence().getName()) == "")
			consequence = "default";
		else
			consequence = "'" + testCase.getConsequence().getName() + "'";
		if ((testCase.getEffect().getName()) == "")
			effect = "default";
		else
			effect = "'" + testCase.getEffect().getName() + "'";
		if ((testCase.getContext().getName()) == "")
			context = "default";
		else
			context = "'" + testCase.getContext().getName() + "'";
		if ((testCase.getCaseName()).equals(""))
			name = null;
		else
			name = "'" + testCase.getCaseName() + "'";
		if ((testCase.getGoal()).equals(""))
			purpose = null;
		else
			purpose = "'" + testCase.getGoal() + "'";
		if ((testCase.getModel().getName()) == "")
			model = "default";
		else
			model = "'" + testCase.getModel().getName() + "'";
		if ((testCase.getRcuType().getName()) == "")
			componentname = "default";
		else
			componentname = "'" + testCase.getRcuType().getName() + "'";
		if ((testCase.getModelConstraint().getName()) == "")
			modelconstraint = "default";
		else
			modelconstraint = "'" + testCase.getModelConstraint().getName() + "'";
		if ((testCase.getTimeExecution()) == 0)
			time = "default";
		else
			time = String.valueOf(testCase.getTimeExecution());
		String insertCase = "INSERT INTO testcase(priority,probability,effect,context,name,purpose,consequence,model,"
				+ "componentname,modelconstraint,executiontime) values (" + priority + ", " + probability + ", "
				+ effect + ", " + context + ", " + name + ", " + purpose + ", " + consequence + ", " + model + ", "
				+ componentname + ", " + modelconstraint + ", " + time + ")";

		databaseConnection.insertTable(insertCase);
	}

}
