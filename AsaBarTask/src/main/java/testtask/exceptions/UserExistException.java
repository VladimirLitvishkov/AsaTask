package testtask.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "User exist!")
public class UserExistException extends RuntimeException{
	private static final long serialVersionUID = 1L;

}
