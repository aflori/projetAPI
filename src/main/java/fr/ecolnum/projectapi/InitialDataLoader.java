package fr.ecolnum.projectapi;

import fr.ecolnum.projectapi.model.Role;
import fr.ecolnum.projectapi.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitialDataLoader implements CommandLineRunner {

    @Autowired
    RoleRepository roleRepository;

    public InitialDataLoader(){
    }

    @Override
    public void run(String... args){
        if(roleRepository.findById(1).isEmpty()) {
            Role role = new Role();
            role.setId(1);
            role.setName("ROLE_ADMIN");
            roleRepository.save(role);
        }
        if(roleRepository.findById(2).isEmpty()) {
            Role role = new Role();
            role.setId(2);
            role.setName("ROLE_USER");
            roleRepository.save(role);
        }
    }
}
