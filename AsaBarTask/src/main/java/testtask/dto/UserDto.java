package testtask.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDto {
	
	String name;
	String description;
	Integer role;

}
