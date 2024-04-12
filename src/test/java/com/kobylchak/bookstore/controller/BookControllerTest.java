package com.kobylchak.bookstore.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kobylchak.bookstore.dto.book.BookDto;
import com.kobylchak.bookstore.dto.book.CreateBookRequestDto;
import java.math.BigDecimal;
import lombok.SneakyThrows;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {

    protected static MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders
                          .webAppContextSetup(webApplicationContext)
                          .apply(springSecurity())
                          .build();
    }

    @Test
    @SneakyThrows
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Sql(
            scripts = "classpath:database/books/delete-test-book.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    void createBook_ValidRequest_Success() {
        CreateBookRequestDto createBookRequestDto = new CreateBookRequestDto()
                                                            .setAuthor("test")
                                                            .setTitle("test")
                                                            .setPrice(BigDecimal.valueOf(100))
                                                            .setIsbn("test");
        BookDto expected = new BookDto()
                                   .setAuthor(createBookRequestDto.getAuthor())
                                   .setTitle(createBookRequestDto.getTitle())
                                   .setIsbn(createBookRequestDto.getIsbn())
                                   .setPrice(createBookRequestDto.getPrice());
        String jsonRequest = mapper.writeValueAsString(createBookRequestDto);

        MvcResult mvcResult = mockMvc.perform(
                        post("/api/books")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                                             )
                                      .andExpect(status().isCreated())
                                      .andReturn();

        BookDto actual = mapper.readValue(mvcResult.getResponse().getContentAsString(),
                                          BookDto.class);
        assertNotNull(actual);
        assertNotNull(actual.getId());
        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @Test
    @SneakyThrows
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void updateBook_ValidRequest_Success() {
        CreateBookRequestDto createBookRequestDto = new CreateBookRequestDto()
                                                            .setAuthor("test")
                                                            .setTitle("test")
                                                            .setPrice(BigDecimal.valueOf(100))
                                                            .setIsbn("test");
        BookDto expected = new BookDto()
                                   .setAuthor(createBookRequestDto.getAuthor())
                                   .setTitle(createBookRequestDto.getTitle())
                                   .setIsbn(createBookRequestDto.getIsbn())
                                   .setPrice(createBookRequestDto.getPrice());
        String jsonRequest = mapper.writeValueAsString(createBookRequestDto);

        MvcResult mvcResult = mockMvc.perform(
                        put("/api/books/1")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                                             )
                                      .andExpect(status().is2xxSuccessful())
                                      .andReturn();

        BookDto actual = mapper.readValue(mvcResult.getResponse().getContentAsString(),
                                          BookDto.class);
        assertNotNull(actual);
        assertNotNull(actual.getId());
        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @Test
    @SneakyThrows
    @WithMockUser(username = "user", roles = {"USER"})
    void getAll_ExpectedTenBooks_Success() {
        int expected = 10;
        MvcResult mvcResult = mockMvc.perform(
                        get("/api/books")
                                .contentType(MediaType.APPLICATION_JSON)
                                             )
                                      .andExpect(status().is2xxSuccessful())
                                      .andReturn();
        BookDto[] bookDtos = mapper.readValue(mvcResult.getResponse().getContentAsString(),
                                              BookDto[].class);

        assertEquals(expected, bookDtos.length);
    }
}
