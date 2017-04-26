package infinispan;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;

@SuppressWarnings("serial")
@WebServlet(value = "/test")
public class TestServlet extends HttpServlet {

	ArrayList<Player> list = new ArrayList<Player>();

	private RemoteCacheManager cacheManager;
	private RemoteCache<String, Object> cache;
	// private HashMap cache = new HashMap();

	@Override
	public void init() {

		ConfigurationBuilder builder = new ConfigurationBuilder();
		builder.addServer().host(System.getenv("HOTROD_SERVICE"))
				.port(Integer.parseInt(System.getenv("HOTROD_SERVICE_PORT")));
		cacheManager = new RemoteCacheManager(builder.build());
		cache = cacheManager.getCache("default");

		System.out.println("Loaded Cache " + cache);
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) {
		doPost(req, res);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) {
		// Here the request is put in asynchronous mode
		res.setContentType("text/html");

		String name = req.getParameter("name");
		String surname = req.getParameter("surname");
		String teamName = req.getParameter("teamname");
		List list = new ArrayList();
		// Actual logic goes here.
		PrintWriter out = null;
		try {
			out = res.getWriter();

			Player player = new Player();
			player.setName(name);
			player.setSurname(surname);
			player.setTeamName(teamName);
			String randomId = UUID.randomUUID().toString();
			cache.put(randomId, player);
			out.println("Added Player: " + cache.get(randomId));
			out.println("at Key " + randomId);
			/*
			 * Collection coll = cache.values(); 
			 * for (Object value: coll) {
			 * list.add(value); 
			 * }
			 * 
			 * 
			 * 
			 * //set list in request scope and forward request to JSP
			 * req.setAttribute("playerList",list); String nextJSP =
			 * "/index.jsp"; RequestDispatcher dispatcher =
			 * getServletContext().getRequestDispatcher(nextJSP); try {
			 * dispatcher.forward(req,res); } catch (ServletException e) { //
			 * TODO Auto-generated catch block e.printStackTrace(); }
			 */

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
