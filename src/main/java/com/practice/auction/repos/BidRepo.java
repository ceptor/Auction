package com.practice.auction.repos;

import com.practice.auction.domain.Bid;
import org.springframework.data.repository.CrudRepository;

public interface BidRepo extends CrudRepository<Bid, Long> {
}
