package model.builder;

import model.Electro;

import java.time.LocalDate;

public class ElectroBuilder {
    private Electro electro;

    public ElectroBuilder(){
        electro = new Electro();
    }

    public ElectroBuilder setId(Long id){
        electro.setId(id);
        return this;
    }

    public ElectroBuilder setCompany(String author){
        electro.setCompany(author);
        return this;
    }

    public ElectroBuilder setTitle(String title){
        electro.setTitle(title);
        return this;
    }


    public ElectroBuilder setPublishedDate(LocalDate publishedDate){
        electro.setPublishedDate(publishedDate);
        return this;
    }

    public ElectroBuilder setStock(int stock)
    {
        electro.setStock(stock);
        return this;
    }

    public ElectroBuilder setDescription(String descrip){
        electro.setDescription(descrip);
        return this;
    }

    public ElectroBuilder setImagePath(String imagePath){
        electro.setImagePath(imagePath);
        return this;
    }

    public ElectroBuilder setPrice(int price){
        electro.setPrice(price);
        return this;
    }

    public Electro build(){
        return electro;
    }
}
