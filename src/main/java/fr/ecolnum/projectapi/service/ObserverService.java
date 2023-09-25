package fr.ecolnum.projectapi.service;

import fr.ecolnum.projectapi.model.Observer;
import fr.ecolnum.projectapi.repository.ObserverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

@Service
public class ObserverService {

    @Autowired
    private ObserverRepository observerRepository;

    public void createObserver(Observer observer) {
        observerRepository.save(observer);
    }

    public Iterable<Observer> getAllObservers() {
        return observerRepository.findAll();
    }
}
