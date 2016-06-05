package siuu;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.joda.time.DateTime;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * Created by domin4815 on 23.05.16.
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Document
public class User {
    private String id;

    @GeoSpatialIndexed(useGeneratedName=true)
    private Location location;

    private List<PreferedActivity> preferedActivities;

    @JsonIgnore
    private DateTime timestamp;
}
