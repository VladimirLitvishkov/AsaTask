package testtask.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import testtask.model.City;

public interface CityRepository extends JpaRepository<City, Integer> {

}
