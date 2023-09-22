package org.formation.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.formation.model.Document;
import org.formation.model.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

@JsonTest
public class MemberViewsTest {

	@Autowired
	private JacksonTester<Member> json;

	private Member aMember;
	private Document doc1;

	@BeforeEach
	public void setUp() {
		aMember = new Member();
		aMember.setId(1);
		aMember.setEmail("dd@dd.fr");
		aMember.setPassword("secret");
		doc1 = new Document();
		doc1.setName("Toto");
		aMember.addDocument(doc1);
	}

	@Test
	public void testSerialize() throws Exception {

		assertThat(this.json.forView(MemberViews.List.class).write(aMember))
		        .hasJsonPathStringValue("@.email")
				.hasEmptyJsonPathValue("@.documents")
				.hasEmptyJsonPathValue("@.password")
				.extractingJsonPathStringValue("@.email").isEqualTo("dd@dd.fr");

		assertThat(this.json.forView(MemberViews.Detail.class).write(aMember))
		       .hasJsonPathArrayValue("@.documents", doc1);

	}
	
	@Test
	public void testDeserialize() throws Exception {

		 String content = "{\"id\":\"1\",\"email\":\"dd@dd.fr\"}";
		 assertThat(this.json.parse(content))
		                 .isEqualTo(aMember);
		 
		 assertThat(this.json.parseObject(content).getEmail()).isEqualTo("dd@dd.fr");

	}
}
