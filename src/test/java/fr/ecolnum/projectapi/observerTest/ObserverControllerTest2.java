package fr.ecolnum.projectapi.observerTest;

import fr.ecolnum.projectapi.DTO.ObserverDto;
import fr.ecolnum.projectapi.controller.ObserverController;
import fr.ecolnum.projectapi.security.SecurityConfig;
import fr.ecolnum.projectapi.service.CandidateService;
import fr.ecolnum.projectapi.service.ObserverService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.security.test.context.support.WithMockUser;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ObserverController.class)
@ImportAutoConfiguration(classes = SecurityConfig.class)
public class ObserverControllerTest2 {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ObserverService observerService;

    private ObserverDto observerDto;
    @BeforeEach
    public void setup() {
        observerDto = new ObserverDto(0, "prenom", "nom");
    }

    @Test
    @WithMockUser(roles="ADMIN")
    public void testGetAllObservers() throws Exception {
        when(observerService.getAllObservers()).thenReturn(Collections.singletonList(observerDto));
        mockMvc.perform(get("/api/admin/observer"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].lastName").value("nom"));

    }
}
