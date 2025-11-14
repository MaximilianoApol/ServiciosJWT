package com.example.marketing.Controller;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.marketing.controller.AuthorController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthorControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private AuthorController controller;

	@Test
	void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
	}

	// =================== Básicos ===================

	@Test
	public void getAllPaginatedTest() throws Exception {
		mvc.perform(get("/api/v1/authors")
						.param("page", "0")
						.param("pageSize", "5")
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(greaterThan(0))));
	}

	@Test
	public void getByIdTest() throws Exception {
		mvc.perform(get("/api/v1/authors/1")
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.identificator", is(1))); // Cambiar "id" por "identificator"
	}

	@Test
	public void getByIdNotFoundTest() throws Exception {
		mvc.perform(get("/api/v1/authors/9999")
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(content().string(containsString("Author not found")));
	}

	// =================== Consultas Especializadas ===================

	@Test
	public void getAllOrderByFollowersTest() throws Exception {
		mvc.perform(get("/api/v1/authors/orderByFollowers")
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk());
	}


	@Test
	public void getPotentialSpamTest() throws Exception {
		mvc.perform(get("/api/v1/authors/alerts/potential-spam")
						.param("keyword", "free")
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk());
	}

	// =================== Búsquedas ===================

	@Test
	public void getVerifiedAuthorsTest() throws Exception {
		mvc.perform(get("/api/v1/authors/verified")
						.param("page", "0")
						.param("pageSize", "5")
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(greaterThan(0))));
	}

	@Test
	public void getPriorityInfluencersTest() throws Exception {
		mvc.perform(get("/api/v1/authors/priority-influencers")
						.param("page", "0")
						.param("pageSize", "5")
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(greaterThan(0))));
	}

	@Test
	public void findByUsernameJPQLTest() throws Exception {
		mvc.perform(get("/api/v1/authors/search")
						.param("username", "john")
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void getByFollowerCountRangeTest() throws Exception {
		mvc.perform(get("/api/v1/authors/followers-range")
						.param("min", "1000")
						.param("max", "100000")
						.param("page", "0")
						.param("pageSize", "5")
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(greaterThan(0))));
	}



}

