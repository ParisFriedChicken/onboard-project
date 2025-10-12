package com.sebdev.onboard.integration;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.wavefront.WavefrontProperties.Application;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.sebdev.onboard.dto.RegisterPlayerDto;
import com.sebdev.onboard.model.Player;
import com.sebdev.onboard.repository.PlayerRepository;
import com.sebdev.onboard.service.AuthenticationService;
import com.sebdev.onboard.service.PlayerService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Application.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@ComponentScan(basePackages = "com.sebdev.onboard")
public class OnboardControllerIntegrationTest {

	@Autowired
	private MockMvc mvc;

    @MockitoBean
    private AuthenticationService authenticationService;
    
    @MockitoBean
	private PlayerRepository repository;

/*    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {

    	@Bean
        public AuthenticationService authenticationService() {
            return new AuthenticationService();
        }
    }
  */  
    @BeforeEach
    public void setUp() {
        Player fabrice = new Player("Fabrice Poulains", "fp@gmail.com", "fp", "Chateauroux");
        ArrayList<Player> players = new ArrayList<>();
        players.add(fabrice);
        		
        Mockito.when(repository.findByFullName(fabrice.getFullName()))
          .thenReturn(players);
    }

	private void createTestPlayer(String fullName, String email, String password, String city) {
		repository.save(new Player(fullName, email, password, city));
	}

	@Test
	public void givenRegisterPlayerDto_whenSignup_thenStatus200()
	  throws Exception {

	      // --- Données d'entrée ---
        Player mockPlayer = new Player("Fabrice Poulains", "fp@gmail.com", "fp", "Chateauroux");
        
        // --- Comportement mock ---
        when(authenticationService.signup(any(RegisterPlayerDto.class))).thenReturn(mockPlayer);

        // --- JSON à envoyer ---
        String jsonBody = """
            {
                "fullName" : "Fabrice Poulains",
                "email": "fp@gmail.com",
                "password": "fp"
            }
        """;
        
        
	    mvc.perform(post("/auth/signup")
	      .contentType(MediaType.APPLICATION_JSON)
	      .content(jsonBody))
	      .andExpect(status().isOk())
	      .andExpect(content()
	    		  .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(jsonPath("fullName", is("Fabrice Poulains")));
	}

}
