package testtask.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import testtask.model.PostalCode;

@Getter
@Setter
@Builder
public class ProposalRequestDto {
	
	String userFirstName;
	String userLastName;
	String userID;
	PostalCode postalCode;
	String userPhone;
	String userMobile;
	String userEmail;
	String insertUser;
}
