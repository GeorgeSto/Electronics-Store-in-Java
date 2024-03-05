package service.orders;

import model.Orders;
import java.util.List;

public interface OrdersService {
    
    List <Orders> findAll();

    Orders findById(Long id);

    boolean save(Orders order);

    Orders findByUser_id(Long id);

    boolean updateQuantityById(Long id,int quantity);

    boolean removeById(Long id);

    void removeAll();


}
