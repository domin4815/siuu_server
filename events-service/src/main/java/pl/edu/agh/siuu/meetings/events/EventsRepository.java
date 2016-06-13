package pl.edu.agh.siuu.meetings.events;

import org.joda.time.DateTime;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;

/**
 * Created by robert on 6/13/16.
 */
public interface EventsRepository extends MongoRepository<Event, String>{
    @Query("{location: {$near: [?0, ?1], $maxDistance: ?2}, participants: {$elemMatch: {category: {$in: ?3}}}, participants: {$elemMatch: {category: {$in: ?3}}}}")
    public List<Event> findEventsWithMatchedPreferences (
            double lon, double lat, double distance,
            List<String> friends
    );
}
