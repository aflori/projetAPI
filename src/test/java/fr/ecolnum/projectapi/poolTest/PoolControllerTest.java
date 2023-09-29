package fr.ecolnum.projectapi.poolTest;


import fr.ecolnum.projectapi.DTO.PoolDto;
import fr.ecolnum.projectapi.controller.PoolController;
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
        final int[] ids = {1,2,4};


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
    public void testGetPoolByIdNoError() {

    }

    @Test
    public void testGetPoolByIdWithError() {

    }

    @Test
    public void testCreatePoolNoError() {

    }

    @Test
    public void testCreatePoolWithError(){

    }

    @Test void testModifyPoolNoError(){

    }

    @Test void testModifyPoolWithError(){

    }

}
