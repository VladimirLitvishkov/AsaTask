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
@IdClass(StreetID.class)
public class Street implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	Integer streetCode;
	@Id
	@ManyToOne(cascade = CascadeType.ALL)
	City city;
	String description;

}
