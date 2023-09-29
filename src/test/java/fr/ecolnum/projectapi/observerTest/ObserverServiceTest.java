package fr.ecolnum.projectapi.observerTest;


import fr.ecolnum.projectapi.DTO.ObserverDto;
import fr.ecolnum.projectapi.exception.IdNotFoundException;
import fr.ecolnum.projectapi.model.Observer;
import fr.ecolnum.projectapi.repository.ObserverRepository;
import fr.ecolnum.projectapi.repository.PoolRepository;
import fr.ecolnum.projectapi.service.ObserverService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

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
    public void testFunctionCreate() throws IdNotFoundException {

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

        assertEquals(returnedFromCreateObserver.getId(),1);
        assertEquals(returnedFromCreateObserver.getLastname(), lastName);
        assertEquals(returnedFromCreateObserver.getFirstname(),firstName);
        assertEquals(returnedFromCreateObserver.getEmail(),email);
        assertEquals(returnedFromCreateObserver.getPassword(),password);

    }

}
