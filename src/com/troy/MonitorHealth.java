package com.troy;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.naming.Context;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class MonitorHealth
 */
public class MonitorHealth extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static MBeanServerConnection connection;
	private static JMXConnector connector;
	private static ObjectName service;
	private static String combea = "com.bea:Name=";
	private static String service1 = "DomainRuntimeService,Type=weblogic.management.mbeanservers.domainruntime.DomainRuntimeServiceMBean";
	private static String service2 = "RuntimeService,Type=weblogic.management.mbeanservers.runtime.RuntimeServiceMBean";
	private static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	static java.io.PrintWriter out;
	
	static int rowCount = 0;
	
    public MonitorHealth() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
		  if (request.getParameter("username") == "" || request.getParameter("password") == "")
		   {
			request.setAttribute("Message", "UserName or Password null");
        	RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.jsp");
        	rd.forward(request,response);  
		  }
		

	      String hostname = request.getParameter("serverhost").toString();
	      String portString = request.getParameter("serverport").toString();
	      String username = request.getParameter("username").toString();
	      String password = request.getParameter("password").toString();
	      
		  response.setContentType("text/html");
		  out = response.getWriter();
		  out.println("<html><head><title>Weblogic ACS</title></head> <img src='2000px-Oracle_logo.svg.png' height='30'/> ");
		  out.println("<body> <hr> <h2> <center> MBean Monitoring demo by Troy </center> </h2> <hr>");
	      
	      out.println("<table id='display' width='90%' align='center' border='0'>");
	      	out.println("<tr>");
	      		out.println("<th>Time</th>");
	      		out.println("<th>ServerName</th>");
	      		out.println("<th>HostIP</th>");
	      		out.println("<th>State</th>");
	      		out.println("<th>Health</th>");
	      		out.println("<th>Reason</th>");
	      	out.println("</tr>");
	      //out.println("</table>");
	      
	      
	       try {
               service =new ObjectName(combea + service1);
               initConnection(hostname, portString, username, password);
               for (int i=0; i<5; i++)
            	   {
           	   		for (int j=0; j <= rowCount;j++ )
           	   			out.println("<script> document.getElementById('display').deleteRow(1);</script>  ");
            	   	getServerState();
            	   	Thread.sleep(5000);
            	   }
               connector.close();
	       }catch (Exception e) {
              e.printStackTrace();
       }
	    
	    
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}
	
	
	   public static void initConnection(String hostname, String portString,
			      String username, String password) throws IOException,
			      MalformedURLException, AttributeNotFoundException, InstanceNotFoundException, MBeanException, ReflectionException {

			      String protocol = "t3";
			      Integer portInteger = Integer.valueOf(portString);
			      int port = portInteger.intValue();
			      String jndiroot = "/jndi/";
			      String mserver = "weblogic.management.mbeanservers.domainruntime";
			      JMXServiceURL serviceURL = new JMXServiceURL(protocol, hostname, port,
			      jndiroot + mserver);

			      Hashtable h = new Hashtable();
			      h.put(Context.SECURITY_PRINCIPAL, username);
			      h.put(Context.SECURITY_CREDENTIALS, password);
			      h.put(JMXConnectorFactory.PROTOCOL_PROVIDER_PACKAGES,
			         "weblogic.management.remote");
			      h.put("jmx.remote.x.request.waiting.timeout", new Long(10000));
			      connector = JMXConnectorFactory.connect(serviceURL, h);
			      connection = connector.getMBeanServerConnection();

			   }
	   

	public static void getServerState() throws Exception {
		    rowCount = 0;
		    ObjectName domain = (ObjectName) connection.getAttribute(service,"DomainConfiguration");
		    //System.out.println("Domain: " + domain.getKeyProperty("Name"));
		    ObjectName[] servers = (ObjectName[]) connection.getAttribute(domain,"Servers");
		    for (ObjectName server : servers) {
		    	rowCount++;
		        String serverState="UNKNOWN";
		        String aName = (String) connection.getAttribute(server,"Name");
		        try {
		            ObjectName ser= new ObjectName("com.bea:Name="+aName+",Location="+aName+",Type=ServerRuntime");
		            serverState=(String) connection.getAttribute(ser,"State");
		            weblogic.health.HealthState serverHealth = (weblogic.health.HealthState)connection.getAttribute(ser,"OverallHealthState");
		            String health = null;
		            if (serverHealth.getState() == weblogic.health.HealthState.HEALTH_CRITICAL)
		            	health = "<font color='red'>CRITICAL</font>";
		            if (serverHealth.getState() == weblogic.health.HealthState.HEALTH_FAILED)
		            	health = "<font color='red'>FAILED</font>";
		            if (serverHealth.getState() == weblogic.health.HealthState.HEALTH_OK)
		            	health = "<font color='green'>OK</font>";
		            if (serverHealth.getState() == weblogic.health.HealthState.HEALTH_OVERLOADED)
		            	health = "<font color='yellow'>OVERLOAD</font>";
		            if (serverHealth.getState() == weblogic.health.HealthState.HEALTH_WARN)
		            	health = "<font color='yellow'>WARNIING</font>";
		            
		            String serverName = aName + "[" + (String) connection.getAttribute(ser,"ListenAddress") + "]";
		            
		            System.out.println(dateFormat.format(new Date()) + " Server: "+serverName+ " State: "+ serverState + ", Health: "+ health + ", Reason: " + serverHealth.getReasonCodeSummary());
		            out.print("<tr>");
		            	out.print("<td align='center' width=30%>" + dateFormat.format(new Date()) + "</td>");
		            	out.print("<td align='center' width=20%>" + aName + "</td>");
		            	out.print("<td align='center' width=20%>" + (String) connection.getAttribute(ser,"ListenAddress") + "</td>");
		            	out.print("<td align='center' width=10%>" + serverState + "</td>");
		            	out.print("<td align='center' width=10%>" + health + "</td>");
		            	out.print("<td align='center' width=10%>" + serverHealth.getReasonCodeSummary() + "</td>");
		            out.print("</tr>");
		            out.flush();
		            	
		            	
		         } catch(Exception e) {
		        	//ObjectName ser= new ObjectName("com.bea:Name="+aName+",Location="+aName+",Type=ServerRuntime");
		            System.out.println(dateFormat.format(new Date()) + " Server: "+aName+" State: SHUTDOWN (or) In State : "+ serverState);
		            out.print("<tr>");
	            		out.print("<td align='center' width=30%>" + dateFormat.format(new Date()) + "</td>");
	            		out.print("<td align='center' width=20%>" + aName + "</td>");
	            		out.print("<td align='center' width=20%>" + "-" + "</td>");
	            		out.print("<td align='center' width=10%>" + serverState + "</td>");
	            		out.print("<td align='center' width=10%>" + "UNKNOWN" + "</td>");
	            		out.print("<td align='center' width=10%>" + "N/A" + "</td>");
	            	out.print("</tr>");
	            	out.flush();
		         }
		      }
		    }

}
