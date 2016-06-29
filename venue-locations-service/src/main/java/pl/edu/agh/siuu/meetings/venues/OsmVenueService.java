package pl.edu.agh.siuu.meetings.venues;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;
import java.util.List;

@Service
public class OsmVenueService {

    private static final String overpassApiUri = "http://overpass-api.de/api/interpreter";
    private static final String queryPrefix = "[out:json];(";
    private static final String querySuffix = ");out center;";

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

    private List<Venue> extractVenuesFromOsmResponse(OsmResponse osmResponse, String category) {
        List<OsmElement> elements = osmResponse.getElements();
        List<Venue> venues = new LinkedList<Venue>();
        for(OsmElement element: elements) {
            if(element.getType().equals("node") && element.getTags() != null){
                Venue venue = new Venue(category, element.getLon(), element.getLat(), element.getTags().get("name"));
                venues.add(venue);
            } else if(element.getType().equals("way")) {
                Venue venue = new Venue(category, element.getCenter().getLon(), element.getCenter().getLat(),
                        element.getTags().get("name"));
                venues.add(venue);
            }
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
            String tagWithlocation = tag + "(around:" + distance + "," + lat + "," + lon + ")";
            String queryPartForTag = "(node" + tagWithlocation + ";" + "way" + tagWithlocation + ";);(._;>;);";
            queryNodeAlternatives += queryPartForTag;
        }

        String queryString = queryPrefix + queryNodeAlternatives + querySuffix;
        String requestUri = overpassApiUri + "?data=" + queryString;
        RestTemplate restTemplate = new RestTemplate();
        OsmResponse osmResponse = restTemplate.getForObject(requestUri, OsmResponse.class);

        return extractVenuesFromOsmResponse(osmResponse, category);
    }
}
