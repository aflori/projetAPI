package fr.ecolnum.projectapi.service;

import fr.ecolnum.projectapi.DTO.ObserverDto;
import fr.ecolnum.projectapi.exception.IdNotFoundException;
import fr.ecolnum.projectapi.exception.IdNotMatchingException;
import fr.ecolnum.projectapi.exception.NameNotFoundException;
import fr.ecolnum.projectapi.exception.NotAuthorizedOperationException;
import fr.ecolnum.projectapi.model.Observer;
import fr.ecolnum.projectapi.model.Role;
import fr.ecolnum.projectapi.repository.ObserverRepository;
import fr.ecolnum.projectapi.repository.PoolRepository;
import fr.ecolnum.projectapi.repository.RoleRepository;
import fr.ecolnum.projectapi.security.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    private RoleRepository roleRepository;

    /**
     * This method is used to authenticate a user with his email from the database
     * @param email email of the user
     * @return details of the user with his authorities
     * @throws UsernameNotFoundException if email doesn't exist in the database
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Observer> observer = observerRepository.findByEmail(email);
        if(observer.isEmpty()){
            throw new UsernameNotFoundException("User not exists by Email");
        }
        Set<GrantedAuthority> authorities = observer.get().getRoles().stream()
                .map((role) -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());
        return new org.springframework.security.core.userdetails.User(email,observer.get().getPassword(),authorities);
    }



    @Autowired
    private PoolRepository poolRepository;

    public ObserverDto createObserver(ObserverDto observerDto) throws IdNotFoundException, NameNotFoundException {

        Observer observer = observerDto.convertToObserverObject(poolRepository);

        //add user role to new observer
        Set<Role> observerRole = new HashSet<>();
        Optional<Role> userRole = roleRepository.findByName("ROLE_USER");
        if (userRole.isEmpty()) {
            throw new NameNotFoundException("This name does not exist");
        }
        observerRole.add(userRole.get());

        observer.setRoles(observerRole);

        //encode password of new observer
        observer.setPassword(SecurityConfig.passwordEncoder().encode(observer.getPassword()));

        observer = observerRepository.save(observer);

        return new ObserverDto(observer);
    }

    public ObserverDto editObserver(int id, ObserverDto observerDto) throws IdNotFoundException, NameNotFoundException, NotAuthorizedOperationException, IdNotMatchingException {

        //Check if id in observerDto is the same as id in request
        if (observerDto.getId() != id) {
            throw new IdNotMatchingException("Observer Id from request does not match Id from poolDTO.");
        }

        //get authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String observerMail = authentication.getName();
        Optional<Observer> authenticatedObserver = observerRepository.findByEmail(observerMail);
        if (authenticatedObserver.isEmpty()) {
            throw new NameNotFoundException("This user doesn't exist.");
        }

        //reference to admin role
        Optional<Role> adminRole = roleRepository.findByName("ROLE_ADMIN");
        if (adminRole.isEmpty()) {
            throw new NameNotFoundException("This role doesn't exist.");
        }
        //get authenticated user roles
        Set<Role> authenticatedObserverRoles = authenticatedObserver.get().getRoles();

        //get authenticated user id
        int authenticatedObserverId = authenticatedObserver.get().getId();

        //check if authenticated user is admin or want to edit his own profile
        if (!authenticatedObserverRoles.contains(adminRole.get()) && authenticatedObserverId != id) {
            throw new NotAuthorizedOperationException("You don't have the right to do this request.");
        }

        //check that observer to edit exists.
        if (observerRepository.findById(id).isEmpty()) {
            throw new IdNotFoundException("This Observer does not exist.");
        }

        Observer modifiedObserver = observerDto.convertToObserverObject(poolRepository);

        //get authorities from observer and add them to new modified observer.
        Set<Role> modifiedObserverRoles = observerRepository.findById(id).get().getRoles();
        modifiedObserver.setRoles(modifiedObserverRoles);

        observerRepository.save(modifiedObserver);

        return new ObserverDto(modifiedObserver);
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

    public ObserverDto getObserverById(int id) throws IdNotFoundException {
        Optional<Observer> observer = observerRepository.findById(id);

        if (observer.isEmpty()) {
            throw new IdNotFoundException();
        }
        return new ObserverDto(observer.get());
    }

    public void changeAuthorities(int id, Set<Role> roleSet) throws IdNotFoundException, NameNotFoundException {
        Optional<Observer> observer = observerRepository.findById(id);
        if (observer.isEmpty()) {
            throw new IdNotFoundException();
        }

        //if the received Role Set doesn't countain authorities ID we need to insert them
        for(Role role : roleSet) {
            Optional<Role> tableRole = roleRepository.findByName(role.getName());
            if (tableRole.isEmpty()) {
                throw new NameNotFoundException("This name does not exist.");
            }
            role.setId(tableRole.get().getId());
        }

        observer.get().setRoles(roleSet);


        observerRepository.save(observer.get());

    }
}
