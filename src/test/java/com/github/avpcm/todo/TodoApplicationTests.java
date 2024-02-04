package com.github.avpcm.todo;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.avpcm.todo.view.TaskContentResource;
import com.github.avpcm.todo.view.UserResource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class TodoApplicationTests {

    @Autowired
    MockMvc mvc;

    ObjectMapper mapper = new ObjectMapper();

    @Test
    void testCommonScenario() throws Exception {

        // register user
        mvc.perform(MockMvcRequestBuilders
                            .post("/auth/register").servletPath("/auth/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(new UserResource("Johny", "qwerty123")))
                            .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // login user
        String token = mvc.perform(MockMvcRequestBuilders
                            .post("/auth/login").servletPath("/auth/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(new UserResource("Johny", "qwerty123")))
                            .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assertions.assertNotNull(token);

        // create task #1
        mvc.perform(MockMvcRequestBuilders
                            .post("/tasks").servletPath("/tasks")
                            .header(Constants.TOKEN_HEADER_NAME, token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(new TaskContentResource("first", "first task")))
                            .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("first"))
                .andExpect(jsonPath("$.description").value("first task"));

        // create task #2
        mvc.perform(MockMvcRequestBuilders
                            .post("/tasks").servletPath("/tasks")
                            .header(Constants.TOKEN_HEADER_NAME, token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(new TaskContentResource("second", "second task")))
                            .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.title").value("second"))
                .andExpect(jsonPath("$.description").value("second task"));

        // create task #3
        mvc.perform(MockMvcRequestBuilders
                            .post("/tasks").servletPath("/tasks")
                            .header(Constants.TOKEN_HEADER_NAME, token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(new TaskContentResource("third", "third task")))
                            .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.title").value("third"))
                .andExpect(jsonPath("$.description").value("third task"));

        // update task #2
        mvc.perform(MockMvcRequestBuilders
                            .put("/tasks/2").servletPath("/tasks/2")
                            .header(Constants.TOKEN_HEADER_NAME, token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(new TaskContentResource("second upd", "second task upd")))
                            .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("second upd"))
                .andExpect(jsonPath("$.description").value("second task upd"));

        // delete task #1
        mvc.perform(MockMvcRequestBuilders
                            .delete("/tasks/1").servletPath("/tasks/1")
                            .header(Constants.TOKEN_HEADER_NAME, token))
                .andExpect(status().isNoContent());

        // get tasks list - expect 2 and 3
        mvc.perform(MockMvcRequestBuilders
                            .get("/tasks").servletPath("/tasks")
                            .header(Constants.TOKEN_HEADER_NAME, token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("second upd"))
                .andExpect(jsonPath("$[0].description").value("second task upd"))
                .andExpect(jsonPath("$[1].title").value("third"))
                .andExpect(jsonPath("$[1].description").value("third task"));

        // logout
        mvc.perform(MockMvcRequestBuilders
                            .post("/auth/logout").servletPath("/auth/logout")
                            .header(Constants.TOKEN_HEADER_NAME, token))
                .andExpect(status().isNoContent());
    }
}
