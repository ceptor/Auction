package com.practice.auction.repos;

import com.practice.auction.domain.Offer;
import org.springframework.data.repository.CrudRepository;

public interface OfferRepo extends CrudRepository<Offer, Long> {
}
