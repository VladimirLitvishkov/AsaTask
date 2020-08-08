package testtask.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Proposal not found")
public class ProposalNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

}
