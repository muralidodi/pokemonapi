package pokemon.api.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pokemon.api.model.Pokemon;
import pokemon.api.service.PokemonService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(PokemonController.class)
class PokemonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PokemonService pokemonService;

    private Pokemon pokemon1;
    private Pokemon pokemon2;

    @BeforeEach
    void setUp() {
        pokemon1 = new Pokemon();
        pokemon1.setId(1L);
        pokemon1.setName("Pikachu");

        pokemon2 = new Pokemon();
        pokemon2.setId(2L);
        pokemon2.setName("Charizard");
    }

    @Test
    void testGetAllPokemon() throws Exception {
        List<Pokemon> allPokemon = Arrays.asList(pokemon1, pokemon2);
        when(pokemonService.getAllPokemon()).thenReturn(allPokemon);

        mockMvc.perform(get("/api/pokemon"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Pikachu"))
                .andExpect(jsonPath("$[1].name").value("Charizard"));
    }

    @Test
    void testGetPokemonById_found() throws Exception {
        when(pokemonService.getPokemonById(1L)).thenReturn(Optional.of(pokemon1));

        mockMvc.perform(get("/api/pokemon/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Pikachu"));
    }

    @Test
    void testGetPokemonById_notFound() throws Exception {
        when(pokemonService.getPokemonById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/pokemon/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetPokemonByName_found() throws Exception {
        when(pokemonService.getPokemonByName("Pikachu")).thenReturn(Optional.of(pokemon1));

        mockMvc.perform(get("/api/pokemon/name/Pikachu"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Pikachu"));
    }

    @Test
    void testGetPokemonByName_notFound() throws Exception {
        when(pokemonService.getPokemonByName("Unknown")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/pokemon/name/Unknown"))
                .andExpect(status().isNotFound());
    }
}
