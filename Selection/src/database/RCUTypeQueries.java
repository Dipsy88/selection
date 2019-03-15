package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.RCUType;

public class RCUTypeQueries {
	public List<RCUType> rcuType = new ArrayList<RCUType>();
	public RCUType[] rcuTypeTemp;

	public List<RCUType> getRCUType() {
		return rcuType;
	}

	public void setRCUType(List<RCUType> RCUType) {
		this.rcuType = RCUType;
	}

	public ResultSet resultSet;

	public RCUTypeQueries() {
		rcuTypeTemp = new RCUType[50];
	}

	public void addRCUType(DatabaseConnection databaseConnection) {
		String selectContext = "SELECT unnest(enum_range(NULL::RCUType))";

		int i = 0;
		resultSet = databaseConnection.queryTable(selectContext);
		try {
			while (resultSet.next()) {
				rcuTypeTemp[i] = new RCUType();
				rcuTypeTemp[i].setName(resultSet.getString("unnest"));

				getRCUType().add(i, rcuTypeTemp[i]);

				i++;
			}
			resultSet.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}
}
