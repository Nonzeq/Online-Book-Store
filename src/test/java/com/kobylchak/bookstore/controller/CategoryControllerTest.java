package com.kobylchak.bookstore.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kobylchak.bookstore.dto.category.CategoryDto;
import com.kobylchak.bookstore.dto.category.CreateCategoryRequestDto;
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
class CategoryControllerTest {
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
            scripts = "classpath:database/categories/delete-test-category.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    void createCategory_WithValidRequest_Created() {
        CreateCategoryRequestDto requestDto = new CreateCategoryRequestDto().setName("test");

        CategoryDto expected = new CategoryDto()
                                       .setName(requestDto.getName());
        String jsonRequest = mapper.writeValueAsString(requestDto);

        MvcResult mvcResult = mockMvc.perform(
                        post("/api/categories")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                                             )
                                      .andExpect(status().isCreated())
                                      .andReturn();

        CategoryDto actual = mapper.readValue(mvcResult.getResponse().getContentAsString(),
                                              CategoryDto.class);
        assertNotNull(actual);
        assertNotNull(actual.getId());
        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @Test
    @SneakyThrows
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void updateCategory_ValidRequest_Success() {
        CreateCategoryRequestDto createBookRequestDto = new CreateCategoryRequestDto()
                                                                .setName("test");
        CategoryDto expected = new CategoryDto()
                                       .setName(createBookRequestDto.getName());
        String jsonRequest = mapper.writeValueAsString(createBookRequestDto);

        MvcResult mvcResult = mockMvc.perform(
                        put("/api/categories/1")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                                             )
                                      .andExpect(status().is2xxSuccessful())
                                      .andReturn();

        CategoryDto actual = mapper.readValue(mvcResult.getResponse().getContentAsString(),
                                              CategoryDto.class);
        assertNotNull(actual);
        assertNotNull(actual.getId());
        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @Test
    @SneakyThrows
    @WithMockUser(username = "user", roles = {"USER"})
    void getAll_ExpectedFiveCategories_Success() {
        int expected = 5;
        MvcResult mvcResult = mockMvc.perform(
                        get("/api/categories")
                                .contentType(MediaType.APPLICATION_JSON)
                                             )
                                      .andExpect(status().is2xxSuccessful())
                                      .andReturn();
        CategoryDto[] bookDtos = mapper.readValue(mvcResult.getResponse().getContentAsString(),
                                                  CategoryDto[].class);

        assertEquals(expected, bookDtos.length);
    }
}
