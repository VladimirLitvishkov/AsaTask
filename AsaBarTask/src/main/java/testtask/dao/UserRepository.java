package testtask.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import testtask.model.User;

public interface UserRepository extends JpaRepository<User, String>{

}
