package nc.notus.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Filter that page accessed directly or through servlet
 * 
 * @author Panchenko Dmytro
 */
public class InstallationEngeenierFilter implements Filter {
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filter) throws IOException, ServletException {
		
	    HttpServletRequest req = (HttpServletRequest)request;
	    HttpServletResponse res = (HttpServletResponse)response;
	    
	    if (request.getAttribute("tasksEng") == null) {
	    	RequestDispatcher view = req.getRequestDispatcher("deniedResource.jsp");
            view.forward(req, res);
	    }

	    filter.doFilter(req,res);

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
  
}
