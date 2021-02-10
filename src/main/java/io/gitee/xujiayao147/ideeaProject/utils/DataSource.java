package io.gitee.xujiayao147.ideeaProject.utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataSource {

	private static String uri = "jdbc:mysql://localhost:3306/ideeaproject?useUnicode=true&characterEncoding=utf8";
	private static String username = "root";
	private static String password = "1234";

	private static Connection connection;

	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(uri, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		return connection;
	}
}
