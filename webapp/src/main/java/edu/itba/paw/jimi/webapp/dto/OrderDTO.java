package edu.itba.paw.jimi.webapp.dto;

import edu.itba.paw.jimi.models.DishData;
import edu.itba.paw.jimi.models.Order;
import edu.itba.paw.jimi.models.OrderStatus;

import java.net.URI;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderDTO {

	private long id;
	private int diners;
	private Float total;
	private Map<DishDTO, DishData> unDoneDishes;
	private Map<DishDTO, Integer> doneDishes;
	private Date openedAt;
	private Date closedAt;
	private OrderStatus status;
	private URI uri;

	public OrderDTO() {
	}

	public OrderDTO(Order order) {
		this.id = order.getId();
		this.diners = order.getDiners();
		this.total = order.getTotal();
		this.unDoneDishes = order.getUnDoneDishes().entrySet().stream()
				.collect(Collectors.toMap(e -> new DishDTO(e.getKey()), Map.Entry::getValue));
		this.doneDishes = order.getDoneDishes().entrySet().stream()
				.collect(Collectors.toMap(e -> new DishDTO(e.getKey()), Map.Entry::getValue));
		this.openedAt = order.getOpenedAt();
		this.closedAt = order.getClosedAt();
		this.status = order.getStatus();
	}

	public OrderDTO(Order order, URI baseURI) {
		this.id = order.getId();
		this.diners = order.getDiners();
		this.total = order.getTotal();
		this.unDoneDishes = order.getUnDoneDishes().entrySet().stream()
				.collect(Collectors.toMap(e -> new DishDTO(e.getKey()), Map.Entry::getValue));
		this.doneDishes = order.getDoneDishes().entrySet().stream()
				.collect(Collectors.toMap(e -> new DishDTO(e.getKey()), Map.Entry::getValue));
		this.openedAt = order.getOpenedAt();
		this.closedAt = order.getClosedAt();
		this.status = order.getStatus();
		this.uri = baseURI.resolve(String.valueOf(this.id));
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getDiners() {
		return diners;
	}

	public void setDiners(int diners) {
		this.diners = diners;
	}

	public Float getTotal() {
		return total;
	}

	public void setTotal(Float total) {
		this.total = total;
	}

	public Map<DishDTO, DishData> getUnDoneDishes() {
		return unDoneDishes;
	}

	public void setUnDoneDishes(Map<DishDTO, DishData> unDoneDishes) {
		this.unDoneDishes = unDoneDishes;
	}

	public Map<DishDTO, Integer> getDoneDishes() {
		return doneDishes;
	}

	public void setDoneDishes(Map<DishDTO, Integer> doneDishes) {
		this.doneDishes = doneDishes;
	}

	public Date getOpenedAt() {
		return openedAt;
	}

	public void setOpenedAt(Date openedAt) {
		this.openedAt = openedAt;
	}

	public Date getClosedAt() {
		return closedAt;
	}

	public void setClosedAt(Date closedAt) {
		this.closedAt = closedAt;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public URI getUri() {
		return uri;
	}

	public void setUri(URI uri) {
		this.uri = uri;
	}
}
