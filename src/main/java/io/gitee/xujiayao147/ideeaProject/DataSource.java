package io.gitee.xujiayao147.ideeaProject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {

	static String uri = "jdbc:mysql://localhost:3306/ideeaproject?useUnicode=true&characterEncoding=utf8";
	static String username = "root";
	static String password = "1234";

	private static Connection connection;

	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			connection = DriverManager.getConnection(uri, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		return connection;
	}
}
