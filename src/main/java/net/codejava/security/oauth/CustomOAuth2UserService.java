package net.codejava.security.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService  {

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		// 應用程式 ( APP ) 的 ID
//		System.out.println(userRequest.getClientRegistration().getClientId());;
		// 該應用程式隸屬 ( ex : facebook / google ... )
//		System.out.println(userRequest.getClientRegistration().getRegistrationId());
		OAuth2User user =  super.loadUser(userRequest);
		System.out.println("loadUser....");
		CustomOAuth2User coUser = new CustomOAuth2User(user);
		coUser.setProvider(userRequest.getClientRegistration().getRegistrationId());
		return coUser;
	}

}
