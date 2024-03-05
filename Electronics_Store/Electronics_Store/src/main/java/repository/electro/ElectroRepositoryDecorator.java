package repository.electro;

public abstract class ElectroRepositoryDecorator implements ElectroRepository {

    protected ElectroRepository decoratedRepository;

    public ElectroRepositoryDecorator(ElectroRepository electroRepository)
    {
        this.decoratedRepository = electroRepository;
    }


}
