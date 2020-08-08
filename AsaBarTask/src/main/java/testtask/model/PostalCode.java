package testtask.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@IdClass(PostalID.class)
public class PostalCode implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@ManyToOne(cascade = CascadeType.PERSIST)
	Street street;
	@Id
	String houseNumber;
	@Id
	Integer postalCode;

}
