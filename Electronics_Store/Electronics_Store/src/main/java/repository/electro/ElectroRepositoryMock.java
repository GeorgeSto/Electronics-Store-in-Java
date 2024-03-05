package repository.electro;

import model.Electro;
import model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ElectroRepositoryMock implements ElectroRepository{

    private final List<Electro> electros;

    public ElectroRepositoryMock(){
        electros = new ArrayList<>();
    }
    @Override
    public List<Electro> findAll() {
        return electros;
    }

    @Override
    public Optional<Electro> findById(Long id) {
        return electros.parallelStream()
                .filter(it -> it.getId().equals(id))
                .findFirst();
    }

    @Override
    public boolean save(Electro electro) {
        return electros.add(electro);
    }

    @Override
    public void removeAll() {
        electros.clear();
    }

    public Electro findByTitle(String title) {
        return electros.parallelStream()
                .filter(electro -> electro.getTitle().equals(title))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean deleteElectroByTitle(String title) {
        return electros.removeIf(electro -> electro.getTitle().equals(title));
    }

    @Override
    public boolean updateStockByTitle(String title, int newStock) {
        for (Electro electro : electros) {
            if (electro.getTitle().equals(title)) {
                electro.setStock(newStock);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updateStockById(Long id, int stock) {
        return false;
    }

    @Override
    public boolean sellElectro(String title, User customer, User employee) {
        return false;
    }
    public List<Electro> findSoldElectros()
    {
        return null;
    }
    public String getElectroTitleById(Long id){ return null;}

    @Override
    public String getElectroCompanyById(Long id) {
        return null;
    }


}
