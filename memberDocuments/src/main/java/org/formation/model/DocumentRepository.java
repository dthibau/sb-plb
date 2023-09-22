package org.formation.model;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DocumentRepository extends JpaRepository<Document, Long>{
	/**
	 * Find Documents by their owner
	 */
	@Query("select m.documents from Member m where m = ?1")
	public List<Document> findByOwner(Member member);
	
	@Query("select m.documents from Member m where m.nom = ?1")
	public List<Document> findByOwnerName(String name);
	
	@Query("select m.documents from Member m where m = ?1")
	public Slice<Document> findByOwnerSlice(Member member, Pageable pageable);
	
	@Query("select m.documents from Member m where m.id = ?1")
	public List<Document> findByOwnerId(Long idMember);

}
