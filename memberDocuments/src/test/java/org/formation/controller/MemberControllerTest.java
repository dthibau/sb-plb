package org.formation.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.formation.model.Member;
import org.formation.model.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(MemberController.class)
public class MemberControllerTest {

	
	@Autowired
	MockMvc mvc;
	
	@MockBean
	MemberRepository memberRepository;
	
	@MockBean
	PasswordEncoder passwordEncoder;
	
	List<Member> myList;
	
	@BeforeEach
	public void setUp() {
		myList = new ArrayList<>();
		myList.add(new Member());
		myList.add(new Member());
	}
	
	@Test
	@WithMockUser
	public void test_GetAll() throws Exception {
		
		when(memberRepository.findAll()).thenReturn(myList);
		
		this.mvc.perform(get("/api/members")).andExpect(status().isOk());
		
		verify(memberRepository).findAll();
	}
}
