package pl.edu.agh.siuu.meetings.users.controller;

import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.net.URISyntaxException;
import java.util.List;

@Controller
@RequestMapping("/")
public class FacebookController {

    private Facebook facebook;
    private ConnectionRepository connectionRepository;

    @Inject
    public FacebookController(Facebook facebook, ConnectionRepository connectionRepository) {
        this.facebook = facebook;
        this.connectionRepository = connectionRepository;
    }

    @RequestMapping(method=RequestMethod.GET)
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
    }

    @RequestMapping(path = "/friends")
    @ResponseBody
    public List<User> getFriends() {
        return facebook.friendOperations().getFriendProfiles();
    }
}
