package pl.edu.agh.siuu.meetings.events;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Created by robert on 6/13/16.
 */
@RestController
public class EventsController {
    @Autowired
    private EventsRepository eventsRepository;

    @RequestMapping(value = "/event", method = RequestMethod.POST)
    public ResponseEntity addEvent(@RequestBody Event event) {
        eventsRepository.insert(event);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(value = "/event/sub/{userId}/{eventId}", method = RequestMethod.POST)
    public ResponseEntity addEvent(@PathVariable("userId") String userId,
                                   @PathVariable("eventId") String eventId) {

        Event event = eventsRepository.findOne(eventId);
        if (event.getParticipants() == null){
            event.setParticipants(new HashSet<>());
        }
        event.getParticipants().add(userId);
        eventsRepository.save(event);

        return new ResponseEntity(event, HttpStatus.OK);
    }

    @RequestMapping(value = "/events/user/{id}", method = RequestMethod.POST)
    public ResponseEntity<List<Event>> getEventsForUser(
            @PathVariable("id") String userId,
            @RequestParam("lon") double lon,
            @RequestParam("lat") double lat,
            @RequestParam(value = "dist", required = false, defaultValue = "1") double distance
    ) {
        List<String> participants = new ArrayList<String>();
        participants.add(userId);
        List<Event> events = eventsRepository.findEventsWithFriends(lon,lat,distance,participants);
        return new ResponseEntity(events, HttpStatus.OK);
    }

    @RequestMapping(value = "/events", method = RequestMethod.GET)
    public ResponseEntity<List<Event>> getEvents() {
        List<Event> events = eventsRepository.findAll();
        return new ResponseEntity(events, HttpStatus.OK);
    }

    @RequestMapping(value = "/events/categories", method = RequestMethod.GET)
    public ResponseEntity<List<String>> getActivityCategories() {
        List<String> categories = new LinkedList<String>();
        categories.add("swimming pool");
        categories.add("soccer");
        categories.add("volleyball");
        categories.add("tennis");
        categories.add("basketball");
        categories.add("table tennis");
        return new ResponseEntity<List<String>>(categories, HttpStatus.OK);
    }
}
