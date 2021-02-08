package io.gitee.xujiayao147.ideeaProject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

	public boolean insert(User user) {
		Connection connection = DataSource.getConnection();
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement("insert into users(username,password) values(?,?)");

			statement.setString(1, user.getUsername());
			statement.setString(2, user.getPassword());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (statement != null && statement.executeUpdate() > 0)
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean update(User user) {
		Connection connection = DataSource.getConnection();
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement("update users set username=?,password=? where uid=?");

			statement.setString(1, user.getUsername());
			statement.setString(2, user.getPassword());
			statement.setInt(3, user.getUid());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (statement != null && statement.executeUpdate() > 0)
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public User query(User user) {
		Connection connection = DataSource.getConnection();
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement("select * from users where username=? and password=?");

			statement.setString(1, user.getUsername());
			statement.setString(2, user.getPassword());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			ResultSet result = null;

			if (statement != null)
				result = statement.executeQuery();

			if (result != null && result.next())
				return new User(result.getInt("uid"), result.getString("username"), result.getString("password"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public boolean delete(User user) {
		Connection connection = DataSource.getConnection();
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement("delete from users where uid=?");

			statement.setInt(1, user.getUid());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (statement != null && statement.executeUpdate() > 0)
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}
}
