package org.formation.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import reactor.test.StepVerifier;

@SpringBootTest
public class MemberClientTest {

	@Autowired
	MemberClient memberClient;
	
	@Test
	public void testOneMember() {	

		memberClient.callOneMember(1).doOnNext(u -> {
			assertAll("heading", () -> assertEquals(1, u.getId()));
		}).block();

		StepVerifier.create(memberClient.callOneMember(1))
					.expectNextCount(1)
					.verifyComplete();
					
	}
	
	@Test
	public void testListMember() {	
	
		StepVerifier.create(memberClient.callMembers())
					.expectNextCount(6)
					.expectComplete()
					.verify();
	}
	
	@Test
	public void testCreateMember() {	
	
		UserDto dto = new UserDto();
		dto.setEmail("@."+System.currentTimeMillis());
		dto.setNom("NOM");
		dto.setPrenom("PRENOM");
		dto.setPassword("secret");
		
		StepVerifier.create(memberClient.createMember(dto))
					.expectNextMatches(u -> u.getId() != null)
					.expectComplete()
					.verify();
	}
}
