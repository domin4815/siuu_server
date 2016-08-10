package pl.edu.agh.siuu.meetings.users;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by domin4815 on 23.05.16.
 */
@RestController
public class UsersController {
    public static final Duration USER_ACTIVITY_TIME = Duration.standardMinutes(5);

    @Autowired
    private UsersRepository usersRepository;

    @PostConstruct
    public void insertFakeData(){
        Random rand = new Random();
        List<PreferedActivity> preferedActivities = new LinkedList<PreferedActivity>();
        preferedActivities.add(new PreferedActivity("board game", "chess"));
        preferedActivities.add(new PreferedActivity("board game", "tic tac toe"));
        preferedActivities.add(new PreferedActivity("football", ""));
        preferedActivities.add(new PreferedActivity("volleyball", "beach"));
        for (int i = 0; i< 10; i++){
            User user = new User(
                    "User_"+i,
                    new Location(49.9+rand.nextDouble(), 19.5+rand.nextDouble()),
                    Arrays.asList(
                            preferedActivities.get(i % preferedActivities.size()),
                            preferedActivities.get((i + 1) % preferedActivities.size())
                    ),
                    DateTime.now()
            );
            usersRepository.save(user);
        }
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseEntity updateUser(@RequestBody User user) {
        user.setTimestamp(DateTime.now());
        usersRepository.save(user);
        System.out.println("Saved user: " + user);
        return new ResponseEntity(HttpStatus.OK);
    }


    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable("id") String userId) {
        User user = usersRepository.findOne(userId);
        if(user == null) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        } else {
            System.out.println("Got user " + user);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/user/{id}/location", method = RequestMethod.POST)
    public ResponseEntity updateUserLocation(@PathVariable("id") String userId, @RequestBody Location location) {
        User user = usersRepository.findOne(userId);
        if(user == null) {
            System.out.println("User with id " + userId + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        } else {
            user.setLocation(location);
            user.setTimestamp(DateTime.now());
            usersRepository.save(user);
            System.out.println("Location of user with id " + userId + " set to " + location);
            return new ResponseEntity(HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/user/{id}/activities", method = RequestMethod.GET)
    public ResponseEntity<List<PreferedActivity>> getUserActivities(@PathVariable("id") String userId) {
        User user = usersRepository.findOne(userId);
        if(user == null) {
            System.out.println("User with id " + userId + " not found");
            return new ResponseEntity<List<PreferedActivity>>(HttpStatus.NOT_FOUND);
        } else {
            System.out.println("Got prefered activities of user with id " + userId + ": " + user.getPreferedActivities());
            return new ResponseEntity<List<PreferedActivity>>(user.getPreferedActivities(), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/user/{id}/activities", method = RequestMethod.POST)
    public ResponseEntity updateUserActivities(@PathVariable("id") String userId, @RequestBody PreferedActivity[] activities) {
        User user = usersRepository.findOne(userId);
        if(user == null) {
            System.out.println("User with id " + userId + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        } else {
            user.setPreferedActivities(Arrays.asList(activities));
            user.setTimestamp(DateTime.now());
            usersRepository.save(user);
            System.out.println("Prefered activities of user with id " + userId + " set to " + Arrays.toString(activities));
            return new ResponseEntity(HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/users/find", method = RequestMethod.POST)
    public ResponseEntity<List<User>> findNearbyUsersWithPreferedActivities(
            @RequestParam("lon") double lon,
            @RequestParam("lat") double lat,
            @RequestParam(value = "dist", required = false, defaultValue = "10") double distance,
            @RequestBody String[] activityCategories
    ) {
        List<User> users = usersRepository.findNearbyUsersWithPreferedActivities(
            lon, lat, distance,
            Arrays.asList(activityCategories),
            DateTime.now().minus(USER_ACTIVITY_TIME)
        );
        System.out.println("Got users " + users);
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }
}
