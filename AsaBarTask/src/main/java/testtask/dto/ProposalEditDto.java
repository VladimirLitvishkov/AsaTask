package testtask.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;
import testtask.model.PostalCode;

@Getter
@Setter
@Builder
public class ProposalEditDto {
	
	String userFirstName;
	String userLastName;
	String userID;
	PostalCode postalCode;
	String userPhone;
	String userMobile;
	String userEmail;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Default
	LocalDateTime updateDate = LocalDateTime.now();
	String updateUser;

}
