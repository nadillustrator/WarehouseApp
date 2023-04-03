package com.example.warehouseapp.services;

import com.example.warehouseapp.model.Income;
import com.example.warehouseapp.model.Outcome;
import com.example.warehouseapp.model.Socks;
import com.example.warehouseapp.repositories.IncomeRepository;
import com.example.warehouseapp.repositories.OutcomeRepository;
import com.example.warehouseapp.repositories.SocksRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SocksService {

    private final SocksRepository socksRepository;
    private final IncomeRepository incomeRepository;
    private final OutcomeRepository outcomeRepository;

    public SocksService(SocksRepository socksRepository,
                        IncomeRepository incomeRepository,
                        OutcomeRepository outcomeRepository) {
        this.socksRepository = socksRepository;
        this.incomeRepository = incomeRepository;
        this.outcomeRepository = outcomeRepository;
    }

    /**
     * Adding socks to the DB.
     * <br>If the cottonPart is less than 0 or more than100% or the quantity is less than 0,
     * the method returns null.
     * <br>If socks with this id are found in the DB, then the method increases their quantity.
     * If there are no socks with this id in the DB,  then the method will add a new row.
     * <br>
     * If the user made a mistake and passed an object that does not have the same id,
     * color and content of the cotton with the DB object,
     * then the request will not be executed and null will be returned.
     * <br>
     * {@link JpaRepository#save(Object)}
     * {@link JpaRepository#findById(Object)}
     *
     * @param socks
     * @return Socks
     */
    public Socks addSocks(Socks socks) {
        int cottonPart = socks.getCottonPart();
        if (cottonPart > 100 || cottonPart < 0) {
            return null;
        }
        if (socks.getQuantity() < 0) {
            return null;
        }
        Socks socksDB = socksRepository.findById(socks.getId()).orElse(null);
        if (socksDB == null) {
            Socks newSocks = socksRepository.save(socks);
            saveIncome(newSocks);
            return newSocks;
        }
        if (!socks.getColor().equals(socksDB.getColor()) || cottonPart != socksDB.getCottonPart()) {
            return null;
        }
        int sum = socks.getQuantity() + socksDB.getQuantity();
        socks.setQuantity(sum);
        saveIncome(socks);
        return socksRepository.save(socks);
    }

    /**
     * Outcome socks from the DB.
     * <br> If  the quantity is less than 0 the method returns null.
     * <br> If the quantity requested by the user is greater than the quantity in the database,
     * the method will return zero. In other cases, the quantity in the DB will decrease.
     * <br>
     * {@link JpaRepository#save(Object)} 
     * {@link JpaRepository#findById(Object)}
     *
     * @param socks
     * @return Socks
     */
    public Socks outcomeSocks(Socks socks) {
        if (socks.getQuantity() < 0) {
            return null;
        }
        int outcome = socks.getQuantity();
        Socks socksAvailability = socksRepository.findById(socks.getId()).orElse(null);
        if (socksAvailability == null) {
            return null;
        }
        int quantity = socksAvailability.getQuantity();
        int balance = quantity - outcome;
        socksAvailability.setQuantity(balance);
        if (balance < 0) {
            return null;
        }
        saveOutcome(socksAvailability);
        return socksRepository.save(socksAvailability);
    }

    /**
     * Search for socks in the DB by id
     *
     * @param id
     * @return Socks
     */
    public Socks findById(Long id) {
        return socksRepository.findById(id).orElse(null);
    }

    /**
     * Returns the total number of socks in DB that match the query criteria passed in the parameters.
     * <br>Depending on the user-specified operation parameter, different repository methods are invoked.
     * <p>
     * {@link SocksService#findByColorAndCottonPart(String, String, int)}

     *
     * @param color
     * @param operation  (String "moreThan",  "lessThan" or "equal")
     * @param cottonPart
     * @return Integer
     */
    public Integer findQuantityOfSocksByColorAndCottonPart(String color, String operation, int cottonPart) {
        List<Socks> socksList = findByColorAndCottonPart(color, operation, cottonPart);
        if(socksList == null || socksList.size()==0){
            return null;
        }
        return socksList.stream().mapToInt(Socks::getQuantity).sum();
    }

    /**
     * Returns the collection of socks in DB that match the query criteria passed in the parameters.
     * <br>Depending on the user-specified operation parameter, different repository methods are invoked.
     * <p>
     * {@link SocksRepository#findSocksByColorAndCottonPartGreaterThan(String, int)} 
     * {@link SocksRepository#findSocksByColorAndCottonPartLessThan(String, int)} 
     * {@link SocksRepository#findSocksByColorAndCottonPart(String, int)}
     *
     * @param color
     * @param operation  (String "moreThan",  "lessThan" or "equal")
     * @param cottonPart
     * @return List<Socks>
     */
    public List<Socks> findByColorAndCottonPart(String color, String operation, int cottonPart) {
        return switch (operation) {
            case "moreThan" -> socksRepository.findSocksByColorAndCottonPartGreaterThan(color, cottonPart);
            case "lessThan" -> socksRepository.findSocksByColorAndCottonPartLessThan(color, cottonPart);
            case "equal" -> socksRepository.findSocksByColorAndCottonPart(color, cottonPart);
            default -> null;
        };
    }

    /**
     * Records the income of socks in stock in DB income
     *
     * @param socks
     */
    private void saveIncome(Socks socks) {
        Income income = new Income();
        income.setDateIncome(LocalDateTime.now());
        income.setSocksId(socks);
        incomeRepository.save(income);
    }

    /**
     * Records the outcome of socks in stock in DB outcome
     *
     * @param socks
     */
    private void saveOutcome(Socks socks) {
        Outcome outcome = new Outcome();
        outcome.setDateOutcome(LocalDateTime.now());
        outcome.setSocksId(socks);
        outcomeRepository.save(outcome);
    }
}
