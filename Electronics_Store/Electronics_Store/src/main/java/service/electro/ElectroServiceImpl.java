package service.electro;

import model.Electro;
import model.User;
import repository.electro.ElectroRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class ElectroServiceImpl implements ElectroService {
    private final ElectroRepository electroRepository;

    public ElectroServiceImpl(ElectroRepository electroRepository){
        this.electroRepository = electroRepository;
    }

    @Override
    public List<Electro> findAll() {
        return electroRepository.findAll();
    }

    @Override
    public Electro findById(Long id) {
        return electroRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Electro with id: %d not found".formatted(id)));
    }

    @Override
    public boolean save(Electro electro) {
        return electroRepository.save(electro);
    }

    @Override
    public int getAgeOfElectro(Long id) {
        Electro electro = this.findById(id);

        LocalDate now = LocalDate.now();

        return (int) ChronoUnit.YEARS.between(electro.getPublishedDate(), now);
    }
    @Override
    public Electro findByTitle(String title)
    {
        return electroRepository.findByTitle(title);
    }
    @Override
    public boolean deleteElectroByTitle(String title)
    {
        return electroRepository.deleteElectroByTitle(title);
    }

    @Override
    public boolean updateStockByTitle(String title,int stock)
    {
        return electroRepository.updateStockByTitle(title, stock);
    }
    public boolean updateStockById(Long id,int stock)
    {
        return electroRepository.updateStockById(id, stock);
    }
    @Override
    public boolean sellElectro(String title, User customer, User employee)
    {
        return electroRepository.sellElectro(title, customer, employee);
    }
    @Override
    public List<Electro> findSoldElectros()
    {
        return electroRepository.findSoldElectros();
    }

    @Override
    public String getTitleById(Long id) {
        return electroRepository.getElectroTitleById(id);
    }

    @Override
    public String getCompanyById(Long id) {
        return electroRepository.getElectroCompanyById(id);
    }


}