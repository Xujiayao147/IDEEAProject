package io.gitee.xujiayao147.ideeaProject;

public class User {

	private int uid;
	private String username;
	private String password;

	public User(int uid, String username, String password) {
		super();

		this.uid = uid;
		this.username = username;
		this.password = password;
	}

	public User() {
		super();
	}

	@Override
	public String toString() {
		return new StringBuilder("[").append(uid).append(", ").append(username).append(", ").append(password).append("]").toString();
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
