package pl.edu.agh.siuu.meetings.events;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by robert on 6/13/16.
 */
@RestController
public class EventsController {
    @Autowired
    private EventsRepository eventsRepository;

    @PostConstruct
    public void insertFakeData() {
        List<String> participants = new ArrayList<String>();
        participants.add("Someone1");
        participants.add("Someone2");
        participants.add("Someone3");
        Event event = new Event(
                "Event_1",
                "Some event",
                "Category",
                new Location(2., 1.),
                DateTime.now(),
                DateTime.now(),
                10,
                11,
                "User_1",
                participants,
                "Some comment"
        );

        eventsRepository.insert(event);
    }

    @RequestMapping(value = "/events", method = RequestMethod.GET)
    public long test() {
        return eventsRepository.count();
    }
}
