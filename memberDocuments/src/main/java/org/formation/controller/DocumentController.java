package org.formation.controller;

import java.util.List;

import org.formation.model.Document;
import org.formation.model.DocumentRepository;
import org.formation.model.Member;
import org.formation.model.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/members/{memberId}/documents")
public class DocumentController {

	@Autowired
	DocumentRepository documentRepository;
	
	@Autowired
	MemberRepository memberRepository;
	
	@GetMapping
	@JsonView(MemberViews.Detail.class)
	List<Document> findAllDocuments(@PathVariable long memberId) {
		memberRepository.findById(memberId).orElseThrow(() -> new EntityNotFoundException("No such Member"));
		return documentRepository.findByOwnerId(memberId);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void addDoc(@PathVariable long memberId, @Valid @RequestBody Document doc ) {
		Member m = memberRepository.findById(memberId).orElseThrow(() -> new EntityNotFoundException("No such Member"));
		m.addDocument(doc);
		memberRepository.save(m);
	}
}
