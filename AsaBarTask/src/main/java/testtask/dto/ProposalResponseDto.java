package testtask.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import testtask.model.PostalCode;

@Getter
@Setter
@Builder
public class ProposalResponseDto {
	
	Long id;
	String userFirstName;
	String userLastName;
	String userID;
	PostalCode postalCode;
	String userPhone;
	String userMobile;
	String userEmail;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime insertDate;
	String insertUser;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime updateDate;
	String updateUser;

}
