package model.builder;

import model.Orders;
import org.junit.jupiter.api.Order;

import java.time.LocalDate;

public class OrderBuilder {

    private Orders order;

    public OrderBuilder(){
        this.order = new Orders();
    }

    public OrderBuilder setId(Long id){
        order.setId(id);
        return this;
    }

    public OrderBuilder setUser_id(Long id){
        order.setUser_id(id);
        return this;
    }

    public OrderBuilder setElectro_id(Long id){
        order.setElectro_id(id);
        return this;
    }

    public OrderBuilder setQuantity(int quant){
        order.setQuantity(quant);
        return this;
    }

    public OrderBuilder setPrice(int price){
        order.setPrice(price);
        return this;
    }

    public OrderBuilder setDate(LocalDate date){
        order.setDate_created(date);
        return this;
    }

    public Orders build() {return order;}


}
