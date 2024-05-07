package net.codejava.security.oauth;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomOAuth2User implements OAuth2User {

	private OAuth2User oauth2User;

	private String provider;
	
	public CustomOAuth2User(OAuth2User oauth2User) {
		this.oauth2User = oauth2User;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return oauth2User.getAttributes();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return oauth2User.getAuthorities();
	}

	@Override
	public String getName() {

		// check information in user attribute
//		for(Map.Entry ent : oauth2User.getAttributes().entrySet()){
//			System.out.println(ent.getKey()+"="+ent.getValue());
//		}

		return oauth2User.getAttribute("name");
	}

	public String getEmail() {
		return oauth2User.<String>getAttribute("email");		
	}

	public String getId() {
		return oauth2User.<String>getAttribute("id");
	}

	public String getPicture() {
		return oauth2User.<String>getAttribute("picture");
	}

	public void setProvider(String provider){
		this.provider = provider;
	}

	public String getProvider(){
		return this.provider;
	}

}
