package com.practice.auction.domain;

import javax.persistence.*;

@Entity
public class Bid {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private Long offerId;
    private String name;
    private String bidPassword;
    private Integer amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "offerId", insertable = false, updatable = false)
    private Offer offer;

    public Bid() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Bid(Long offerId, String name, String bidPassword, Integer amount) {
        this.offerId = offerId;
        this.name = name;
        this.bidPassword = bidPassword;
        this.amount = amount;
    }

    public Long getOfferId() {
        return offerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBidPassword() {
        return bidPassword;
    }

    public void setBidPassword(String bidPassword) {
        this.bidPassword = bidPassword;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
