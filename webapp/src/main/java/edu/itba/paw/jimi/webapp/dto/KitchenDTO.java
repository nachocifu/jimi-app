package edu.itba.paw.jimi.webapp.dto;

import edu.itba.paw.jimi.models.Dish;
import edu.itba.paw.jimi.models.Table;

import java.net.URI;
import java.util.List;

public class KitchenDTO {

    private TableListDTO tables;
    private TableListDTO urgentTables;
    private DishListDTO dishes;

    public KitchenDTO(){
    }

    public KitchenDTO(List<Dish> dishes, List<Table> tables, List<Table> urgentTables, URI uri){
        this.dishes = new DishListDTO(dishes, uri);
        this.tables = new TableListDTO(tables, uri);
        this.urgentTables = new TableListDTO(urgentTables, uri);
    }

    public TableListDTO getTables() {
        return tables;
    }

    public void setTables(TableListDTO tables) {
        this.tables = tables;
    }

    public TableListDTO getUrgentTables() {
        return urgentTables;
    }

    public void setUrgentTables(TableListDTO urgentTables) {
        this.urgentTables = urgentTables;
    }

    public DishListDTO getDishes() {
        return dishes;
    }

    public void setDishes(DishListDTO dishes) {
        this.dishes = dishes;
    }

 }
