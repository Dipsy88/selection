package controller;

import java.awt.EventQueue;

import view.CreateTestCasePage;
import view.ListTestCasesPage;
import view.ListTestCasesResults;
import view.MainPage;
import view.TestCaseSelectionPage;

public class MainPageController {

	public CreateTestCasePage createTestCasePage;
	public ListTestCasesPage listTestCasesPage;
	public MainPage mainPage;
	public TestCaseSelectionPage testCaseSelectionPage;
	public ListTestCasesResults listTestCasesResults;

	public void runcreateTestCasePage() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					createTestCasePage = new CreateTestCasePage();
					createTestCasePage.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void runListCasesPage() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					listTestCasesPage = new ListTestCasesPage();
					listTestCasesPage.listAllCases();
					listTestCasesPage.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void runMainPage() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					mainPage = new MainPage();
					mainPage.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void runSelectionPage() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					testCaseSelectionPage = new TestCaseSelectionPage();
					testCaseSelectionPage.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void runListResultPage() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					listTestCasesResults = new ListTestCasesResults();
					listTestCasesResults.listAllCases();
					listTestCasesResults.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
