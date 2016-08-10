package pl.edu.agh.siuu.meetings.venues;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class OsmResponse {
    private List<OsmElement> elements;
}
