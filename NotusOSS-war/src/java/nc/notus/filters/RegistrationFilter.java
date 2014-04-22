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
 * Filter that registration.jsp page accessed directly or through selection
 * service process.
 * 
 * @author Panchenko Dmytro
 */
public class RegistrationFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filter) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		if (req.getParameter("serviceCatalogID") == null || 
		      req.getParameter("serviceLocationID") == null) {
			RequestDispatcher view = req.getRequestDispatcher("deniedResource.jsp");
			view.forward(req, res);
		}

		filter.doFilter(req, res);

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
