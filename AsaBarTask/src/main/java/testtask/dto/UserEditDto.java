package testtask.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserEditDto {
	
	String description;
	Integer role;

}
