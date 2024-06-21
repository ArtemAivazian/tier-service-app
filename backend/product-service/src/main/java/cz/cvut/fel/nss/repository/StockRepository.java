package cz.cvut.fel.nss.repository;

import cz.cvut.fel.nss.data.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Stock entities.
 */
@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    /**
     * Finds a stock by its ID.
     *
     * @param stockId the ID of the stock
     * @return the stock entity
     */
    Stock findByStockId(Long stockId);
}
