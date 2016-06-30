package pl.edu.agh.siuu.meetings.events;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.joda.time.DateTime;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by robert on 6/13/16.
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Document
public class Event {
    private String id;

    private String name;

    private String category;

    @GeoSpatialIndexed(useGeneratedName=true)
    private Location location;

    @JsonIgnore
    private DateTime startTime;

    @JsonIgnore
    private DateTime endTime;

    private int maxPeople;

    private int minPeople;

    private String owner;

    private List<String> participants;

    private String comment;

}