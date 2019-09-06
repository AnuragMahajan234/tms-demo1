package org.yash.rms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.RatingDao;
import org.yash.rms.domain.Rating;
import org.yash.rms.service.RatingService;

@Service("ratingService")
public class RatingServiceImpl implements RatingService{

	@Autowired@Qualifier("ratingDao")
	RatingDao ratingDao; 
	
	public List<Rating> findAllRatings() {
		
		return ratingDao.findAllRatings();
	}

	
}
