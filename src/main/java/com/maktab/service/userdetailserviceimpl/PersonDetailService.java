package com.maktab.service.userdetailserviceimpl;


import com.maktab.entity.person.Person;
import com.maktab.repository.PersonRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PersonDetailService implements UserDetailsService {
    private final PersonRepository personRepository;

    public PersonDetailService( PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            return (UserDetails) personRepository.findByEmail(email)
                    .orElseThrow();
        } catch (Throwable e) {
            throw new UsernameNotFoundException(String.format("User with email %s not found", email));
        }
    }
}
