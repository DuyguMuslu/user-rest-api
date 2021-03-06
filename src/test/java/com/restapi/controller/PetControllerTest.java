package com.restapi.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restapi.configuration.LoginAccessDeniedHandler;
import com.restapi.domain.PetDto;
import com.restapi.domain.ResponseDto;
import com.restapi.service.PetService;
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

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@WebMvcTest(PetController.class)
class PetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @MockBean
    private PetService petService;

    @MockBean
    private LoginAccessDeniedHandler loginAccessDeniedHandler;

    JacksonTester<ResponseDto> jacksonTester;


    @Before
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @WithMockUser
    @Test
    void whenCreatePet_Then201Returned() throws Exception {
        PetDto dto = new PetDto();
        dto.setName("Bailey");
        dto.setAge(3);
        given(petService.createPet(dto)).willReturn(dto);
        String petJson = "{\"name\": \"Daisy\",\"age\": 1 }";
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        MockHttpServletResponse response = mockMvc.perform(post("/pets").contentType(MediaType.APPLICATION_JSON)
                .content(petJson)).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    void  whenRetrieveAllPetsThen200Returned()throws Exception {
        List<PetDto> petDtoList = Stream.generate(PetDto::new).limit(2).collect(Collectors.toList());
        long counter= 1;
        for (PetDto petDto : petDtoList) {
            petDto.setPetID(counter);
            petDto.setAge(3);
            petDto.setName("PET-" + counter++);
        }
        given(petService.retrieveAllPets()).willReturn(petDtoList);
        String petJson = "{\"petID\": 1, \"name\": \"Daisy\",\"age\": 1 },{\"petID\": 2,\"name\": \"Tarkan\",\"age\": 2 }";
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        MockHttpServletResponse response = mockMvc.perform(get("/pets").contentType(MediaType.APPLICATION_JSON)
                .content(petJson)).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        verify(petService).retrieveAllPets();
    }

    @Test
    void whenRetrievePet_Then200Returned() throws Exception {
        PetDto petDto = new PetDto();
        petDto.setName("Bailey");
        petDto.setAge(3);
        petDto.setPetID(1L);
        given(petService.retrievePet(1L)).willReturn(petDto);
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        JacksonTester.initFields(this, new ObjectMapper());
        MockHttpServletResponse response = mockMvc.perform(get("/pets/1").contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jacksonTester.write(ResponseDto.builder().status("200 OK").message("Success").body(petDto).build()).getJson());
    }

    @Test
    void whenUpdatePet_Then202Returned() throws Exception {
        PetDto petDto = new PetDto();
        petDto.setName("Joei");
        petDto.setAge(3);
        petDto.setPetID(1L);
        given(petService.updatePet(petDto.getName(),petDto.getAge(),1L)).willReturn(petDto);
         MockHttpServletResponse response = mockMvc.perform(put("/pets/1/name/Joei/age/7").contentType(MediaType.APPLICATION_JSON))
                 .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
}