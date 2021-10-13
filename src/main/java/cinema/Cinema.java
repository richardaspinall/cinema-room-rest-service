package cinema;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

public class Cinema {
    int totalRows;
    int totalColumns;
    List<Seat> seats = new ArrayList<>();

    public Cinema(int rows, int columns) {
        this.totalRows = rows;
        this.totalColumns = columns;
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= columns; j++) {
                // Price is determined by row number (row <= 4 is $10 and $8 otherwise)
                seats.add(new Seat(i, j, (i < 4) ? 10 : 8));
            }
        }
    }

    @JsonProperty("total_rows")
    public int getTotalRows() {
        return totalRows;
    }

    @JsonProperty("total_columns")
    public int getTotalColumns() {
        return totalColumns;
    }

    @JsonProperty("available_seats")
    public List<Seat> getAvailableSeats() {
        List availableSeats = seats.stream()
                .filter(s -> !s.isPurchased()).collect(Collectors.toList());
        return availableSeats;
    }

    public Map purchaseSeat(int row, int column) {
        for (Seat seat : seats) {
            if (seat.getRow() == row && seat.getColumn() == column) {
                if (!seat.isPurchased()) {
                    Token token = new Token();
                    seat.setToken(token);
                    seat.setPurchased(true);
                    return Map.of("token", seat.getToken(), "ticket", seat);
                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The ticket has been already purchased!");
                }

            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The number of a row or a column is out of bounds!");
    }

    public Map returnSeat(Token token) {
        for (Seat seat : seats) {
            if (seat.isPurchased() && seat.getToken().equals(token.getToken())) {
                seat.setToken(null);
                seat.setPurchased(false);
                return Map.of("returned_ticket", seat);
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong token!");
    }

    public Map getStats(String password) {
        if (password != null && password.equals("super_secret")) {
            long currentIncome = seats.stream()
                    .filter(s -> s.isPurchased())
                    .reduce(0, (subtotal, element) -> subtotal + element.getPrice(), Integer::sum);
            long number_of_available_seats = seats.stream()
                    .filter(s -> !s.isPurchased()).count();

            long number_of_purchased_tickets = seats.stream()
                    .filter(s -> s.isPurchased()).count();

            return Map.of("current_income", currentIncome,
                    "number_of_available_seats", number_of_available_seats,
                    "number_of_purchased_tickets", number_of_purchased_tickets);
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "The password is wrong!");
    }
}
