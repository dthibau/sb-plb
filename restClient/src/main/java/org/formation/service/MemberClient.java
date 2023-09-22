package org.formation.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MemberClient {
	
	private WebClient webClient;
	
	public MemberClient() {
		webClient = WebClient.builder()
		         .baseUrl("http://localhost:8080/api/members")
		         .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
		         .build();
	}

	public Mono<UserDto> callOneMember(long id) {
		
		return webClient.get()
		         .uri("/{id}", id)
		         .retrieve()
		         .bodyToMono(UserDto.class);
		
	}
	
	public Flux<UserDto> callMembers() {
		
		return webClient.get()
		         .retrieve()
		         .bodyToFlux(UserDto.class);
	}
	
	public Mono<UserDto> createMember(UserDto userDto) {

		return webClient.post()
		         .bodyValue(userDto)
		         .retrieve()
		         .bodyToMono(UserDto.class);
		
	}
}
