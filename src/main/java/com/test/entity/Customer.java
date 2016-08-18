package com.test.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Entity
@Table
public class Customer implements TransferObject {
	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO) //or try just that line
	@SequenceGenerator(name = "customer_id_seq", sequenceName = "customer_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_id_seq")
	private long id;

	@Column(nullable = false, length = 30)
	private String name;

	@Column(nullable = false, length = 30)
	private String surname;

	@NotNull
	private Date birth;

	@NotNull
	private int role;

	@Column(unique = true, nullable = false, length = 90)
	private String email;

	@Column(nullable = false, length = 12)
	private String password;

	@Transient
	private String passwordConfirm;

	@Column(nullable = false, length = 12)
	private String salt;

	@Column
	private String data;

	@JsonIgnore
	@OneToMany(mappedBy = "userId", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private Set<Picture> pictures;

	protected Customer() {
	}

	public Customer(String name, String surname, Date birth, int role, String email, String password, String salt, String data) {
		this.name = name;
		this.surname = surname;
		this.birth = birth;
		this.role = role;
		this.email = email;
		this.password = password;
		this.salt = salt;
		this.data = data;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	@Override
	public long getId() {
		return id;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

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

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public Set<Picture> getPictures() {
		return pictures;
	}

	public void setPictures(Set<Picture> pictures) {
		this.pictures = pictures;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("Customer{");
		sb.append("id=").append(id);
		sb.append(", name='").append(name).append('\'');
		sb.append(", surname='").append(surname).append('\'');
		sb.append(", birth=").append(birth);
		sb.append(", email='").append(email).append('\'');
		sb.append(", password='").append(password).append('\'');
		sb.append(", salt='").append(salt).append('\'');
		sb.append(", data='").append(data).append('\'');
		sb.append('}');
		return sb.toString();
	}
}