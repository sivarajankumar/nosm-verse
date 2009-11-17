import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Telemetry extends javax.servlet.http.HttpServlet implements
javax.servlet.Servlet {
	public Telemetry() {
		super();
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {
		// TODO:
		String amode = (String)request.getParameter("mode");
		// take telem from SL HttpRequest
		// if amode=stats:
			// for mnode id
				// # times selected/player cnt
				// avg duration
				// selected % vs the rest (pie)
				// most probable plots

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//TODO:
		// if amode=admin:
		// ?? add remotelistener, etc... see CS's dox

	}



}
