package pl.edu.agh.siuu.meetings.venues;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class OsmVenueService {

    /*
    http://overpass-api.de/api/interpreter?data=[out:json];(node["sport"="tennis"](around:2000,50.069925,19.9123987);way["sport"="tennis"](around:2000,50.069925,19.9123987););(._;>;);out;
    */

    private static final String overpassApiUri = "http://overpass-api.de/api/interpreter";
    private static final String queryPrefix = "[out:json];(";
    private static final String querySuffix = ");out%20center;";

    private List<String> generateTagsForCategory(String category) {
        List<String> tags = new LinkedList<String>();
        String whitespacelessCategory = category.toLowerCase().replace(' ', '_');
        tags.add("[amenity=" + whitespacelessCategory + "]");
        tags.add("[leisure=" + whitespacelessCategory + "]");
        tags.add("[sport=" + whitespacelessCategory + "]");
        return tags;
    }

    private List<String> getTagsForCategory(String category) {
        return generateTagsForCategory(category);
    }

    private List<Venue> extractVenues(OsmResponse osmResponse, String category) {
        List<OsmNode> nodes = osmResponse.getElements();
        List<Venue> venues = new LinkedList<Venue>();
        for(OsmNode node: nodes) {
            Venue venue = new Venue(category, node.getLon(), node.getLat(), node.getTags().get("name"));
            venues.add(venue);
        }
        return venues;
    }

    public List<Venue> findNearbyVenues(String category, double lon, double lat, double distance) {
        List<String> osmTags = getTagsForCategory(category);

        if(osmTags.isEmpty()) {
            return new LinkedList<Venue>();
        }

        String queryNodeAlternatives = "";
        for(String tag : osmTags) {
            String locationSpecification = tag + "(around:" + distance + "," + lat + "," + lon + ")";
            queryNodeAlternatives += "node" + locationSpecification + ";";
            queryNodeAlternatives += "way" + locationSpecification + ";";
        }

        String queryString = queryPrefix + queryNodeAlternatives + querySuffix;
        String requestUri = overpassApiUri + "?data=" + queryString;
        RestTemplate restTemplate = new RestTemplate();
        OsmResponse osmResponse = restTemplate.getForObject(requestUri, OsmResponse.class, queryString);

        return extractVenues(osmResponse, category);
    }
}
