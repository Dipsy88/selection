package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Consequence;
import model.Context;
import model.Effect;
import model.Model;
import model.ModelConstraint;
import model.Priority;
import model.Probability;
import model.RCUType;
import model.Risk;
import model.TestCase;

public class GetTestCasesQueries {
	public ResultSet resultSet;
	private TestCase testCase;
	private ArrayList<TestCase> testCaseList;

	public TestCase getTestCase() {
		return testCase;
	}

	public void setTestCase(TestCase testCase) {
		this.testCase = testCase;
	}

	private DatabaseConnection databaseConnection;

	public GetTestCasesQueries() {
		testCaseList = new ArrayList<TestCase>();

	}

	public ArrayList<TestCase> getTestCaseContents(String context2, String component, String constraint,
			String effect2) {
		// String selectContext= "SELECT
		// id,name,purpose,priority,probability,effect,context,
		// model,componentname,modelconstraint,executiontime from testcase ORDER BY id";
		String selectContext, selectComponent, selectConstraint, selectEffect;
		if (context2.isEmpty())
			selectContext = "SELECT id,name,purpose,priority,probability,effect,context, model,componentname,modelconstraint,executiontime,consequence,risk from testcase";
		else
			selectContext = "SELECT id,name,purpose,priority,probability,effect,context, model,componentname,modelconstraint,executiontime,consequence,risk from testcase where context in ('"
					+ context2 + "')";

		if (component.isEmpty())
			selectComponent = "SELECT id,name,purpose,priority,probability,effect,context, model,componentname,modelconstraint,executiontime,consequence,risk from testcase";
		else
			selectComponent = "SELECT id,name,purpose,priority,probability,effect,context, model,componentname,modelconstraint,executiontime,consequence,risk from testcase where componentname in ('"
					+ component + "')";

		if (constraint.isEmpty())
			selectConstraint = "SELECT id,name,purpose,priority,probability,effect,context, model,componentname,modelconstraint,executiontime,consequence,risk from testcase";
		else
			selectConstraint = "SELECT id,name,purpose,priority,probability,effect,context, model,componentname,modelconstraint,executiontime,consequence,risk from testcase where modelconstraint in ('"
					+ constraint + "')";

		if (effect2.isEmpty())
			selectEffect = "SELECT id,name,purpose,priority,probability,effect,context, model,componentname,modelconstraint,executiontime,consequence,risk from testcase";
		else
			selectEffect = "SELECT id,name,purpose,priority,probability,effect,context, model,componentname,modelconstraint,executiontime,consequence,risk from testcase where effect in ('"
					+ effect2 + "')";

		String selectQuery = selectContext + " intersect " + selectComponent + " intersect " + selectConstraint
				+ " intersect " + selectEffect + " ORDER BY ID";
		resultSet = databaseConnection.queryTable(selectQuery);

		double executionTime = 0;

		// System.out.println("The id now is" +testCase.getId());
		try {
			while (resultSet.next()) {
				testCase = new TestCase();
				Priority priority = new Priority();
				Probability probability = new Probability();
				Effect effect = new Effect();
				Context context = new Context();
				Model model = new Model();
				RCUType rcuType = new RCUType();
				ModelConstraint modelConstraint = new ModelConstraint();
				Consequence consequence = new Consequence();
				Risk risk = new Risk();

				testCase.setId(Integer.parseInt(resultSet.getString("id")));
				testCase.setCaseName(resultSet.getString("name"));
				testCase.setGoal(resultSet.getString("purpose"));
				priority.setName(resultSet.getString("priority"));
				probability.setName(resultSet.getString("probability"));
				effect.setName(resultSet.getString("effect"));
				context.setName(resultSet.getString("context"));
				model.setName(resultSet.getString("model"));
				rcuType.setName(resultSet.getString("componentname"));
				modelConstraint.setName(resultSet.getString("modelConstraint"));
				consequence.setName(resultSet.getString("consequence"));
				risk.setName(resultSet.getString("risk"));

				// testCaseList.add(testCase);
				try {
					executionTime = Double.parseDouble(resultSet.getString("executiontime"));
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}

				testCase.setPriority(priority);
				testCase.setProbability(probability);
				testCase.setEffect(effect);
				testCase.setContext(context);
				testCase.setModel(model);
				testCase.setRcuType(rcuType);
				testCase.setModelConstraint(modelConstraint);
				testCase.setTimeExecution(executionTime);
				testCase.setConsequence(consequence);
				testCase.setRisk(risk);

				testCaseList.add(testCase);
			}
			resultSet.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());

		}

		return testCaseList;

	}

	public DatabaseConnection getDatabaseConnection() {
		return databaseConnection;
	}

	public void setDatabaseConnection(DatabaseConnection databaseConnection) {
		this.databaseConnection = databaseConnection;
	}

	public ArrayList<TestCase> getTestCaseList() {
		return testCaseList;
	}

	public void setTestCaseList(ArrayList<TestCase> testCaseList) {
		this.testCaseList = testCaseList;
	}

}
