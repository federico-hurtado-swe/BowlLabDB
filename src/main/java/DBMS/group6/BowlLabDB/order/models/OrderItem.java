package DBMS.group6.BowlLabDB.order.models;
import org.springframework.data.annotation.Id;

//import java.util.List;
import jakarta.validation.constraints.NotNull;


public record OrderItem(@Id Integer item_id, 
                @NotNull Integer order_id

) {
}