package com.example.todoback;

import com.example.todoback.controller.TodoController;
import com.example.todoback.enums.PriorityLevel;
import com.example.todoback.models.DTOs.TodoItemDTO;
import com.example.todoback.models.TodoItem;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@AutoConfigureMockMvc
class ToDoBackApplicationTests {

    @Autowired
    private TodoController todoController;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void contextLoads() {
        assertThat(todoController).isNotNull();
    }

    @Test
    void getShouldReturnObject() throws Exception {

        this.mockMvc.perform(get("/api/todos"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("content")));

    }

    @Test
    void postShouldReturnObject() throws Exception {

        TodoItemDTO dto = TodoItemDTO.builder()
                .text("test text")
                .done(false)
                .priority(PriorityLevel.High)
                .dueDate(null)
                .build();

        String content = mapper.writeValueAsString(dto);

        this.mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("test text")));


    }

    @Test
    void deleteShouldReturnObject() throws Exception {
        TodoItemDTO dto = TodoItemDTO.builder()
                .text("test text")
                .done(false)
                .priority(PriorityLevel.High)
                .dueDate(null)
                .build();



        String content = mapper.writeValueAsString(dto);

        System.out.println(content);

        MvcResult res = this.mockMvc.perform(post("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)).andReturn();


        String json = res.getResponse().getContentAsString();

        JSONObject obj = new JSONObject(json);

        this.mockMvc.perform(delete("/api/todos/" + obj.getString("id")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("test text")));



    }

    @Test
    void putShouldReturnObject() throws Exception {
        TodoItemDTO dto = TodoItemDTO.builder()
                .text("test text")
                .done(false)
                .priority(PriorityLevel.High)
                .dueDate(null)
                .build();



        String content = mapper.writeValueAsString(dto);

        System.out.println(content);

        MvcResult res = this.mockMvc.perform(post("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)).andReturn();


        String json = res.getResponse().getContentAsString();

        JSONObject obj = new JSONObject(json);

        TodoItemDTO update = TodoItemDTO.builder()
                .text("Updated text")
                .done(false)
                .priority(PriorityLevel.High)
                .dueDate(null)
                .build();


        this.mockMvc.perform(post("/api/todos/" + obj.getString("id"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(update)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Updated text")));
    }





}
