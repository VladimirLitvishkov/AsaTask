package testtask.fiters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import testtask.service.TaskService;

@Service
@Order(10)
public class AuthFilter implements Filter {
	
	@Autowired
	TaskService service;

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		String path = request.getServletPath();
		String method = request.getMethod();
		if (!checkPointCut(path, method)) {
			String xToken = request.getHeader("X-token");
			if (xToken == null) {
				response.sendError(401, "You not have token");
				return;
			}
			ResponseEntity<String> responseCheckToken = service.checkJWT(xToken);
			if (responseCheckToken.getStatusCode().equals(HttpStatus.CONFLICT)) {
				response.sendError(409, "Something wrong");
				return;
			}
			response.addHeader("X-token", responseCheckToken.getHeaders().getFirst("X-token"));
		}
		chain.doFilter(request, response);

	}

	private boolean checkPointCut(String path, String method) {
		boolean check = ((path.matches(".*/task/login") || path.matches(".*/task/user")) && "Post".equalsIgnoreCase(method)) 
				|| "Options".equalsIgnoreCase(method) || path.matches("/h2-console");
		return check;
	}

}
