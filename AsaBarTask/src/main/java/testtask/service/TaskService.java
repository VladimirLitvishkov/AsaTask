package testtask.service;

import testtask.dto.ProposalRequestDto;
import testtask.dto.ProposalResponseDto;

import java.util.List;

import org.springframework.http.ResponseEntity;

import testtask.dto.ProposalEditDto;
import testtask.dto.UserDto;
import testtask.dto.UserEditDto;

public interface TaskService {
	
	boolean addUser(UserDto userDto);
	
	boolean addProposal(ProposalRequestDto proposalRequestDto);
	
	ResponseEntity<UserDto> login(String name);
	
	ResponseEntity<String> checkJWT(String xToken);
	
	UserDto findUser(String name);
	
	ProposalResponseDto findProposal(Long id);
	
	UserDto editUser(UserEditDto editDto, String name);
	
	ProposalResponseDto editProposal(ProposalEditDto editDto, Long id);
	
	UserDto removeUser(String name);
	
	ProposalResponseDto removeProposal(Long id);
	
	List<UserDto> findAllUsers();
	
	List<ProposalResponseDto> findAllProposal();

}
