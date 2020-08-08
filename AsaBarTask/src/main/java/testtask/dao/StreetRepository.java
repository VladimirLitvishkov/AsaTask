package testtask.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import testtask.model.Street;
import testtask.model.StreetID;

public interface StreetRepository extends JpaRepository<Street, StreetID> {

}
