package io.inholland.groep4.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.inholland.groep4.api.model.Transaction;
import io.inholland.groep4.api.repository.TransactionRepository;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionsApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    @WithMockUser(username = "test-employee1", password = "password", roles = "EMPLOYEE")
    public void getTransactionsAsEmployeeShouldReturnOk() throws Exception {
        this.mockMvc.perform(get("/transactions"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser(username = "test-user1", password = "password", roles = "USER")
    public void getTransactionsAsUserShouldReturnOk() throws Exception {
        this.mockMvc.perform(get("/transactions"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser(username = "test-employee3", password = "password", roles = "EMPLOYEE")
    public void getTransactionsAsNonexistentEmployeeShouldReturnNotFound() throws Exception {
        this.mockMvc.perform(get("/transactions/"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string("422 UNPROCESSABLE_ENTITY \"Username not found\""));
    }

    @Test
    @WithMockUser(username = "test-user3", password = "password", roles = "USER")
    public void getTransactionsAsNonexistentUserShouldReturnNotFound() throws Exception {
        this.mockMvc.perform(get("/transactions/"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string("422 UNPROCESSABLE_ENTITY \"Username not found\""));
    }

    @Test
    @WithMockUser(username = "test-employee1", password = "password", roles = "EMPLOYEE")
    public void getSpecificTransactionShouldReturnOk() throws Exception {
        this.mockMvc.perform(get("/transactions/10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser(username = "test-employee1", password = "password", roles = "EMPLOYEE")
    public void getSpecificNonexistentTransactionShouldReturnNotFound() throws Exception {
        this.mockMvc.perform(get("/transactions/99"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string("404 NOT_FOUND \"No transactions found\""));
    }

    @Test
    @WithMockUser(username = "test-user1", password = "password", roles = "USER")
    public void getSpecificTransactionAsUserWithoutPrivilegeShouldReturnForbidden() throws Exception {
        this.mockMvc.perform(get("/transactions/10"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string("403 FORBIDDEN \"Transaction does not belong to owner\""));
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
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser(username = "test-user1", password = "password", roles = "USER")
    public void createTransactionWithInvalidIBANShouldReturnForbidden() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setSender("USER_ACCOUNT_4_IBAN");
        transaction.setReceiver("USER_ACCOUNT_3_IBAN");
        transaction.setAmount(49.95);
        transaction.setDescription("Test description");

        this.mockMvc.perform(post("/transactions")
                .content(asJsonString(transaction))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "test-employee1", password = "password", roles = "EMPLOYEE")
    public void getNonexistentTransactionsShouldReturnNotFound() throws Exception {
        this.transactionRepository.deleteAll();
        this.mockMvc.perform(get("/transactions"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string("404 NOT_FOUND \"No transactions found\""));;
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
