package siuu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by domin4815 on 24.05.16.
 */
@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    private Integer id;
    private String name;
    private Double lat;
    private Double lng;

    public UserDto(Integer id, String name, Double lat, Double lng) {
        this.id = id;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
    }
}
