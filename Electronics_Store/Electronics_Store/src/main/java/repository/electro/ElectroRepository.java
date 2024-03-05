package repository.electro;

import model.Electro;
import model.User;

import java.util.List;
import java.util.Optional;

public interface ElectroRepository {

    List <Electro> findAll();
    Optional<Electro> findById(Long id);
    boolean save(Electro electro);
    void removeAll();
    Electro findByTitle(String title);
    boolean deleteElectroByTitle(String title);
    boolean updateStockByTitle(String title, int stock);
    boolean updateStockById(Long id, int stock);
    boolean sellElectro(String title, User customer, User employee);
    List<Electro> findSoldElectros();
    String getElectroTitleById(Long id);
    String getElectroCompanyById(Long id);

}
