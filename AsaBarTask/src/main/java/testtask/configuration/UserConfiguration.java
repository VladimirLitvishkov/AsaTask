package testtask.configuration;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;
import testtask.exceptions.UserAuthenticationException;

@Configuration
@Getter
@Setter
public class UserConfiguration {
	
	@Value("${expPeriod}")
	long expPeriod;
	
	@Value("${secret}")
	String secret;
	
	public UserCredentials tokenDecode(String token) {
		try {
			int pos = token.indexOf(" ");
			token = token.substring(pos + 1);
			String credential = new String(Base64.getDecoder().decode(token));
			String[] credentials = credential.split(":");
			return new UserCredentials(credentials[0], credentials[1]);
		} catch (Exception e) {
			throw new UserAuthenticationException();
		}
	}

}
