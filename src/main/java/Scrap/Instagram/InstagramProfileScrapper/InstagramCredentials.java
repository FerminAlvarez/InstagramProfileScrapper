package Scrap.Instagram.InstagramProfileScrapper;

public class InstagramCredentials {
	private String username = null;
	private String password = null;
	
	public InstagramCredentials() {
	}
	public InstagramCredentials(String username, String password) {
		this.username = username;
		this.password = password;
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
