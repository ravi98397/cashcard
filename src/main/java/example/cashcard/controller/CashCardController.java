package example.cashcard.controller;

import java.net.URI;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import example.cashcard.model.CashCard;
import example.cashcard.repository.CashCardRepository;


@RestController
@RequestMapping("/cashcards")
public class CashCardController {
	
	@Autowired
	private CashCardRepository cashCardRepository;

	   @GetMapping("/{requestedId}")
	   public ResponseEntity<CashCard> findById(@PathVariable Long requestedId) {
		   System.out.println(requestedId);
		   Optional<CashCard> cashCardOptional = cashCardRepository.findById(requestedId);
		     if (cashCardOptional.isPresent()) {
		         return ResponseEntity.ok(cashCardOptional.get());
		     } else {
		         return ResponseEntity.notFound().build();
		     }
	   }
	   
	   @PostMapping
	   private ResponseEntity createCashCard(@RequestBody CashCard newCashCardRequest, UriComponentsBuilder ubc) {
		  CashCard savedCashCard = cashCardRepository.save(newCashCardRequest);
		  URI locationOfNewCashCard = ubc.path("cashcards/{id}")
				  .buildAndExpand(savedCashCard.getId())
				  .toUri();
	      return ResponseEntity.created(locationOfNewCashCard).build();
	   }
	   
	   @GetMapping
	   public ResponseEntity<Collection<CashCard>> findAll(Pageable pageable) {
	       Page<CashCard> page = cashCardRepository.findAll(
	               PageRequest.of(
	                       pageable.getPageNumber(),
	                       pageable.getPageSize(),
	                       pageable.getSortOr(Sort.by(Sort.Direction.ASC, "amount"))
	       ));
	       return ResponseEntity.ok(page.toList());
	   }
	   
	   
}
