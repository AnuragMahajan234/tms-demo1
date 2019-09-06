package org.yash.rms.dao;

import java.util.List;

import org.yash.rms.domain.Rating;

public interface RatingDao {

	List<Rating> findAllRatings();
}
