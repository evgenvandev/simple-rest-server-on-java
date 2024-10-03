import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MyHandler implements HttpHandler {
	private UserDao userDao = new UserDao();
	
	@Override
	public void handle(HttpExchange t) throws IOException {
		String requestMethod = t.getRequestMethod().toUpperCase();
		System.out.println("Метод " + requestMethod);
		String response = "";
		if ("GET".equals(requestMethod)) {
			listUsers(t);
		}
		if ("POST".equals(requestMethod)) {
			System.out.println("Метод 1 " + requestMethod);
			createUser(t);
		}
	}
	
	private void createUser(HttpExchange t) throws IOException {
		// Считываем тело запроса от клиента
		StringBuilder requestPOST = new StringBuilder();
		System.out.println("Метод createUser");
		try (InputStream input = t.getRequestBody();) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			String line;
			while ((line = reader.readLine()) != null) {
				requestPOST.append(line);
			}
			System.out.println(requestPOST);
		}
		User userInput = convertJsonToUser(requestPOST.toString());
		User user = userDao.create(userInput.getEmail(), userInput.getRoles());
		if (null == user) {
			System.out.println(String.format("User %s already exists.", userInput.getEmail()));
			sendJson(t, String.format("User %s already exists.", userInput.getEmail()));
			return;
		}
		sendJson(t, convertUserToJson(user));
	}
	
	private void listUsers(HttpExchange t) throws IOException {
		List<User> users = userDao.listUsers();
		String listUsers = convertListUsersToJson(users);
		sendJson(t, listUsers);
	}
	
	private void sendJson(HttpExchange t, String responseJson) throws IOException {
		// Устанавливаем код 200 = OK и размер отправляемых данных
		t.sendResponseHeaders(200, responseJson.length());
		// Пишем в поток вывода данные, которые отправятся пользователю
		try (OutputStream os = t.getResponseBody();) {
			os.write(responseJson.getBytes());
			os.flush();
		}
	}
	
	private User convertJsonToUser(String jsonString) {
		System.out.println("GSON");
		// Создаём экземпляр Gson
		Gson gson = new Gson();
		System.out.println("GSON1");
		// Создадим экземпляр пользователя на основе строки JSON
		User user = gson.fromJson(jsonString, User.class);
		System.out.println("GSON2");
		System.out.println(user);
		return user;
	}
	
	// Метод преобразует один объект User в формат JSON
	private String convertUserToJson(User user) {
		// Создаём объект GsonBuilder для настройки формата JSON
		GsonBuilder builder = new GsonBuilder();
		// Настраиваем удобочитаемый вид JSON
		builder.setPrettyPrinting();
		// Создаём экземпляр Gson из экземпляра GsonBuilder
		Gson gson = builder.create();
		// Сериализуем User в JSON
		String userJson = gson.toJson(user);
		return userJson;
	}
	
	// Метод преобразует список объектов List<User> в формат JSON
	private String convertListUsersToJson(List<User> users) {
		// Создаём объект GsonBuilder для настройки формата JSON
		GsonBuilder builder = new GsonBuilder();
		// Настраиваем удобочитаемый вид JSON
		builder.setPrettyPrinting();
		// Создаём экземпляр Gson из экземпляра GsonBuilder
		Gson gson = builder.create();
		// Сериализуем список объектов List<User> в формат JSON
		String usersJson = gson.toJson(users);
		return usersJson;
	}
}
