package miquido.recruitment.controller;

import miquido.recruitment.exception.NotFoundException;
import miquido.recruitment.service.PersonImportService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PersonImportController.class)
@ExtendWith(MockitoExtension.class)
public class PersonImportControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonImportService personImportService;

    @Test
    public void importPerson_success() throws Exception {
        mockMvc.perform(post("/import/{id}", anyLong()))
                .andExpect(status().isOk());
    }

    @Test
    public void importPerson_fail() throws Exception {
        doThrow(new NotFoundException("Person not found!")).when(personImportService).importPerson(anyLong());

        mockMvc.perform(post("/import/{id}", anyLong()))
                .andExpect(status().is4xxClientError());
    }
}
