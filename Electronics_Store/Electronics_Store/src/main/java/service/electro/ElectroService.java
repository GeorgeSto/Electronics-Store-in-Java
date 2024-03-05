package service.electro;

import model.Electro;
import model.User;

import java.util.List;

public interface ElectroService {

    List<Electro> findAll();
    Electro findById(Long id);

    boolean save(Electro book);

    int getAgeOfElectro(Long id);
    boolean deleteElectroByTitle(String title);
    boolean updateStockByTitle(String title, int stock);
    boolean updateStockById(Long id, int stock);
    Electro findByTitle(String title);
    boolean sellElectro(String title, User customer, User employee);
    List<Electro> findSoldElectros();

    String getTitleById(Long id);

    String getCompanyById(Long id);
}