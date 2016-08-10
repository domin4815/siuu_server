package pl.edu.agh.siuu.meetings.venues;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Location {
    private double lon;
    private double lat;
}