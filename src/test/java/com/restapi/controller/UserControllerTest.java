package com.restapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restapi.configuration.LoginAccessDeniedHandler;
import com.restapi.domain.ResponseDto;
import com.restapi.domain.UserDto;
import com.restapi.service.UserService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

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

    JacksonTester<ResponseDto> jacksonTester;


    @Before
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @WithMockUser()
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

    @WithMockUser()
    @Test
    void  whenRetrieveAllUsersThen200Returned()throws Exception {
        List<UserDto> userDtoList = Stream.generate(UserDto::new).limit(2).collect(Collectors.toList());
        long counter= 1;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        for (UserDto userDto : userDtoList) {
            userDto.setUserID(counter);
            userDto.setFirstName("Duygu"+ counter);
            userDto.setLastName("Muslu"+ counter);
            userDto.setDateOfBirth(format.parse ( "2009-12-31" ));
            userDto.setCurrentAddress("Amsterdam"+ counter++);
        }
        given(userService.retrieveAllUsers()).willReturn(userDtoList);
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        String userJson = "{\"userID\" : 1 ,\"firstName\" \"Duygu\",\"lastName\": \"Muslu\",\"dateOfBirth\" : \"1982-05-29\", \"currentAddress\": \"Amsterdam\"}";
        userJson = userJson+",{\"userID\" : 2 ,\"firstName\" \"Alex\",\"lastName\": \"Wender\",\"dateOfBirth\" : \"1980-01-31\", \"currentAddress\": \"Utrecht\"}";
        MockHttpServletResponse response = mockMvc.perform(get("/users").contentType(MediaType.APPLICATION_JSON)
                .content(userJson)).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        verify(userService).retrieveAllUsers();
    }

    @WithMockUser()
    @Test
    void whenRetrieveUser_Then200Returned() throws Exception {
        UserDto dto = new UserDto();
        dto.setFirstName("Dummy");
        dto.setLastName("Dummyson");
        dto.setCurrentAddress("Disneyland");
        dto.setUserID(1L);
        given(userService.retrieveUser(1L)).willReturn(dto);
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        JacksonTester.initFields(this, new ObjectMapper());
        MockHttpServletResponse response = mockMvc.perform(get("/users/1").contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jacksonTester.write(ResponseDto.builder().status("200 OK").message("Success").body(dto).build()).getJson());
    }

    @WithMockUser("admin")
    @Test
    void whenUpdateUser_Then202Returned() throws Exception{
        UserDto dto = new UserDto();
        dto.setUserID(1L);
        dto.setCurrentAddress("Rotterdam");
        given(userService.updateUserAddress(dto.getCurrentAddress(),1L)).willReturn(dto);
        MockHttpServletResponse response = mockMvc.perform(put("/users/1/address/Rotterdam").contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
}