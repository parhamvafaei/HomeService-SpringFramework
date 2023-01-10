package com.maktab.service.userdetailserviceimpl;

import com.maktab.repository.ClientRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ClientDetailService implements UserDetailsService {

    private final ClientRepository clientRepository;

    public ClientDetailService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return clientRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("Client not present"));
    }
}
