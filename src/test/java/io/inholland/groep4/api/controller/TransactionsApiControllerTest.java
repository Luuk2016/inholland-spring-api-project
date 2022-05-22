package io.inholland.groep4.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.inholland.groep4.api.model.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionsApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "test-employee1", password = "password", roles = "EMPLOYEE")
    public void getTransactionsShouldReturnOk() throws Exception {
        this.mockMvc.perform(get("/transactions"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test-employee1", password = "password", roles = "EMPLOYEE")
    public void getSpecificTransactionShouldReturnOk() throws Exception {
        this.mockMvc.perform(get("/transactions/10"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test-user1", password = "password", roles = "USER")
    public void createTransactionShouldReturnOk() throws Exception {
        // Create a new transaction
        Transaction transaction = new Transaction();
        transaction.setSender("USER_ACCOUNT_3_IBAN");
        transaction.setReceiver("USER_ACCOUNT_4_IBAN");
        transaction.setAmount(49.95);
        transaction.setDescription("Test description");

        this.mockMvc.perform(post("/transactions")
                        .content(asJsonString(transaction))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
