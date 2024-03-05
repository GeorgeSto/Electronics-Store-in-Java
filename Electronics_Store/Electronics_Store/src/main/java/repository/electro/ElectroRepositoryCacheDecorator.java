package repository.electro;

import model.Electro;
import model.User;

import java.util.List;
import java.util.Optional;

public class ElectroRepositoryCacheDecorator implements ElectroRepository {

    private final ElectroRepository decoratedRepository;
    private final Cache cache;

    public ElectroRepositoryCacheDecorator(ElectroRepository electroRepository, Cache cache) {
        this.decoratedRepository = electroRepository;
        this.cache = cache;
    }

    @Override
    public List findAll() {
        if (cache.hasResult()) {
            return cache.load();
        }

        List electros = decoratedRepository.findAll();
        cache.save(electros);
        return electros;
    }

    @Override
    public Optional findById(Long id) {
        return decoratedRepository.findById(id);
    }

    @Override
    public boolean save(Electro electro) {
        cache.invalidateCache();
        return decoratedRepository.save(electro);
    }

    @Override
    public void removeAll() {
        cache.invalidateCache();
        decoratedRepository.removeAll();
    }

    @Override
    public Electro findByTitle(String title) {
        return decoratedRepository.findByTitle(title);
    }

    @Override
    public boolean deleteElectroByTitle(String title){
        return decoratedRepository.deleteElectroByTitle(title);
    }

    @Override
    public boolean updateStockByTitle(String title, int stock)
    {
        return decoratedRepository.updateStockByTitle(title, stock);
    }

    @Override
    public boolean updateStockById(Long id, int stock) {
        return decoratedRepository.updateStockById(id,stock);
    }

    public boolean sellElectro(String title, User customer, User employee)
    {
        return decoratedRepository.sellElectro(title, customer, employee);
    }
    public List<Electro> findSoldElectros(){
        return decoratedRepository.findSoldElectros();
    }
    public String getElectroTitleById(Long id){ return decoratedRepository.getElectroTitleById(id);}

    @Override
    public String getElectroCompanyById(Long id) {
        return decoratedRepository.getElectroCompanyById(id);
    }
}
