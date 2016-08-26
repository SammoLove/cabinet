package com.test.repository;

import com.test.model.Picture;
import org.springframework.data.repository.CrudRepository;

//@Repository
public interface PictureRepository extends CrudRepository<Picture, Long> {
}