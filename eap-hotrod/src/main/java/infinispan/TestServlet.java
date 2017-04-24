package infinispan;

 

 
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.UUID;

import javax.servlet.AsyncContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;

 

/**
 * <p>
 * A simple asynchronous servlet taking advantage of features added in 3.0.
 * </p>
 * 
 * <p>
 * The servlet is registered and mapped to /AsynchronousServlet using the {@link WebServlet} annotation. The
 * {@link LongRunningService} is injected by CDI.
 * </p>
 * 
 * <p>
 * It shows how to detach the execution of a long-running task from the request processing thread, so the thread is free
 * to serve other client requests. The long-running tasks are executed using a dedicated thread pool and create the
 * client response asynchronously using the {@link AsyncContext}.
 * </p>
 * 
 * <p>
 * A long-running task in this context does not refer to a computation intensive task executed on the same machine but
 * could for example be contacting a third-party service that has limited resources or only allows for a limited number
 * of concurrent connections. Moving the calls to this service into a separate and smaller sized thread pool ensures
 * that less threads will be busy interacting with the long-running service and that more requests can be served that do
 * not depend on this service.
 * </p>
 * 
 * @author Christian Sadilek <csadilek@redhat.com>
 */
@SuppressWarnings("serial")
@WebServlet(value = "/test" )
public class TestServlet extends HttpServlet {

  ArrayList<Player> list = new ArrayList<Player>();

  private RemoteCacheManager cacheManager;
  private RemoteCache<String, Object> cache;
  
  @Override
  public void init() {
      ConfigurationBuilder builder = new ConfigurationBuilder();
      builder.addServer()
            .host(System.getenv("HOTROD_SERVICE"))
            .port(Integer.parseInt(System.getenv("HOTROD_SERVICE_PORT")));
      cacheManager = new RemoteCacheManager(builder.build());
      cache = cacheManager.getCache("default");
      
      System.out.println("Loaded Cache "+cache.getName());
  }
  protected void doGet(HttpServletRequest req, HttpServletResponse res) {
	  doPost(req,res);
  }
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse res) {
    // Here the request is put in asynchronous mode
	//  response.setContentType("text/html");

	  String name = req.getParameter("name");
	  String surname = req.getParameter("surname");
	  String teamName = req.getParameter("teamname");
	  
      // Actual logic goes here.
      PrintWriter out=null;
	try {
		//out = res.getWriter();
		
		   Player player = new Player();
		   player.setName(name);
		   player.setSurname(surname);
		   player.setTeamName(teamName);
		   String randomId = UUID.randomUUID().toString();
	       cache.put(randomId, player);
	       
          //set list in request scope and forward request to JSP
            req.setAttribute("playerList",list);         
            String nextJSP = "/index.jsp";
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
            try {
				dispatcher.forward(req,res);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      out.println("<h1>Called Servlet</h1>");
  }
}
