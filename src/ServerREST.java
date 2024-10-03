/*
REST сервер без использования фреймворков.
*/
import com.sun.net.httpserver.HttpServer;

import java.io.OutputStream;
import java.net.InetSocketAddress;

public class ServerREST {
	public static void main(String[] args) throws Exception {
		// Создаём объект http сервера
		HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
		// Добавляем контекст
		server.createContext("/users", new MyHandler());
		server.setExecutor(null);
		server.start();
		System.out.println("http://localhost:8080");
	}
}
