package fr.ecolnum.projectapi.poolTest;


import fr.ecolnum.projectapi.DTO.PoolDto;
import fr.ecolnum.projectapi.controller.PoolController;
import fr.ecolnum.projectapi.exception.IdNotFoundException;
import fr.ecolnum.projectapi.exception.IdNotMatchingException;
import fr.ecolnum.projectapi.service.PoolService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PoolControllerTest {

    @InjectMocks
    PoolController poolController;

    @Mock
    PoolService poolService;
//    @Mock
//    PoolDto poolDto;

    @Test
    public void testGetAllPools() {
        //mock for poolService.getAllPools()
        final int numberPool = 3;
        final int[] ids = {1, 2, 4};


        //initializing object
        List<PoolDto> allPoolAvailableInTest = new ArrayList<>(numberPool);
        for (int i = 0; i < numberPool; i++) {
            PoolDto poolDto = new PoolDto();
            poolDto.setId(ids[i]); //1, 2 or 4
            allPoolAvailableInTest.add(poolDto);
        }

        //mock
        when(poolService.getAllPools())
                .thenReturn(allPoolAvailableInTest);


        ResponseEntity<?> functionResult = poolController.getAllPools();

        assert (functionResult.getStatusCode() == HttpStatus.OK);

        Object responseEntityContent = functionResult.getBody();
        assert (responseEntityContent instanceof Iterable<?>);


        List<Integer> possibleId = new ArrayList<>(List.of(1, 2, 2, 4));
        for (Object object : (Iterable<?>) responseEntityContent) {
            assert (object instanceof PoolDto);
            PoolDto pool = (PoolDto) object;

            assert (possibleId.contains(pool.getId()));
            possibleId.remove((Integer) pool.getId());
        }
    }

    @Test
    public void testGetPoolByIdNoError() throws IdNotFoundException {
        PoolDto pool = new PoolDto();
        int idTested = 3;
        pool.setId(idTested);
        when(poolService.findById(idTested))
                .thenReturn(pool);

        ResponseEntity<?> responseEntity = poolController.getPoolById(idTested);

        assert (responseEntity.getStatusCode() == HttpStatus.OK &&
                responseEntity.getBody() instanceof PoolDto &&
                ((PoolDto) responseEntity.getBody()).getId() == idTested);
    }

    @Test
    public void testGetPoolByIdWithError() throws IdNotFoundException {
        int idTested = 3;
        when(poolService.findById(idTested))
                .thenThrow(IdNotFoundException.class);

        ResponseEntity<?> responseEntity = poolController.getPoolById(idTested);

        assert (responseEntity.getStatusCode() == HttpStatus.NOT_FOUND);
    }

    @Test
    public void testCreatePoolNoError() throws IdNotFoundException {
        PoolDto poolDto = new PoolDto();
        PoolDto mockReturn = new PoolDto();
        when(poolService.createPool(poolDto))
                .thenReturn(mockReturn);

        ResponseEntity<?> resultController = poolController.createPool(poolDto);

        assert (resultController.getStatusCode() == HttpStatus.CREATED &&
                resultController.getBody() == mockReturn);
    }

    @Test
    public void testCreatePoolWithError() throws IdNotFoundException {
        PoolDto poolTested = new PoolDto();

        when(poolService.createPool(poolTested))
                .thenThrow(IdNotFoundException.class);

        ResponseEntity<?> responseEntity = poolController.createPool(poolTested);

        assert (responseEntity.getStatusCode() == HttpStatus.CONFLICT);
    }

    @Test
    void testModifyPoolNoError() throws IdNotFoundException, IdNotMatchingException {
        int idDto = 3;
        PoolDto poolDto = new PoolDto();
        PoolDto mockReturn = new PoolDto();
        when(poolService.modifyPool(idDto, poolDto))
                .thenReturn(mockReturn);

        ResponseEntity<?> resultController = poolController.modifyPool(idDto, poolDto);

        assert (resultController.getStatusCode() == HttpStatus.OK &&
                resultController.getBody() == mockReturn);
    }

    @Test
    void testModifyPoolWithErrorIdNotFound() throws IdNotFoundException, IdNotMatchingException {
        PoolDto poolTested = new PoolDto();
        int poolIdTested = 1;

        when(poolService.modifyPool(poolIdTested, poolTested))
                .thenThrow(IdNotFoundException.class);

        ResponseEntity<?> responseEntity = poolController.modifyPool(poolIdTested, poolTested);

        assert (responseEntity.getStatusCode() == HttpStatus.CONFLICT);
    }

    @Test
    void testModifyPoolWithErrorPoolNotMatching() throws IdNotFoundException, IdNotMatchingException {
        PoolDto poolTested = new PoolDto();
        int poolIdTested = 1;

        when(poolService.modifyPool(poolIdTested, poolTested))
                .thenThrow(IdNotMatchingException.class);

        ResponseEntity<?> responseEntity = poolController.modifyPool(poolIdTested, poolTested);

        assert (responseEntity.getStatusCode() == HttpStatus.CONFLICT);
    }

}
