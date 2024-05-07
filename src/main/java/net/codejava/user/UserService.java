package net.codejava.user;

import net.codejava.security.oauth.CustomOAuth2User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository repo;
     
    public void processOAuthPostLogin(CustomOAuth2User oauthUser) {
    	System.out.println("username: " + oauthUser.getEmail());
    	System.out.println("provider: " + oauthUser.getProvider());
        User existUser = repo.getUserByUsernameAndProvider(
                oauthUser.getEmail() == null ? oauthUser.getName() : oauthUser.getEmail(),
                Provider.valueOf(oauthUser.getProvider().toUpperCase()));

        if (existUser == null) {

            System.out.println("user not exist... create new one");

            User newUser = new User();
            newUser.setUsername(oauthUser.getEmail() == null ? oauthUser.getName() : oauthUser.getEmail());
            newUser.setProvider(Provider.valueOf(oauthUser.getProvider().toUpperCase()));
            newUser.setEnabled(true);          
             
            repo.save(newUser);        
        }
    }

    public User getUserByUsername(String username){
        return repo.getUserByUsername(username);
    }
}
