package pl.edu.agh.siuu.meetings.venues;

import lombok.*;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class OsmNode {
    private String id;
    private double lat;
    private double lon;
    private Map<String, String> tags;
    private String version;
}
