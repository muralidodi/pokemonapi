package pokemon.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pokemon {
    private Long id;
    private String name;
    private String image;
    private String silhouette;

    public Pokemon() {

    }
}
