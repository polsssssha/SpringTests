package com.example.springTests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-product-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-product-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class IntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testProductList() throws Exception {
        mockMvc.perform(get("/api/product/"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testProductById() throws Exception {
        mockMvc.perform(get("/api/product/2"))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    public void testNotFound() throws Exception {
        mockMvc.perform(get("/api/product/123"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}