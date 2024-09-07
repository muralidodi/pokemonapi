package pokemon.api.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

import pokemon.api.model.Pokemon;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PokemonService {

    List<Pokemon> pokemonList  = new ArrayList();

    public Optional<Pokemon> getPokemonByName(String name) {
        return pokemonList.stream()
                .filter(pokemon -> pokemon.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    public Optional<Pokemon> getPokemonById(Long id) {
        return pokemonList.stream()
                .filter(pokemon -> pokemon.getId().equals(id))
                .findFirst();
    }

    public List<Pokemon> getAllPokemon() {
        if (pokemonList.isEmpty()) {
            fetchPokemonList();
        }
        return pokemonList;
    }

    private void fetchPokemonList() {
        String apiUrl = "https://pokeapi.co/api/v2/pokemon?limit=50"; // Adjust limit as needed
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            in.close();
            connection.disconnect();

            // Parse JSON response using Gson
            Gson gson = new Gson();
            JsonObject jsonResponse = gson.fromJson(content.toString(), JsonObject.class);
            JsonArray results = jsonResponse.getAsJsonArray("results");

            for (int i = 0; i < results.size(); i++) {
                JsonObject pokemonJson = results.get(i).getAsJsonObject();
                String name = pokemonJson.get("name").getAsString();
                String urlToPokemonDetails = pokemonJson.get("url").getAsString();

                // Fetch details for each Pokemon (like id, image, etc.)
                Pokemon pokemon = fetchPokemonDetails(urlToPokemonDetails);
                pokemonList.add(pokemon);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected Pokemon fetchPokemonDetails(String url) {
        try {
            URL detailUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) detailUrl.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            in.close();
            connection.disconnect();

            // Parse JSON response for details using Gson
            Gson gson = new Gson();
            JsonObject jsonResponse = gson.fromJson(content.toString(), JsonObject.class);
            long id = jsonResponse.get("id").getAsLong();
            String name = jsonResponse.get("name").getAsString();
            String imageUrl = jsonResponse.getAsJsonObject("sprites").get("front_default").getAsString();
            String silhouetteUrl = jsonResponse.getAsJsonObject("sprites").get("front_shiny").getAsString(); // Example silhouette

            // Create a new Pokemon object
            Pokemon pokemon = new Pokemon();
            pokemon.setId(id);
            pokemon.setName(name);
            pokemon.setImage(imageUrl);
            pokemon.setSilhouette(silhouetteUrl);

            return pokemon;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}