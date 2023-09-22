package org.formation.controller;

import java.util.List;

import org.formation.model.Member;
import org.formation.model.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/members")
public class MemberController {

	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@JsonView(MemberViews.List.class)
	public List<Member> findMembers(@RequestParam(required = false) String q) {
		if ( q == null || q.isBlank() )
			return memberRepository.findAll();
		return memberRepository.findByNomContainsOrPrenomContainsAllIgnoreCase(q, q);
	}
	@GetMapping("/{id}")
	@JsonView(MemberViews.Detail.class)
	@ResponseStatus(code = HttpStatus.OK)
	public Member findById(@PathVariable long id) throws EntityNotFoundException {
		return memberRepository.fullLoad(id).orElseThrow(() -> new EntityNotFoundException("No such member "+id));
	}

    @PostMapping
    @JsonView(MemberViews.Create.class)
    @ResponseStatus(HttpStatus.CREATED)
    public Member create(@Valid @RequestBody Member member) {
    	member.setPassword(passwordEncoder.encode(member.getPassword()));
      return memberRepository.save(member);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id) {
      Member member = memberRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No such member "+id));
      memberRepository.delete(member);

      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @JsonView(MemberViews.List.class)
    public void replace(@PathVariable long id, @Valid @RequestBody Member member) {
        memberRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No such member "+id));
        member.setId(id);
        memberRepository.save(member);
    }
    @PatchMapping("/{id}")
    public void partialUpdate(@PathVariable long id, @RequestBody Member member) {
    	Member originalMember = memberRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No such member "+id));
        
    	originalMember.merge(member);
    	
        memberRepository.save(originalMember);
    	
    }
}
