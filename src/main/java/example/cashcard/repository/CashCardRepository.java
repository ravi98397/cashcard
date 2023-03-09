package example.cashcard.repository;

import org.springframework.data.repository.CrudRepository;

import example.cashcard.model.CashCard;

public interface CashCardRepository extends CrudRepository<CashCard, Long>{
	
}
