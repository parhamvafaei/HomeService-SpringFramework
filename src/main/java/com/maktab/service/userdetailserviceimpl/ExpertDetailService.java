package com.maktab.service.userdetailserviceimpl;

import com.maktab.repository.ExpertRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ExpertDetailService implements UserDetailsService {

    private final ExpertRepository expertRepository;

    public ExpertDetailService(ExpertRepository expertRepository) {
        this.expertRepository = expertRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return expertRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("Expert not present"));
    }
}
