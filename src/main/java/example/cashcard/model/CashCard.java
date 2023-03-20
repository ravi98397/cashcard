package example.cashcard.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



@Entity(name = "cash_card")
public class CashCard {

	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;
	
	private double amount;
	
	private String owner;

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public CashCard() {}
	
	public CashCard(Long id, Double amount, String owner) {
		super();
		this.id = id;
		this.amount = amount;
		this.owner = owner;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	@Override
	public boolean equals(Object obj) {
		try {
			CashCard temp = (CashCard)obj;
			if(temp.getId() == this.id && temp.getAmount() == this.amount) {
				return true;
			}
			return false;
		}catch(Exception e) {
			return false;
		}
	}
	
	
}
