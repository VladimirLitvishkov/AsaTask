package testtask.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import testtask.model.Proposal;

public interface ProposalRepository extends JpaRepository<Proposal, Long> {

}
