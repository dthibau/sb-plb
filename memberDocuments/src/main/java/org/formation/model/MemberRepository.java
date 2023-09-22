package org.formation.model;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {

	/**
	 * Le membre ayant un email particulier.
	 * 
	 * @param email
	 * @return
	 */
	public Optional<Member> findByEmail(String email);

	/**
	 * Find Members whose owner name contains the specified string
	 * 
	 * @param partialName Any alphabetic string.
	 * @return The list of matching Members - always non-null, but may be empty.
	 */
	public List<Member> findByNomContainsOrPrenomContainsAllIgnoreCase(String partialNom, String partialPrenom);
	
	
	/**
	 * Return a Member via its email and password
	 * 
	 * @param email
	 * @param password
	 * @return
	 */
	public Optional<Member> findByEmailAndPassword(String email, String password);

	@Query("select m from Member m left join fetch m.documents where m.id= :id")
	public Optional<Member> fullLoad(@Param("id") Long id);
	
}
