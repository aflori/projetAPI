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
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

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
        assert(controllerResponse.getStatusCode()==HttpStatus.CONFLICT);
    }
}
