package fr.ecolnum.projectapi.controller;

import fr.ecolnum.projectapi.model.Pool;
import fr.ecolnum.projectapi.service.PoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path="/pool")
public class PoolController {
    @Autowired
    private PoolService poolService;
    @GetMapping("/pools")
    public @ResponseBody Iterable<Pool> getAllReservation() {
        return poolService.findAll();
    }
    @GetMapping("pool/{id}")
    public @ResponseBody Optional<Pool> getReservationById(@PathVariable(value = "id") int id){
        return poolService.finById(id);
    }
    @PostMapping(value = "pool/add")
    public @ResponseBody String CreateReservation(@RequestBody Pool pool) {
        if(poolService.save(pool)==null){
            return "Pool not create";
        }
        return "Pool create with success !";
    }
    @DeleteMapping(value = "pool/delete")
    public @ResponseBody String RemoveReservation(@RequestBody Pool pool){
        poolService.delete(pool);
        return "Delete with success !";
    }

    @PutMapping(value = "pool/modify")
    public @ResponseBody String ModifyReservation(@RequestBody Pool pool){
        poolService.update(pool);
        return "Pool modify with success !";
    }
}
