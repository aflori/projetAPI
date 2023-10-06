package fr.ecolnum.projectapi;

import fr.ecolnum.projectapi.exception.NameNotFoundException;
import fr.ecolnum.projectapi.model.Observer;
import fr.ecolnum.projectapi.model.Role;
import fr.ecolnum.projectapi.repository.ObserverRepository;
import fr.ecolnum.projectapi.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class InitialDataLoader implements CommandLineRunner {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ObserverRepository observerRepository;

    public InitialDataLoader() {
    }

    @Override
    public void run(String... args) throws NameNotFoundException {
        if (roleRepository.findByName("ROLE_ADMIN").isEmpty()) {
            Role role = new Role();
            role.setName("ROLE_ADMIN");
            roleRepository.save(role);
        }
        if (roleRepository.findByName("ROLE_USER").isEmpty()) {
            Role role = new Role();
            role.setName("ROLE_USER");
            roleRepository.save(role);
        }
        if (observerRepository.findByEmail("admin@gmail.com").isEmpty()) {
            Set<Role> adminRoles = new HashSet<>();

            Optional<Role> adminRole = roleRepository.findByName("ROLE_ADMIN");
            if (adminRole.isEmpty()) {
                throw new NameNotFoundException("This name does not exist.");
            }
            adminRoles.add(adminRole.get());

            Optional<Role> userRole = roleRepository.findByName("ROLE_USER");
            if (userRole.isEmpty()) {
                throw new NameNotFoundException("This name does not exist");
            }
            adminRoles.add(userRole.get());

            Observer admin = new Observer(0, "Super", "Admin", "admin@gmail.com", "$2a$10$2MYM9YlRupftFSo10iXy/uMFNM8zgyw3ChwY9NowiHopIXsJsk1UC");
            admin.setRoles(adminRoles);
            observerRepository.save(admin);
        }
    }
}
