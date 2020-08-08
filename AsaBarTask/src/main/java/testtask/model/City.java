package testtask.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@EqualsAndHashCode(of = "cityCode")
public class City implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	Integer cityCode;
	String description;

}
