package sakila;

// Generated 07-mar-2017 10:05:25 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Inventory generated by hbm2java
 */
public class Inventory implements java.io.Serializable {

	private int inventoryId;
	private Store store;
	private Film film;
	private Date lastUpdate;
	private Set rentals = new HashSet(0);

	public Inventory() {
	}

	public Inventory(Store store, Film film, Date lastUpdate) {
		this.store = store;
		this.film = film;
		this.lastUpdate = lastUpdate;
	}

	public Inventory(Store store, Film film, Date lastUpdate, Set rentals) {
		this.store = store;
		this.film = film;
		this.lastUpdate = lastUpdate;
		this.rentals = rentals;
	}

	public int getInventoryId() {
		return this.inventoryId;
	}

	public void setInventoryId(int inventoryId) {
		this.inventoryId = inventoryId;
	}

	public Store getStore() {
		return this.store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public Film getFilm() {
		return this.film;
	}

	public void setFilm(Film film) {
		this.film = film;
	}

	public Date getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Set getRentals() {
		return this.rentals;
	}

	public void setRentals(Set rentals) {
		this.rentals = rentals;
	}

}