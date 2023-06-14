package rest_beispiel;

import demo.messages.MyApplication;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
	public static void main(String[] args) throws Exception {
		Logger.getLogger("org.glassfish").setLevel(Level.SEVERE);

		// listen on every available network interface
		URI baseUri = new URI("http://0.0.0.0:8080/demo/rest");
		ResourceConfig config = ResourceConfig.forApplicationClass(MyApplication.class);
		HttpServer server = GrizzlyHttpServerFactory.createHttpServer(baseUri, config);
		StaticHttpHandler handler = new StaticHttpHandler("web");
		handler.setFileCacheEnabled(false);
		server.getServerConfiguration().addHttpHandler(handler, "/demo");

		System.out.println("http://localhost:8080/demo/");
		System.out.println("ENTER stoppt den Server.");
		System.in.read();
		server.shutdownNow();
	}
}
