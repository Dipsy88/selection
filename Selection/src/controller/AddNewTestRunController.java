package controller;

import java.awt.EventQueue;

import database.DatabaseConnection;
import view.AddNewTestRunPage;

public class AddNewTestRunController {

	public AddNewTestRunPage addNewTestRunPage;
	public DatabaseConnection databaseConnection;

	public DatabaseConnection getDatabaseConnection() {
		return databaseConnection;
	}

	public void setDatabaseConnection(DatabaseConnection databaseConnection) {
		this.databaseConnection = databaseConnection;
	}

	public void runAddNewTestRun() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {

				try {
					addNewTestRunPage = new AddNewTestRunPage();
					addNewTestRunPage.setDatabaseConnection(databaseConnection);
					addNewTestRunPage.run();
					addNewTestRunPage.frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
