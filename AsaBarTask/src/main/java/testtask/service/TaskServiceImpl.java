package testtask.service;

import java.io.UnsupportedEncodingException;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import testtask.configuration.UserConfiguration;
import testtask.dao.CityRepository;
import testtask.dao.PostalCodeRepository;
import testtask.dao.ProposalRepository;
import testtask.dao.StreetRepository;
import testtask.dao.UserRepository;
import testtask.dto.ProposalEditDto;
import testtask.dto.ProposalRequestDto;
import testtask.dto.ProposalResponseDto;
import testtask.dto.UserDto;
import testtask.dto.UserEditDto;
import testtask.exceptions.ProposalNotFoundException;
import testtask.exceptions.UserExistException;
import testtask.exceptions.UserNotFoundException;
import testtask.model.City;
import testtask.model.PostalCode;
import testtask.model.PostalID;
import testtask.model.Proposal;
import testtask.model.Street;
import testtask.model.StreetID;
import testtask.model.User;

@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	ProposalRepository proposalRepository;

	@Autowired
	PostalCodeRepository postalCodeRepository;

	@Autowired
	StreetRepository streetRepository;

	@Autowired
	CityRepository cityRepository;

	@Autowired
	UserConfiguration userConfig;

	@Override
	public boolean addUser(UserDto userDto) {
		if (userDto == null) {
			return false;
		}
		if (userRepository.existsById(userDto.getName())) {
			throw new UserExistException();
		}
		User user = User.builder().name(userDto.getName()).description(userDto.getDescription()).role(userDto.getRole())
				.build();
		return userRepository.save(user) != null;
	}

	@Override
	public boolean addProposal(ProposalRequestDto proposalRequestDto) {
		if (proposalRequestDto == null) {
			return false;
		}
		Integer newCityCode = proposalRequestDto.getPostalCode().getStreet().getCity().getCityCode();
		City newCity = cityRepository.findById(newCityCode).orElse(City.builder().cityCode(newCityCode)
				.description(proposalRequestDto.getPostalCode().getStreet().getCity().getDescription()).build());
		StreetID streetID = StreetID.builder().city(newCity)
				.streetCode(proposalRequestDto.getPostalCode().getStreet().getStreetCode()).build();
		Street newStreet = streetRepository.findById(streetID)
				.orElse(Street.builder().city(newCity)
						.streetCode(proposalRequestDto.getPostalCode().getStreet().getStreetCode())
						.description(proposalRequestDto.getPostalCode().getStreet().getDescription()).build());
		PostalID postalID = PostalID.builder().street(newStreet)
				.postalCode(proposalRequestDto.getPostalCode().getPostalCode())
				.houseNumber(proposalRequestDto.getPostalCode().getHouseNumber()).build();
		PostalCode newPostalCode = postalCodeRepository.findById(postalID)
				.orElse(PostalCode.builder().street(newStreet)
						.postalCode(proposalRequestDto.getPostalCode().getPostalCode())
						.houseNumber(proposalRequestDto.getPostalCode().getHouseNumber()).build());
		Proposal proposal = Proposal.builder().userFirstName(proposalRequestDto.getUserFirstName())
				.userLastName(proposalRequestDto.getUserLastName()).userID(proposalRequestDto.getUserID())
				.userEmail(proposalRequestDto.getUserEmail()).userPhone(proposalRequestDto.getUserPhone())
				.userMobile(proposalRequestDto.getUserMobile()).insertUser(proposalRequestDto.getInsertUser())
				.updateUser(proposalRequestDto.getInsertUser()).postalCode(newPostalCode).build();
		return proposalRepository.save(proposal) != null;
	}

	@Override
	public UserDto findUser(String name) {
		User user = userRepository.findById(name).orElseThrow(UserNotFoundException::new);
		return buildUserDto(user);
	}

	private UserDto buildUserDto(User user) {
		return UserDto.builder().name(user.getName()).description(user.getDescription()).role(user.getRole()).build();
	}

	@Override
	public ProposalResponseDto findProposal(Long id) {
		Proposal proposal = proposalRepository.findById(id).orElseThrow(ProposalNotFoundException::new);
		return buildProposalDto(proposal);
	}

	private ProposalResponseDto buildProposalDto(Proposal proposal) {
		return ProposalResponseDto.builder().id(proposal.getId()).userFirstName(proposal.getUserFirstName())
				.userLastName(proposal.getUserLastName()).userID(proposal.getUserID())
				.userEmail(proposal.getUserEmail()).userPhone(proposal.getUserPhone())
				.userMobile(proposal.getUserMobile()).insertUser(proposal.getInsertUser())
				.insertDate(proposal.getInsertDate()).updateUser(proposal.getUpdateUser())
				.updateDate(proposal.getUpdateDate()).postalCode(proposal.getPostalCode()).build();
	}

	@Override
	public UserDto editUser(UserEditDto editDto, String name) {
		User user = userRepository.findById(name).orElseThrow(UserNotFoundException::new);
		if (editDto != null && editDto.getDescription() != null) {
			user.setDescription(editDto.getDescription());
		}
		if (editDto != null && editDto.getRole() != null) {
			user.setRole(editDto.getRole());
		}
		userRepository.save(user);
		return buildUserDto(user);
	}

	@Override
	public ProposalResponseDto editProposal(ProposalEditDto editDto, Long id) {
		Proposal proposal = proposalRepository.findById(id).orElseThrow(ProposalNotFoundException::new);
		if (editDto != null && editDto.getUserFirstName() != null) {
			proposal.setUserFirstName(editDto.getUserFirstName());
		}
		if (editDto != null && editDto.getUserLastName() != null) {
			proposal.setUserLastName(editDto.getUserLastName());
		}
		if (editDto != null && editDto.getUserID() != null) {
			proposal.setUserID(editDto.getUserID());
		}
		if (editDto != null && editDto.getUserEmail() != null) {
			proposal.setUserEmail(editDto.getUserEmail());
		}
		if (editDto != null && editDto.getUserPhone() != null) {
			proposal.setUserPhone(editDto.getUserPhone());
		}
		if (editDto != null && editDto.getUserMobile() != null) {
			proposal.setUserMobile(editDto.getUserMobile());
		}
		if (editDto != null && editDto.getUpdateUser() != null) {
			proposal.setUpdateUser(editDto.getUpdateUser());
		}
		if (editDto != null && editDto.getUpdateDate() != null) {
			proposal.setUpdateDate(editDto.getUpdateDate());
		}
		if (editDto != null && editDto.getPostalCode() != null) {
			Street newStreet = null;
			City newCity = null;
			if (editDto.getPostalCode().getStreet() != null) {
				if (editDto.getPostalCode().getStreet().getCity() != null) {
					newCity = City.builder()
							.cityCode(editDto.getPostalCode().getStreet().getCity().getCityCode() != null
									? editDto.getPostalCode().getStreet().getCity().getCityCode()
									: proposal.getPostalCode().getStreet().getCity().getCityCode())
							.description(editDto.getPostalCode().getStreet().getCity().getDescription() != null
									? editDto.getPostalCode().getStreet().getCity().getDescription()
									: proposal.getPostalCode().getStreet().getCity().getDescription())
							.build();
				}
				newStreet = Street.builder()
						.streetCode(editDto.getPostalCode().getStreet().getStreetCode() != null
								? editDto.getPostalCode().getStreet().getStreetCode()
								: proposal.getPostalCode().getStreet().getStreetCode())
						.description(editDto.getPostalCode().getStreet().getDescription() != null
								? editDto.getPostalCode().getStreet().getDescription()
								: proposal.getPostalCode().getStreet().getDescription())
						.city(newCity != null ? newCity : proposal.getPostalCode().getStreet().getCity()).build();
			}
			proposal.setPostalCode(
					PostalCode.builder()
							.houseNumber(editDto.getPostalCode().getHouseNumber() != null
									? editDto.getPostalCode().getHouseNumber()
									: proposal.getPostalCode().getHouseNumber())
							.postalCode(editDto.getPostalCode().getPostalCode() != null
									? editDto.getPostalCode().getPostalCode()
									: proposal.getPostalCode().getPostalCode())
							.street(newStreet != null ? newStreet : proposal.getPostalCode().getStreet()).build());
		}
		proposalRepository.save(proposal);
		return buildProposalDto(proposal);
	}

	@Override
	public UserDto removeUser(String name) {
		User user = userRepository.findById(name).orElseThrow(UserNotFoundException::new);
		UserDto result = buildUserDto(user);
		userRepository.delete(user);
		return result;
	}

	@Override
	public ProposalResponseDto removeProposal(Long id) {
		Proposal proposal = proposalRepository.findById(id).orElseThrow(ProposalNotFoundException::new);
		ProposalResponseDto result = buildProposalDto(proposal);
		proposalRepository.deleteById(id);
		return result;
	}

	@Override
	public List<UserDto> findAllUsers() {
		return userRepository.findAll().stream().map(this::buildUserDto).collect(Collectors.toList());
	}

	@Override
	public List<ProposalResponseDto> findAllProposal() {
		return proposalRepository.findAll().stream().map(this::buildProposalDto).collect(Collectors.toList());
	}

	@Override
	public ResponseEntity<UserDto> login(String name) {
		User user = userRepository.findById(name).get();
		String newToken = buildJWT(user);
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-token", newToken);
		return new ResponseEntity<UserDto>(buildUserDto(user), headers, HttpStatus.OK);
	}

	private String buildJWT(User user) {
		String token = null;
		try {
			return token = Jwts.builder().setSubject("User")
					.setExpiration(Date.from(ZonedDateTime.now().plusDays(userConfig.getExpPeriod()).toInstant()))
					.claim("userId", user.getName()).claim("description", user.getDescription())
					.signWith(SignatureAlgorithm.HS256, userConfig.getSecret().getBytes("UTF-8")).compact();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return token;
		}
	}

	@Override
	public ResponseEntity<String> checkJWT(String xToken) {
		String newXToken = null;
		String userId = null;
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(userConfig.getSecret().getBytes("UTF-8"))
					.parseClaimsJws(xToken);
			userId = (String) claims.getBody().get("userId");
			if (!userRepository.existsById(userId)) {
				throw new UserNotFoundException();
			}
			newXToken = Jwts.builder().addClaims(claims.getBody())
					.setExpiration(Date.from(ZonedDateTime.now().plusDays(userConfig.getExpPeriod()).toInstant()))
					.signWith(SignatureAlgorithm.HS256, userConfig.getSecret().getBytes("UTF-8")).compact();
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.CONFLICT);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-token", newXToken);
//		headers.add("X-userId", userId);
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

}
