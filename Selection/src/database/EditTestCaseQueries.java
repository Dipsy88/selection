package database;

import java.sql.ResultSet;
import java.sql.SQLException;

import model.Consequence;
import model.Context;
import model.Effect;
import model.Model;
import model.ModelConstraint;
import model.Priority;
import model.Probability;
import model.RCUType;
import model.TestCase;

public class EditTestCaseQueries {
	public ResultSet resultSet;
	private TestCase testCase;

	public TestCase getTestCase() {
		return testCase;
	}

	public void setTestCase(TestCase testCase) {
		this.testCase = testCase;
	}

	private DatabaseConnection databaseConnection;

	public EditTestCaseQueries() {
		testCase = new TestCase();

	}

	public void getTestCaseContents(int id) {
		String selectContext = "SELECT name,purpose,priority,probability,effect,context, model,componentname,modelconstraint,executiontime,consequence from testcase where id ="
				+ id;
		resultSet = databaseConnection.queryTable(selectContext);

		testCase.setId(id);
		Priority priority = new Priority();
		Probability probability = new Probability();
		Consequence consequence = new Consequence();
		Effect effect = new Effect();
		Context context = new Context();
		Model model = new Model();
		RCUType rcuType = new RCUType();
		ModelConstraint modelConstraint = new ModelConstraint();
		double executionTime = 0;

		// System.out.println("The id now is" +testCase.getId());
		try {
			while (resultSet.next()) {
				testCase.setCaseName(resultSet.getString("name"));
				testCase.setGoal(resultSet.getString("purpose"));
				priority.setName(resultSet.getString("priority"));
				probability.setName(resultSet.getString("probability"));
				consequence.setName(resultSet.getString("consequence"));
				effect.setName(resultSet.getString("effect"));
				context.setName(resultSet.getString("context"));
				model.setName(resultSet.getString("model"));
				rcuType.setName(resultSet.getString("componentname"));
				modelConstraint.setName(resultSet.getString("modelConstraint"));
				try {
					executionTime = Double.parseDouble(resultSet.getString("executiontime"));
				} catch (Exception e) {

				}
			}
			resultSet.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());

		}
		testCase.setPriority(priority);
		testCase.setProbability(probability);
		testCase.setConsequence(consequence);
		testCase.setEffect(effect);
		testCase.setContext(context);
		testCase.setModel(model);
		testCase.setRcuType(rcuType);
		testCase.setModelConstraint(modelConstraint);
		testCase.setTimeExecution(executionTime);
	}

	public DatabaseConnection getDatabaseConnection() {
		return databaseConnection;
	}

	public void setDatabaseConnection(DatabaseConnection databaseConnection) {
		this.databaseConnection = databaseConnection;
	}

}
