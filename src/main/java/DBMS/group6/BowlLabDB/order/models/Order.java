package DBMS.group6.BowlLabDB.order.models;
import org.springframework.data.annotation.Id;

import java.sql.Timestamp;
//import java.util.List;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record Order(@Id Integer id, 
                @NotNull Integer ordered_by,
                @NotNull Timestamp date_ordered,
                @NotNull @Positive Float total_price,
                @NotNull Boolean order_complete 
) {
}