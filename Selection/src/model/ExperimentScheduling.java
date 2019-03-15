package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;

import database.DatabaseConnection;
import database.GetTestCasesQueries;
import simula.oclga.Search;
import view.ListTestCasesPage;

public class ExperimentScheduling {

	private DecimalFormat df;
	private BufferedWriter file;
	private int loopNum;
	private ProblemScheduling problemScheduling;
	private ProblemSchedulingSimple problemSchedulingSimple;
	private int jobsMax;
	private int jobsMin;
	private double maxTime;
	private double total;
	private double priority;
	private double probability;
	private double consequence;
	private String context;
	private double risk;
	private double epriority;
	private double eprobability;
	private double econsequence;
	private String component;
	private String constraint;
	private String effect;
	private DatabaseConnection databaseConnection;

	private ArrayList<TestCase> testCaseList;
	private ListTestCasesPage listTestCasesPage;

	private GetTestCasesQueries testCasesQueries;

	private File fileName;

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public double getRisk() {
		return risk;
	}

	public void setRisk(double risk) {
		this.risk = risk;
	}

	public double getEpriority() {
		return epriority;
	}

	public void setEpriority(double epriority) {
		this.epriority = epriority;
	}

	public double getEprobability() {
		return eprobability;
	}

	public void setEprobability(double eprobability) {
		this.eprobability = eprobability;
	}

	public double getEconsequence() {
		return econsequence;
	}

	public void setEconsequence(double econsequence) {
		this.econsequence = econsequence;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public String getConstraint() {
		return constraint;
	}

	public void setConstraint(String constraint) {
		this.constraint = constraint;
	}

	public String getEffect() {
		return effect;
	}

	public void setEffect(String effect) {
		this.effect = effect;
	}

	public double getPriority() {
		return priority;
	}

	public void setPriority(double priority) {
		this.priority = priority;
	}

	public double getProbability() {
		return probability;
	}

	public void setProbability(double probability) {
		this.probability = probability;
	}

	public double getConsequence() {
		return consequence;
	}

	public void setConsequence(double consequence) {
		this.consequence = consequence;
	}

	public DatabaseConnection getDatabaseConnection() {
		return databaseConnection;
	}

	public void setDatabaseConnection(DatabaseConnection databaseConnection) {
		this.databaseConnection = databaseConnection;
	}

	public ExperimentScheduling() {
		df = new DecimalFormat("0.000");
		loopNum = 100;

		listTestCasesPage = new ListTestCasesPage();
		testCasesQueries = new GetTestCasesQueries();
	}

	public void createFile() throws Exception {
		fileName = new File("C:\\Personal\\practice\\files\\test.txt");

		// if file does not exists, then create it
		if (!fileName.exists()) {
			fileName.createNewFile();
		}
	}

	public ArrayList<TestCase> getValues_1() throws Exception {

		Search[] s = new Search[] { new simula.oclga.AVM(), new simula.oclga.SSGA(100, 0.75), new simula.oclga.OpOEA(),
				new simula.oclga.RandomSearch() };
		String[] s_name = new String[] { "AVM", "GA", "(1+1)EA", "RS" };
		ArrayList<TestCase> tempCaseList = new ArrayList<TestCase>();
		int counter = 0;
		double fitnessValue = 1;

		for (int sea = 2; sea < 3; sea++) {
			// System.out.println("New starts");
			for (int K = 0; K < 10; K++) {
				problemScheduling = new ProblemScheduling();
				problemScheduling.setTestCaseList(testCaseList);
				problemScheduling.setJobsMax(jobsMax);
				problemScheduling.setJobsMin(jobsMin);
				problemScheduling.setTimeBudget(maxTime);
				problemScheduling.setPriority(priority);
				problemScheduling.setProbability(probability);
				problemScheduling.setConsequence(consequence);
				problemScheduling.setRisk(risk);
				problemScheduling.setEpriority(epriority);
				problemScheduling.setEprobability(eprobability);
				problemScheduling.setEconsequence(econsequence);
				problemScheduling.setMax(counter);

				s[sea].setMaxIterations(20000);
				Search.validateConstraints(problemScheduling);
				int[] v_1 = s[sea].search(problemScheduling);

				// System.out.println("max is " + K + " "+ problemScheduling.getMax());

				if (counter <= problemScheduling.getMax()) {
					counter = problemScheduling.getMax();
					// System.out.println("counter is is " + K+ " " +counter);
				}
				if (fitnessValue > problemScheduling.getInitalFitnessValue()) {
					int size = tempCaseList.size() - 1;
					while (size >= 0) {
						// System.out.println(tempCaseList.get(size).getId());
						size--;
					}
					tempCaseList = problemScheduling.caseList;
					fitnessValue = problemScheduling.getInitalFitnessValue();
					// System.out.println("The name is" + s[sea].getShortName());
				}

				//
			}
			// file.write(df.format(m) + "\t"); //

		}
		return tempCaseList;
	}

	public ArrayList<TestCase> getValues_2() throws Exception {
		createFile();

		FileWriter fw = new FileWriter(fileName.getAbsoluteFile());
		file = new BufferedWriter(fw);

		Search[] s = new Search[] { new simula.oclga.AVM(), new simula.oclga.SSGA(100, 0.75), new simula.oclga.OpOEA(),
				new simula.oclga.RandomSearch() };
		String[] s_name = new String[] { "AVM", "GA", "(1+1)EA", "RS" };
		ArrayList<TestCase> tempCaseList = new ArrayList<TestCase>();
		int counter = 0;
		double fitnessValue = 1;
		for (int sea = 2; sea < 3; sea++) {
			for (int K = 0; K < 10; K++) {
				problemSchedulingSimple = new ProblemSchedulingSimple();

				problemSchedulingSimple.setTestCaseList(testCaseList);
				problemSchedulingSimple.setJobsMax(jobsMax);
				problemSchedulingSimple.setJobsMin(jobsMin);
				problemSchedulingSimple.setTimeBudget(maxTime);
				problemSchedulingSimple.calculate();

				s[sea].setMaxIterations(80000);
				Search.validateConstraints(problemSchedulingSimple);
				int[] v_1 = s[sea].search(problemSchedulingSimple);

				// file.write(problemScheduling.getInitalFitnessValue() + "\t"); //
				if (fitnessValue > problemSchedulingSimple.getInitalFitnessValue()) {
					int size = tempCaseList.size() - 1;
					while (size >= 0) {
						// System.out.println(tempCaseList.get(size).getId());
						size--;
					}
					tempCaseList = problemSchedulingSimple.caseList;
					fitnessValue = problemSchedulingSimple.getInitalFitnessValue();
				}
			}
			file.write("\r");
			file.flush();
		}
		return tempCaseList;
	}

	public int getJobsMin() {
		return jobsMin;
	}

	public void setJobsMin(int jobsMin) {
		this.jobsMin = jobsMin;
	}

	public int getJobsMax() {
		return jobsMax;
	}

	public void setJobsMax(int jobsMax) {
		this.jobsMax = jobsMax;
	}

	public double getMaxTime() {
		return maxTime;
	}

	public void setMaxTime(double maxTime) {
		this.maxTime = maxTime;
	}

	public ArrayList<TestCase> run() {
		ArrayList<TestCase> tempCaseList = new ArrayList<TestCase>();
		getTestCases();
		if (this.total > 0) {
			try {
				tempCaseList = getValues_1();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (this.total == 0 && this.maxTime != 0)
			try {
				tempCaseList = getValues_2();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		else
			tempCaseList = this.testCaseList;
		return tempCaseList;

	}

	public void getTestCases() {
		testCasesQueries.setDatabaseConnection(databaseConnection);
		testCaseList = testCasesQueries.getTestCaseContents(context, component, constraint, effect);

		jobsMax = testCaseList.size();
		jobsMin = 0;
	}
}
