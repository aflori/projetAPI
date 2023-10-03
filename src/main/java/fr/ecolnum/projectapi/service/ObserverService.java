package fr.ecolnum.projectapi.service;

import fr.ecolnum.projectapi.DTO.ObserverDto;
import fr.ecolnum.projectapi.exception.IdNotFoundException;
import fr.ecolnum.projectapi.model.Observer;
import fr.ecolnum.projectapi.repository.ObserverRepository;
import fr.ecolnum.projectapi.repository.PoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This class contains the methods regarding the Observer objects.
 */
@Service
public class ObserverService implements UserDetailsService {

    @Autowired
    private ObserverRepository observerRepository;

    /**
     * This method is used to authenticate a user with his email from the database
     * @param email email of the user
     * @return details of the user with us authorities
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Observer observer = observerRepository.findByEmail(email);
        if(observer == null){
            throw new UsernameNotFoundException("User not exists by Email");
        }
        Set<GrantedAuthority> authorities = observer.getRoles().stream()
                .map((role) -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());
        return new org.springframework.security.core.userdetails.User(email,observer.getPassword(),authorities);
    }



    @Autowired
    private PoolRepository poolRepository;

    public ObserverDto createObserver(ObserverDto observerDto) throws IdNotFoundException {

        Observer observer = observerDto.convertToObserverObject(poolRepository);

        observer = observerRepository.save(observer);

        return new ObserverDto(observer);
    }

    public Iterable<ObserverDto> getAllObservers() {

        Iterable<Observer> allObserver = observerRepository.findAll();

        Set<ObserverDto> allObserverDto = new HashSet<>();

        for (Observer observer : allObserver) {
            ObserverDto observerDto = new ObserverDto(observer);
            allObserverDto.add(observerDto);
        }

        return allObserverDto;
    }
}
