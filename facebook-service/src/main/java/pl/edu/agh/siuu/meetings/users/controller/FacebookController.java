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

    /*private Facebook facebook;
    private ConnectionRepository connectionRepository;

    @Inject
    public FacebookController(Facebook facebook, ConnectionRepository connectionRepository) {
        this.facebook = facebook;
        this.connectionRepository = connectionRepository;
    }

    @RequestMapping(path="/", method=RequestMethod.GET)
    public String helloFacebook(Model model) {
        if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
            return "redirect:/connect/facebook";
        }

        model.addAttribute("facebookProfile", facebook.userOperations().getUserProfile());
        PagedList<Post> feed = facebook.feedOperations().getFeed();
        model.addAttribute("feed", feed);
        return "hello";
    }

    @RequestMapping(path = "/email", method = RequestMethod.GET)
    public String getEmail(Model model) {

        if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
            return "redirect:/connect/facebook";
        }

        model.addAttribute("facebookProfile", facebook.userOperations().getUserProfile());
        model.addAttribute("email", facebook.userOperations().getUserProfile().getEmail());
        return "email";
    }

    @RequestMapping(path = "/logout")
    public String logout(HttpServletRequest request) throws URISyntaxException {
        new RestTemplate().delete(request.getRequestURL().toString().replace(request.getRequestURI(), "") + "/connect/facebook");
        return "redirect:/connect/facebook";
    }*/

    @RequestMapping(path = "/friends", method = RequestMethod.POST)
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
