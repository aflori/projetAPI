package fr.ecolnum.projectapi.observerTest;


import fr.ecolnum.projectapi.DTO.ObserverDto;
import fr.ecolnum.projectapi.exception.IdNotFoundException;
import fr.ecolnum.projectapi.model.Observer;
import fr.ecolnum.projectapi.model.Pool;
import fr.ecolnum.projectapi.repository.ObserverRepository;
import fr.ecolnum.projectapi.repository.PoolRepository;
import fr.ecolnum.projectapi.service.ObserverService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import javax.naming.NameNotFoundException;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ObserverServiceTest {

    @InjectMocks
    private ObserverService observerService;

    @Mock
    ObserverRepository observerRepository;
    @Mock
    PoolRepository poolRepository;
    @Mock
    ObserverDto observerDtoTest;

    @Test
    public void testFunctionCreate() throws IdNotFoundException, NameNotFoundException {

        //observer tested value

        String lastName = "tuto";
        String firstName = "totu";
        String email = "eee@aaa.fr";
        String password = "12345";

        //mock: convertToObserverObject() function
        Observer observerConvertedFromDto = new Observer(0, lastName, firstName, email, password, null);
        when(observerDtoTest.convertToObserverObject(poolRepository))
                .thenReturn(observerConvertedFromDto);

        //mock: save(Observer) function
        Observer observerAfterSaveDone = new Observer(1, lastName, firstName, email, password, null);
        when(observerRepository.save(observerConvertedFromDto))
                .thenReturn(observerAfterSaveDone);

        ObserverDto returnedFromCreateObserver = observerService.createObserver(observerDtoTest);

        assertEquals(returnedFromCreateObserver.getId(), 1);
        assertEquals(returnedFromCreateObserver.getLastName(), lastName);
        assertEquals(returnedFromCreateObserver.getFirstName(), firstName);
        assertEquals(returnedFromCreateObserver.getEmail(), email);
        assertEquals(returnedFromCreateObserver.getPassword(), password);

    }

    @Test
    public void testFunctionGetAll() {

        // observerRepository.findAll() mock
        Observer[] arrayLocalObserver = {
                new Observer(1, "toto", "titi", "toti@gmail.com", "1234", null),
                new Observer(3, "Alex", "Xela", "alexXela@gmail.com", "54321", null),
                new Observer(4, "Thale", "Elle", "Thale.elle@gmail.com", "abcd", null)
        };
        List<Observer> listLocalObserver = List.of(arrayLocalObserver);
        when(observerRepository.findAll())
                .thenReturn(listLocalObserver);

        Iterable<ObserverDto> allObserver = observerService.getAllObservers();

        int numberElement = 0;
        for (ObserverDto observerDto : allObserver) {

            int id = observerDto.getId();
            assert (id == 1 || id == 3 || id == 4);

            String lastName = observerDto.getLastName();
            String firstName = observerDto.getFirstName();

            assert ((lastName.equals("toto") && firstName.equals("titi")) ||
                    (lastName.equals("Alex") && firstName.equals("Xela")) ||
                    (lastName.equals("Thale") && firstName.equals("Elle"))
            );

            numberElement++;
        }
        assertEquals(numberElement, listLocalObserver.size());

    }

    @Test
    public void testFunctionGetAllWithAssociationTable() {

        // observerRepository.findAll() mock

        Observer[] arrayLocalObserver = {
                new Observer(1, "t", "t", "tt@gmail.com", "12",
                        Set.of(new Pool(1), new Pool(3))
                ),
                new Observer(3, "A", "Z", "az@gmail.com", "21",
                        Set.of(new Pool(2), new Pool(5))),
        };
        List<Observer> listLocalObserver = List.of(arrayLocalObserver);
        when(observerRepository.findAll())
                .thenReturn(listLocalObserver);

        Iterable<ObserverDto> allObserver = observerService.getAllObservers();

        int numberElement = 0;
        for (ObserverDto observerDto : allObserver) {

            int id = observerDto.getId();
            List<Integer> poolAssociated = observerDto.getContainInPool();

            assert (
                    (id == 1 && poolAssociated.containsAll(
                            List.of(new Integer[]{1, 3})
                    )) ||
                    (id == 3 && poolAssociated.containsAll(
                            List.of(new Integer[]{2,5})
                    ))
            );

            numberElement++;
        }
        assertEquals(numberElement, listLocalObserver.size());

    }

}
