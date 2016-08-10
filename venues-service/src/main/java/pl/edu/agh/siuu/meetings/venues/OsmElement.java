package pl.edu.agh.siuu.meetings.venues;

import lombok.*;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class OsmElement {
    private String type;
    private double lat;
    private double lon;
    private Location center;
    private Map<String, String> tags;
}
