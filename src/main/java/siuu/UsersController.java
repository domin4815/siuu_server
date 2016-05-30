package siuu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.jws.soap.SOAPBinding;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by domin4815 on 23.05.16.
 */
@RestController
public class UsersController {

    private IUserRepository userRepository;
    private Random r = new Random();


    @Autowired
    public UsersController(IUserRepository userRepository) {
        this.userRepository = userRepository;
        for (int i = 0; i< 10; i++){
            User user = new User("User_"+i, 49.9+r.nextDouble(), 19.5+r.nextDouble());
            userRepository.save(user);
        }
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<UserDto> getAllUsers() {

        Iterable<User> userIterable = userRepository.findAll();
        List<UserDto> users = new LinkedList<>();
        Iterator<User> userIterator = userIterable.iterator();

        while (userIterator.hasNext()){
            User u = userIterator.next();
            u.setLat(u.getLat()+r.nextDouble()/100);
            userRepository.save(u);
            users.add(new UserDto(u.getId(), u.getName(), u.getLat(), u.getLng()));
        }

        System.out.println("USERS GET ");
        return users;
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String updatePosition(@RequestParam("id") int id,
                                 @RequestParam("lat") double lat,
                                 @RequestParam("lng") double lng) {

        System.out.println("User: "+id+" on: "+lat+ " "+lng);

        User user = userRepository.findOne(id);
        if (user == null){
            return null;
        }

        user.setLat(lat);
        user.setLng(lng);
        userRepository.save(user);

        return "OK";
    }
}
