package fr.ecolnum.projectapi.controller;

import fr.ecolnum.projectapi.service.PoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path="/pool")
public class PoolController {
    @Autowired
    private PoolService poolService;
    @GetMapping("/pools")
    public @ResponseBody Iterable<Reservation> getAllReservation() {
        return reservationService.findAll();
    }
}
