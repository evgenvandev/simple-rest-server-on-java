import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class User {
	private String email;
	private Set<Role> roles;
	private LocalDate dateCreated;
	
	public User() {}
	
	public User(String email, Set<Role> roles) {
		this.setEmail(email);
		this.setRoles(roles);
		this.setDateCreated(LocalDate.now());
	}
	
	public User(String email, Set<Role> roles, LocalDate dateCreated) {
		this.setEmail(email);
		this.setRoles(roles);
		this.setDateCreated(dateCreated);
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Set<Role> getRoles() {
		return roles;
	}
	
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	public LocalDate getDateCreated() {
		return dateCreated;
	}
	
	public void setDateCreated(LocalDate dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	public static enum Role {
		USER, ADMIN
	}
}
