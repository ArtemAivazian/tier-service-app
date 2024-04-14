package cz.cvut.fel.nss.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdatedEvent {

    private Long productId;  // The ID of the product that was updated
    private Instant timestamp;  // The time at which the event was generated
    private Map<String, Object> changes;  // A map of the fields that were changed and their new values
//    public ProductUpdatedEvent(Long productId, Map<String, Object> changes) {
//        this.productId = productId;
//        this.timestamp = Instant.now();
//        this.changes = changes;
//    }
}
