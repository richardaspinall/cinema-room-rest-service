package cinema;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class CinemaController {

    int totalRows = 9;
    int totalColumns = 9;

    Cinema cinema = new Cinema(totalRows, totalColumns);

    @GetMapping("/seats")
    public Cinema getSeats() {
        return cinema;
    }

    @PostMapping("/purchase")
    public Map purchaseSeat(@RequestBody Seat seatToPurchase) {

        return cinema.purchaseSeat(seatToPurchase.getRow(), seatToPurchase.getColumn());
    }

    @PostMapping("/return")
    public Map returnSeat(@RequestBody Token token) {
        return cinema.returnSeat(token);
    }

    @PostMapping("/stats")
    public Map getStats(@RequestParam (required = false) String password){
        return cinema.getStats(password);
    }
}

