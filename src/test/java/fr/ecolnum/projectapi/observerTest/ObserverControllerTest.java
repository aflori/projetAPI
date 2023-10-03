package fr.ecolnum.projectapi.observerTest;

import fr.ecolnum.projectapi.DTO.ObserverDto;
import fr.ecolnum.projectapi.controller.ObserverController;
import fr.ecolnum.projectapi.exception.IdNotFoundException;
import fr.ecolnum.projectapi.model.Observer;
import fr.ecolnum.projectapi.service.ObserverService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ObserverControllerTest {

    @InjectMocks
    private ObserverController observerController;

    @Mock
    private ObserverService observerService;
    @Mock
    private ObserverDto observerDto;


    @Test
    public void testEndpointCreateObserver() throws IdNotFoundException {

        //mock for working correctly test
        Observer originObserver = new Observer(1, null, null, null, null);
        ObserverDto observerDtoResult = new ObserverDto(originObserver);
        when(observerService.createObserver(observerDto))
                .thenReturn(observerDtoResult);

        ResponseEntity<?> controllerResponse = observerController.createObserver(observerDto);

        // result verification
        Object responseObject = controllerResponse.getBody();
        assert (controllerResponse.getStatusCode() == HttpStatus.CREATED);
        assert (responseObject instanceof ObserverDto);

        ObserverDto responseDto = (ObserverDto) responseObject;
        assert (responseDto.getId() == 1);


        //mock for throw
        when(observerService.createObserver(observerDto))
                .thenThrow(IdNotFoundException.class);


        controllerResponse = observerController.createObserver(observerDto);
        assert (controllerResponse.getStatusCode() == HttpStatus.CONFLICT);
    }

    @Test
    public void testEndpointGetAllObservers() {
        //mock
        List<ObserverDto> answer = List.of(
                new ObserverDto(1, "toto", "totobis"),
                new ObserverDto(2, "toto2", "toto2bis"),
                new ObserverDto(10, "toto3", "toto3bis")
        );
        when(observerService.getAllObservers())
                .thenReturn(answer);


        ResponseEntity<?> endpointResult = observerController.getAllObservers();

        int numberObserver = checkObserverAndGetTheirNumver(endpointResult);
        assertEquals(numberObserver, 3);

    }

    private static int checkObserverAndGetTheirNumver(ResponseEntity<?> endpointResult) {

        assert (endpointResult.getStatusCode() == HttpStatus.OK);
        //check if its a good instance
        Object contentResponseEntity = endpointResult.getBody();
        assert (contentResponseEntity instanceof Iterable<?>);

        //cast to avoid problems
        Iterable<?> contentIterables = (Iterable<?>) contentResponseEntity;


        int numberObserver = 0;
        for (Object observer : contentIterables) {
            //check type
            assert (observer instanceof ObserverDto);
            //cast
            ObserverDto trueObserverDto = (ObserverDto) observer;
            //check ids
            assert (trueObserverDto.getId() == 1 || trueObserverDto.getId() == 2 || trueObserverDto.getId() == 10);

            numberObserver++;
        }
        return numberObserver;
    }
}
