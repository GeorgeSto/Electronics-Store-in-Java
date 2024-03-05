package service.orders;

import model.Orders;
import org.junit.jupiter.api.Order;
import repository.orders.OrdersRepository;

import java.util.List;

public class OrdersServiceImpl implements OrdersService {

    private final OrdersRepository ordersRepository;

    public OrdersServiceImpl(OrdersRepository ordersRepository){
        this.ordersRepository = ordersRepository;
    }
    @Override
    public List<Orders> findAll(){
        return ordersRepository.findAll();
    }
    @Override
    public Orders findById(Long id){
        return ordersRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("Order with id: %d not found".formatted(id)));

    }
    @Override
    public boolean save(Orders order){return ordersRepository.save(order);}

    @Override
    public Orders findByUser_id(Long id) {
        return ordersRepository.findByUser_id(id);
    }

    @Override
    public boolean updateQuantityById(Long id,int quantity) {
        return ordersRepository.updateQuantityById(id,quantity);
    }

    @Override
    public boolean removeById(Long id) {
        return ordersRepository.removeById(id);
    }

    @Override
    public void removeAll() {
        ordersRepository.removeAll();
    }


}
