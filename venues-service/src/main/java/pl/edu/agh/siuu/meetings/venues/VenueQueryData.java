package pl.edu.agh.siuu.meetings.venues;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class VenueQueryData {
    private double lon;
    private double lat;
    private double dist;
    private String category;
}
