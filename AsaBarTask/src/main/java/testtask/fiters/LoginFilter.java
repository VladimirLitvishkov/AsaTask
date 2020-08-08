package testtask.fiters;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import testtask.configuration.UserConfiguration;
import testtask.configuration.UserCredentials;
import testtask.dao.UserRepository;
import testtask.model.User;

@Service
@Order(15)
public class LoginFilter implements Filter {
	
	@Autowired
	UserConfiguration config;
	
	@Autowired
	UserRepository repository;

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		String path = request.getServletPath();
		String method = request.getMethod();
		String auth = request.getHeader("Authorization");
		if (checkPointCut(path, method)) {
			UserCredentials credentials = null;
			try {
				credentials = config.tokenDecode(auth);
			} catch (Exception e) {
				response.sendError(401, "Header Authorization is not valid");
				return;
			}
			User user = repository.findById(credentials.getLogin()).orElse(null);
			if (user == null) {
				response.sendError(401, "User not found");
				return;
			}
//			if (!BCrypt.checkpw(credentials.getPassword(), user.getPassword())) { // if there is a password
//				response.sendError(403, "Password incorrect");
//				return;
//			}
			chain.doFilter(new WrapperRequest(request, credentials.getLogin()), response);
			return;
		}
		chain.doFilter(request, response);

	}

	private boolean checkPointCut(String path, String method) {
		boolean check = path.matches(".*/task/login") && "Post".equalsIgnoreCase(method);
		return check;
	}
	
	private class WrapperRequest extends HttpServletRequestWrapper {
		String user;

		public WrapperRequest(HttpServletRequest request, String user) {
			super(request);
			this.user = user;
		}

		@Override
		public Principal getUserPrincipal() {
			return new Principal() {

				@Override
				public String getName() {
					return user;
				}
			};
		}
	}

}
