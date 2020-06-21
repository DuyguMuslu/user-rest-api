package com.restapi.controller;

import com.restapi.configuration.LoginAccessDeniedHandler;
import com.restapi.domain.UserDto;
import com.restapi.service.UserService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @MockBean
    private UserService userService;

    @MockBean
    private LoginAccessDeniedHandler loginAccessDeniedHandler;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @WithMockUser(value = "user")
    @Test
    void whenCreateUser_Then201kReturned() throws Exception {
        UserDto dto = new UserDto();
        dto.setFirstName("Dummy");
        dto.setLastName("Dummyson");
        dto.setCurrentAddress("Disneyland");
        given(userService.createUser(dto)).willReturn(dto);
        String userJson = "{\"firstName\": \"Duygu\",\"lastName\": \"Muslu\",\"dateOfBirth\" : \"1982-05-29\", \"currentAddress\": \"Amsterdam\"}";
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        MockHttpServletResponse response = mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
                .content(userJson)).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    void testRetrieveAllUsers()throws Exception {
    }

    @Test
    void retrieveUser() {
    }

    @Test
    void updateUser() {
    }
}