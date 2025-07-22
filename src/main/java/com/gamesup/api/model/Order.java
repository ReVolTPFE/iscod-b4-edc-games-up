package com.gamesup.api.model;

import java.util.Date;
import java.util.List;

public class Order {

	
	List<OrderLine> line;
	Date date;
	boolean paid;
	boolean delivered;
	boolean archived;
}
