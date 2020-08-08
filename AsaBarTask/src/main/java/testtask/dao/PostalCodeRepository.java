package testtask.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import testtask.model.PostalCode;
import testtask.model.PostalID;

public interface PostalCodeRepository extends JpaRepository<PostalCode, PostalID> {

}
