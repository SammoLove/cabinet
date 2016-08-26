package com.test.model;

import javax.persistence.*;

@Entity
@Table
public class Picture implements TransferObject {
	private static final long serialVersionUID = 2L;

	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@SequenceGenerator(name = "picture_id_seq", sequenceName = "picture_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "picture_id_seq")
	private long id;

	@Column(name = "user_id", nullable = false)
	private long userId;

	@Column(name = "mime_type", nullable = false, length = 60)
	private String mimeType;

	@Column(name = "image_file", nullable = false)
	private byte[] imageFile;

	@ManyToOne
	@JoinColumn(name = "userId")
	private Customer customer;

	protected Picture() {
	}

	public Picture(long userId, String mimeType, byte[] imageFile) {
		this.userId = userId;
		this.mimeType = mimeType;
		this.imageFile = imageFile;
	}

	@Override
	public long getId() {
		return id;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public byte[] getImageFile() {
		return imageFile;
	}

	public void setImageFile(byte[] imageFile) {
		this.imageFile = imageFile;
	}

	public Customer getCustomer() {
		return customer;
	}
}