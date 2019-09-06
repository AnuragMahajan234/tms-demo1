package org.yash.rms.service;

import java.util.List;
import org.yash.rms.domain.Rating;

public interface RatingService {
	
	List<Rating> findAllRatings();

}
