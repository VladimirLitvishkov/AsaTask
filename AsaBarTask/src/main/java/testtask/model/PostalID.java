package testtask.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostalID implements Serializable{
	
	private static final long serialVersionUID = 1L;

	Street street;
	
	String houseNumber;
	
	Integer postalCode;

}
