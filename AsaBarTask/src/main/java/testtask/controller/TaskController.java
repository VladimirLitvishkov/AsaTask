package testtask.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import testtask.dto.ProposalRequestDto;
import testtask.dto.ProposalResponseDto;
import testtask.dto.ProposalEditDto;
import testtask.dto.UserDto;
import testtask.dto.UserEditDto;
import testtask.service.TaskService;

@RestController
@RequestMapping("/task")
@CrossOrigin(origins = "*", exposedHeaders = "X-token")
public class TaskController {
	
	@Autowired
	TaskService service;

	@PostMapping("/user")
	public UserDto addUser(@RequestBody UserDto userDto) {
		return service.addUser(userDto);
	}
	
	@PostMapping("/login")
	public ResponseEntity<UserDto> login(Principal principal) {
		return service.login(principal.getName());
	}
	
	@PostMapping("/proposal")
	public ProposalResponseDto addProposal(@RequestBody ProposalRequestDto proposalRequestDto) {
		return service.addProposal(proposalRequestDto);
	}
	
	@GetMapping("/user/{name}")
	public UserDto findUser(@PathVariable String name) {
		return service.findUser(name);
	}
	
	@GetMapping("/proposal/{id}")
	public ProposalResponseDto findProposal(@PathVariable Long id) {
		return service.findProposal(id);
	}
	
	@PutMapping("/user/{name}")
	public UserDto editUser(@RequestBody UserEditDto editDto, @PathVariable String name) {
		return service.editUser(editDto, name);
	}
	
	@PutMapping("/proposal/{id}")
	public ProposalResponseDto editProposal(@RequestBody ProposalEditDto editDto, @PathVariable Long id) {
		return service.editProposal(editDto, id);
	}
	
	@DeleteMapping("/user/{name}")
	public UserDto removeUser(@PathVariable String name) {
		return service.removeUser(name);
	}
	
	@DeleteMapping("/proposal/{id}")
	public ProposalResponseDto removeProposal(@PathVariable Long id) {
		return service.removeProposal(id);
	}
	
	@GetMapping("/users")
	public List<UserDto> findAllUsers() {
		return service.findAllUsers();
	}
	
	@GetMapping("/proposals")
	public List<ProposalResponseDto> findAllProposal() {
		return service.findAllProposal();
	}
}
