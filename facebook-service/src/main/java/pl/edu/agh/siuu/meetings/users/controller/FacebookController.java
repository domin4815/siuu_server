package pl.edu.agh.siuu.meetings.users.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import pl.edu.agh.siuu.meetings.users.model.TokenString;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.net.URISyntaxException;
import java.util.List;

@Controller
public class FacebookController {
    @RequestMapping(path = "/facebook/friends", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<User>> getFriends(@RequestBody TokenString tokenString) {
        System.out.println("Access token: " + tokenString.getString());
        Facebook facebook = new FacebookTemplate(tokenString.getString());
        PagedList<User> friendProfiles = facebook.friendOperations().getFriendProfiles();
        System.out.println("Friends:");
        for(User friend : friendProfiles){
            System.out.println(friend.getName());
        }
        System.out.println();
        return new ResponseEntity<List<User>>(friendProfiles, HttpStatus.OK);
    }
}
