package com.test.service;

import com.test.repository.PictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("pictureService")
public class PictureServiceImpl implements PictureService {
	private final PictureRepository pictureRepository;

	@Autowired
	public PictureServiceImpl(PictureRepository pictureRepository) {
		this.pictureRepository = pictureRepository;
	}
}
