package org.formation.service;

import org.formation.model.Document;
import org.formation.model.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemberService implements UserDetailsService {

	private final MemberRepository memberRepository;
	
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
	
	public void addDocumentToAllMembers(Document doc) {
		
		memberRepository.findAll().forEach(m -> {
			m.addDocument(doc);
			memberRepository.save(m);
		});
	}

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return memberRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("No such user " + username));
	}
	
}
