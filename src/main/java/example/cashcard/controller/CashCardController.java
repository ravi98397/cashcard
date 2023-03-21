package example.cashcard.controller;

import java.net.URI;
import java.security.Principal;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	   public ResponseEntity<CashCard> findById(@PathVariable Long requestedId, Principal principal) {
		   Optional<CashCard> cashCardOptional = Optional.ofNullable(cashCardRepository.findByIdAndOwner(requestedId, principal.getName()));
		     if (cashCardOptional.isPresent()) {
		         return ResponseEntity.ok(cashCardOptional.get());
		     } else {
		         return ResponseEntity.notFound().build();
		     }
	   }
	   
	   @PostMapping
	   private ResponseEntity createCashCard(@RequestBody CashCard newCashCardRequest, UriComponentsBuilder ubc, Principal principal) {
		  //CashCard savedCashCard = cashCardRepository.save(newCashCardRequest);
		  CashCard cashCardWithOwner = cashCardRepository.save(new CashCard(null, newCashCardRequest.getAmount(), principal.getName()));
		  
		  URI locationOfNewCashCard = ubc.path("cashcards/{id}")
				  .buildAndExpand(cashCardWithOwner.getId())
				  .toUri();
	      return ResponseEntity.created(locationOfNewCashCard).build();
	   }
	   
	   @GetMapping
	   public ResponseEntity<Collection<CashCard>> findAll(Pageable pageable, Principal principal) {
	       Page<CashCard> page = cashCardRepository.findByOwner(principal.getName(),
	               PageRequest.of(
	                       pageable.getPageNumber(),
	                       pageable.getPageSize(),
	                       pageable.getSortOr(Sort.by(Sort.Direction.ASC, "amount"))
	       ));
	       return ResponseEntity.ok(page.toList());
	   }
	   
	   @PutMapping("/{requestedId}")
	   private ResponseEntity<Void> putCashCard(@PathVariable Long requestedId, @RequestBody CashCard cashCardUpdate, Principal principal) {
	       CashCard cashCard = cashCardRepository.findByIdAndOwner(requestedId, principal.getName());
	       if (cashCard != null) {
	           CashCard updatedCashCard = new CashCard(cashCard.getId(), cashCardUpdate.getAmount(), principal.getName());
	           cashCardRepository.save(updatedCashCard);
	           return ResponseEntity.noContent().build();
	       }
	       return ResponseEntity.notFound().build();
	   }
	   
	   @DeleteMapping("/{id}")
	   private ResponseEntity<Void> deleteCashCard(@PathVariable Long id, Principal principal) {
		   if (!cashCardRepository.existsByIdAndOwner(id, principal.getName())) {
		        return ResponseEntity.notFound().build();
		    }
	       cashCardRepository.deleteById(id); // Add this line
	       return ResponseEntity.noContent().build();
	   }
}
