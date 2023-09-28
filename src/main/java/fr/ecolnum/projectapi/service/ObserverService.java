package fr.ecolnum.projectapi.service;

import fr.ecolnum.projectapi.DTO.ObserverDto;
import fr.ecolnum.projectapi.exception.IdNotFoundException;
import fr.ecolnum.projectapi.model.Observer;
import fr.ecolnum.projectapi.repository.ObserverRepository;
import fr.ecolnum.projectapi.repository.PoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * This class contains the methods regarding the Observer objects.
 */
@Service
public class ObserverService {

    @Autowired
    private ObserverRepository observerRepository;

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
