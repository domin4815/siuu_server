package pl.edu.agh.siuu.meetings.users;

import lombok.*;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Location {
    private double lon;
    private double lat;
}
