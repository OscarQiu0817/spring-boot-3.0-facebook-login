package net.codejava;

import net.codejava.security.MyUserDetails;
import net.codejava.security.oauth.CustomOAuth2User;
import net.codejava.user.Provider;
import net.codejava.user.User;
import net.codejava.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private OAuth2AuthorizedClientService clientService;

    @Autowired
    private UserService userService;

    @ModelAttribute("globalData")
    public void getGlobalData(Model model) {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if( authentication.getPrincipal() instanceof MyUserDetails ){
            model.addAttribute("loginProvider", Provider.LOCAL.getName());
        }else{

            // 轉成 OAuth2AuthenticationToken 取得 accessToken
            OAuth2AuthenticationToken oauthToken =
                    (OAuth2AuthenticationToken) authentication;
            OAuth2AuthorizedClient client =
                    clientService.loadAuthorizedClient(
                            oauthToken.getAuthorizedClientRegistrationId(),
                            oauthToken.getName());
            String accessToken = client.getAccessToken().getTokenValue();

            System.out.println("accessToken = " + accessToken);

            // 轉成 CustomOAuth2User 取得 email
            CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();

            User user = userService.getUserByUsername(oauthUser.getEmail() == null ? oauthUser.getName() : oauthUser.getEmail());
            Provider provider = user.getProvider();

            model.addAttribute("loginProvider", provider.getName());



            StringBuilder profilePictureUrl = new StringBuilder("");
            switch (provider){
                case FACEBOOK:
                    profilePictureUrl.append("https://graph.facebook.com/").append(oauthUser.getId());
                    profilePictureUrl.append("/picture?");
                    // enum{small, normal, album, large, square}
                    profilePictureUrl.append("&type=").append("normal");
                    profilePictureUrl.append("&access_token=").append(accessToken);
                    break;
                case GOOGLE:
                    profilePictureUrl.append(oauthUser.getPicture());
                    break;
                case GITHUB:
                    profilePictureUrl.append(oauthUser.getAvatarUrl());
                    break;
                default:
                    break;
            }

            System.out.println("profilePictureUrl : " + profilePictureUrl);
            model.addAttribute("userPicture", profilePictureUrl.toString());
        }
    }
}
