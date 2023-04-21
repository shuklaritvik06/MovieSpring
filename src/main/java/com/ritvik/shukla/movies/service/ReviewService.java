package com.ritvik.shukla.movies.service;

import com.ritvik.shukla.movies.entity.Movie;
import com.ritvik.shukla.movies.entity.Review;
import com.ritvik.shukla.movies.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    public Review createReview(String body, String imdbId){
        Review review = reviewRepository.insert(new Review(body, LocalDateTime.now(), LocalDateTime.now()));
        mongoTemplate.update(Movie.class)
                .matching(Criteria.where("imdbId").is(imdbId))
                .apply(new Update().push("reviews").value(review))
                .first();
        return review;
    }
}
