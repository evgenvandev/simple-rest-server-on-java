import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class UserDao {
	private final Map<String, User> userMap;
	
	public UserDao() {
		this.userMap = new HashMap();
	}
	
	public User create(String email, Set<User.Role> roles) {
		User user = new User(email, roles, LocalDate.now());
		
		// If we get a non null value that means the user already exists in the Map.
		// Если мы получаем ненулевое значение, это означает, что пользователь уже существует в Map (базе).
		if (null != userMap.putIfAbsent(user.getEmail(), user)) {
			return null;
		}
		return user;
	}
	
	public User get(String email) {
		return userMap.get(email);
	}
	
	// Alternate implementation to throw exceptions instead of return nulls for not found.
	// Альтернативная реализация для выдачи исключений вместо возврата значений NULL для ненайденных объектов.
	public User getThrowNotFound(String email) {
		User user = userMap.get(email);
		if (null == user) {
			System.out.println(String.format("User %s not found", email));
		}
		return user;
	}
	
	public User update(User user) {
		// This means no user existed so update failed. return null
		// Это означает, что пользователя не существует, поэтому обновление не удалось. return null
		if (null == userMap.replace(user.getEmail(), user)) {
			return null;
		}
		// Update succeeded return the user
		// Обновление прошло успешно, метод возвращает пользователя
		return user;
	}
	
	public boolean delete(String email) {
		return null != userMap.remove(email);
	}
	
	public List<User> listUsers() {
		return userMap.values().stream().sorted(Comparator.comparing((User u) -> u.getEmail())).collect(Collectors.toList());
	}
}
