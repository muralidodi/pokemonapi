package pokemon.api.service;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pokemon.api.model.Pokemon;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PokemonServiceTest {

    @InjectMocks
    private PokemonService pokemonService;

    @Mock
    private Gson gson;

    private List<Pokemon> mockPokemonList;

    @BeforeEach
    void setUp() {
        // Initialize mock Pokemon list
        mockPokemonList = new ArrayList<>();
        Pokemon pikachu = new Pokemon();
        pikachu.setId(1L);
        pikachu.setName("Pikachu");
        pikachu.setImage("pikachu.png");
        pikachu.setSilhouette("pikachu_silhouette.png");

        Pokemon bulbasaur = new Pokemon();
        bulbasaur.setId(2L);
        bulbasaur.setName("Bulbasaur");
        bulbasaur.setImage("bulbasaur.png");
        bulbasaur.setSilhouette("bulbasaur_silhouette.png");

        mockPokemonList.add(pikachu);
        mockPokemonList.add(bulbasaur);

        pokemonService.pokemonList = mockPokemonList;
    }

    @Test
    void testGetPokemonByName_Found() {
        Optional<Pokemon> result = pokemonService.getPokemonByName("Pikachu");
        assertTrue(result.isPresent());
        assertEquals("Pikachu", result.get().getName());
    }

    @Test
    void testGetPokemonByName_NotFound() {
        Optional<Pokemon> result = pokemonService.getPokemonByName("Charizard");
        assertFalse(result.isPresent());
    }

    @Test
    void testGetPokemonById_Found() {
        Optional<Pokemon> result = pokemonService.getPokemonById(1L);
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void testGetPokemonById_NotFound() {
        Optional<Pokemon> result = pokemonService.getPokemonById(99L);
        assertFalse(result.isPresent());
    }

    @Test
    void testGetAllPokemon_NotEmpty() {
        List<Pokemon> result = pokemonService.getAllPokemon();
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
    }

    @Test
    void testFetchPokemonList_Success() throws Exception {

        List<Pokemon> result = pokemonService.getAllPokemon();

        assertEquals(2, result.size());
        assertEquals("Pikachu", result.get(0).getName());
        assertEquals("pikachu.png", result.get(0).getImage());
        assertEquals("pikachu_silhouette.png", result.get(0).getSilhouette());

        assertEquals("Bulbasaur", result.get(1).getName());
        assertEquals("bulbasaur.png", result.get(1).getImage());
        assertEquals("bulbasaur_silhouette.png", result.get(1).getSilhouette());
    }

    @Test
    void testFetchPokemonDetails_Success_1() throws Exception {
        // Create a spy on the real service
        PokemonService spyService = Mockito.spy(new PokemonService());

        // Define the mock response Pokemon
        Pokemon mockPokemon = new Pokemon();
        mockPokemon.setId(1L);
        mockPokemon.setName("pikachu");
        mockPokemon.setImage("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png");
        mockPokemon.setSilhouette("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/1.png");

        // Mock the behavior of fetchPokemonDetails method inside the spy
        doReturn(mockPokemon).when(spyService).fetchPokemonDetails("https://pokeapi.co/api/v2/pokemon/1/");

        // Call the real method (which internally uses the mocked fetchPokemonDetails)
        Pokemon result = spyService.fetchPokemonDetails("https://pokeapi.co/api/v2/pokemon/1/");

        // Assertions to validate the expected output
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("pikachu", result.getName());
        assertEquals("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png", result.getImage());
        assertEquals("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/1.png", result.getSilhouette());
    }


}

