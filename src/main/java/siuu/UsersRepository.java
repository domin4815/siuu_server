package siuu;

import org.joda.time.DateTime;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;

public interface UsersRepository extends MongoRepository<User, String> {

/*    db.places.aggregate([
    {
        $geoNear: {
            near: { type: "Point", coordinates: [ -73.99279 , 40.719296 ] },
            distanceField: "dist.calculated",
                    maxDistance: 2,
                    query: { type: "public" },
            includeLocs: "dist.location",
                    num: 5,
                    spherical: true
        }
    }
    ])*/



    @Query("{location: {$near: [?0, ?1], $maxDistance: ?2}, preferedActivities: {$elemMatch: {category: {$in: ?3}}}, timestamp: {$gte: ?4}}")
    public List<User> findNearbyUsersWithPreferedActivities(
            double lon, double lat, double distance,
            List<String> activityCategories,
            DateTime activeSince
    );
}
