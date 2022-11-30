package com.practice.auction;

import com.practice.auction.domain.Bid;
import com.practice.auction.domain.Offer;
import com.practice.auction.repos.OfferRepo;
import com.practice.auction.repos.BidRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * CRUD operation with offers and bids
 */
@RestController
public class MainController {

    private final OfferRepo offerRepo;
    private final BidRepo bidRepo;

    public MainController(OfferRepo offerRepo, BidRepo bidRepo) {
        this.offerRepo = offerRepo;
        this.bidRepo = bidRepo;
    }

    /**
     * Build list of non-closed offers
     *
     * @return list of non-closed offers
     */
    @GetMapping("/offer")
    @ResponseBody
    public List<Offer> getOffer() {
        Iterable<Offer> offers = offerRepo.findAll();
        List<Offer> returnOffers = new ArrayList<>();

        for (Offer offer : offers) {
            if (!offer.getClosed()) {
                returnOffers.add(offer);
            }
        }
        return returnOffers;
    }

    /**
     * Create offer
     *
     * @param offer with parameters:
     *              -title (string)
     *              -description (string)
     *              -offer price in € (number)
     *              -password
     * @return new offer
     */
    @PostMapping("/offer")
    public String createOffer(@RequestBody Offer offer) {
        offerRepo.save(offer);
        return Statuses.OK.name();
    }

    /**
     * Close offer
     *
     * @param offer with offer id, selected bid id and password
     * @return Ok and close offer, if and only if password is matched, fail otherwise
     */
    @DeleteMapping("/offer")
    public String closeOffer(@RequestBody Offer offer) {
        Optional<Offer> optional = offerRepo.findById(offer.getId());
        if (optional.isPresent() && Objects.equals(optional.get().getPassword(), offer.getPassword())) {
            Offer entity = optional.get();
            entity.setClosed(true);
            offerRepo.save(entity);
            return Statuses.OK.name();
        }
        return Statuses.FAIL.name();
    }

    /**
     * Get bid
     *
     * @param id is a Bid id
     * @return corresponding bid
     */
    @GetMapping("/bid")
    @ResponseBody
    public Bid getBidById(@RequestParam Long id) {
        return bidRepo.findById(id).isPresent() ? bidRepo.findById(id).get() : null;
    }

    /**
     * Create a bid
     *
     * @param bid with parameters:
     *            - offer id
     *            - buyer name
     *            - bid password
     *            - amount in €
     * @return new bid
     */
    @PostMapping("/bid")
    public String createBid(@RequestBody Bid bid) {
        bidRepo.save(bid);
        return Statuses.OK.name();
    }

    /**
     * Delete bid
     *
     * @param bid id and password is required
     * @return Ok and deletes bid if offer is still open and password matches
     */
    @DeleteMapping("/bid")
    public String deleteBid(@RequestBody Bid bid) {
        Optional<Offer> offer = offerRepo.findById(bid.getOfferId());
        if (offer.isPresent() && !offer.get().getClosed()) {
            Bid cBid = bidRepo.findById(bid.getId()).isPresent() ? bidRepo.findById(bid.getId()).get() : null;
            if (cBid != null) {
                if (cBid.getBidPassword().equals(bid.getBidPassword())) {
                    bidRepo.delete(cBid);
                    return Statuses.OK.name();
                }
            }
        }
        return Statuses.FAIL.name();
    }

    /**
     * Exception page beautify method
     * @param ex is original exception
     * @return new response entity
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleMissingRequestBody(HttpMessageNotReadableException ex) {
        return new ResponseEntity<>(Statuses.FAIL + ": " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
