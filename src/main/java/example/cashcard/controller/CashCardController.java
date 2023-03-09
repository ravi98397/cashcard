package example.cashcard.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import example.cashcard.model.CashCard;

@RestController
@RequestMapping("/cashcards")
public class CashCardController {

   @GetMapping("/{requestedId}")
   public ResponseEntity<String> findById() {
         return ResponseEntity.ok("{}");
   }
}
