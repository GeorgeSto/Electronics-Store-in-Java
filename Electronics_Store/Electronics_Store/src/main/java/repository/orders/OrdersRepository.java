package repository.orders;

import model.Electro;
import model.Orders;
import model.User;

import java.util.List;
import java.util.Optional;

public interface OrdersRepository {

    List<Orders> findAll();

    Optional<Orders> findById(Long id);

    boolean save(Orders order);

    Orders findByUser_id(Long id);

    void addElectroById(Electro electro, int quantity, User user);

    boolean updateQuantityById(Long id,int quantity);
    void removeAll();

    boolean removeById(Long id);






}
