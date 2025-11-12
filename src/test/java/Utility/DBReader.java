package Utility;

import java.sql.*;
import java.util.*;

public class DBReader implements DataManager {
	private String jdbcUrl;
	private String user;
	private String password;

	public DBReader(String jdbcUrl, String user, String password) {
		this.jdbcUrl = jdbcUrl;
		this.user = user;
		this.password = password;
	}

	@Override
	public List<Map<String, String>> getAllTestData(String tableName) {
		List<Map<String, String>> allData = new ArrayList<>();
		String query = "SELECT * FROM " + tableName;
		try (Connection conn = DriverManager.getConnection(jdbcUrl, user, password);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			while (rs.next()) {
				Map<String, String> dataMap = new HashMap<>();
				for (int i = 1; i <= columnCount; i++) {
					String key = metaData.getColumnName(i);
					String value = rs.getString(i);
					dataMap.put(key, value);
				}
				allData.add(dataMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allData;
	}
}
