package com.practice.auction;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuctionApplicationTests {

    @Autowired
    private MainController controller;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads()  {
        assertThat(controller).isNotNull();
    }

    @Test
    void getOfferStatus() throws Exception {
        this.mockMvc.perform(get("/offer"))
                .andExpect(status().isOk());
    }

    @Test
    void getBidStatus() throws Exception {
        this.mockMvc.perform(get("/bid").param("id", "1"))
                .andExpect(status().isOk());
    }
}
