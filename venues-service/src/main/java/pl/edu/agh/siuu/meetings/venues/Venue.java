package pl.edu.agh.siuu.meetings.venues;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Venue {
    private String category;
    private double lon;
    private double lat;
    private String name;
}
