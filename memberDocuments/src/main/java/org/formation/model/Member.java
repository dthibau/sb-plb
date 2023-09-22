package org.formation.model;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.formation.controller.MemberViews;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;



@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames = {"email"})})
public class Member implements UserDetails {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(MemberViews.List.class)
	private long id;
	
	@NotNull
	@JsonView(MemberViews.List.class)
	private String email;
	@NotNull
    @JsonView(MemberViews.Create.class)
	private String password;
	
	@JsonView(MemberViews.List.class)
	private String nom,prenom;
	
	@JsonView(MemberViews.List.class)
	private int age;
	
	@Temporal(TemporalType.TIMESTAMP)
	@JsonView(MemberViews.List.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private Date registeredDate;
	
	@OneToMany(cascade=CascadeType.ALL,orphanRemoval=true)
	@JsonView(MemberViews.Detail.class)
	private Set<Document> documents = new HashSet<Document>();

	@Transient
	private  Set<GrantedAuthority> grantedAuthorities;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(unique=true)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Date getRegisteredDate() {
		return registeredDate;
	}

	public void setRegisteredDate(Date registeredDate) {
		this.registeredDate = registeredDate;
	}

	public Set<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(Set<Document> documents) {
		this.documents = documents;
	}
	
	public void addDocument(Document document) {
		this.documents.add(document);
	}
	
	@Transient
	public void merge(Member anotherMember) {
		if ( anotherMember.getAge() != 0 ) {
			setAge(anotherMember.getAge());
		}
		if ( anotherMember.getEmail() != null ) {
			setEmail(anotherMember.getEmail());
		}
		if ( anotherMember.getNom() != null ) {
			setNom(anotherMember.getNom());
		}
		if ( anotherMember.getPrenom() != null ) {
			setPrenom(anotherMember.getPrenom());
		}
		if ( anotherMember.getPassword() != null ) {
			setPassword(anotherMember.getPassword());
		}
	}
	
	@Transient
	public String getNomComplet() {
		return getPrenom() + " " + getNom();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Member other = (Member) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if ( grantedAuthorities == null ) {
			grantedAuthorities = new HashSet<>();
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
			if ( id == 1 ) {
				grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
			}
		}
		return grantedAuthorities;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
}
