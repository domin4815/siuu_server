package pl.edu.agh.siuu.meetings.venues;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VenuesController {

    @Autowired
    private OsmVenueService osmVenueService;

    @RequestMapping(value = "/venues", method = RequestMethod.POST)
    public ResponseEntity<List<Venue>> findVenues(@RequestBody VenueQueryData queryData) {
        List<Venue> venues = osmVenueService.findNearbyVenues(
                queryData.getCategory(), queryData.getLon(), queryData.getLat(), queryData.getDist()
        );
        return new ResponseEntity<List<Venue>>(venues, HttpStatus.OK);
    }
}
