package osg.loki.simple_auth.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import osg.loki.simple_auth.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	private final UserRepository userRepository;
	
	@Autowired
	public CustomUserDetailsService(UserRepository userRpository) {
		this.userRepository=userRpository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		if(!username.equals(userRepository.findUserByName(username).getUsername())) throw new UsernameNotFoundException(username+" not found");
		return new UserDetails() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = -6352567153833631794L;

			@Override
			public boolean isEnabled() {
				// TODO Auto-generated method stub
				return true;
			}
			
			@Override
			public boolean isCredentialsNonExpired() {
				// TODO Auto-generated method stub
				return true;
			}
			
			@Override
			public boolean isAccountNonLocked() {
				// TODO Auto-generated method stub
				return true;
			}
			
			@Override
			public boolean isAccountNonExpired() {
				// TODO Auto-generated method stub
				return true;
			}
			
			@Override
			public String getUsername() {
				// TODO Auto-generated method stub
				return username;
			}
			
			@Override
			public String getPassword() {
				// TODO Auto-generated method stub
				return userRepository.findUserByName(username).getPassword();
			}
			
			@Override
			public Collection<? extends GrantedAuthority> getAuthorities() {
				// TODO Auto-generated method stub
				List<SimpleGrantedAuthority> auth = new java.util.ArrayList<SimpleGrantedAuthority>();
				auth.add(new SimpleGrantedAuthority("USER"));
				return auth;
			}
		};
	}
	

}
