package model;

import model.builder.ElectroBuilder;

import java.io.File;
import java.time.LocalDate;

public class Electro extends ElectroBuilder {

    private Long id;

    private String company;

    private String description;

    private String title;

    private LocalDate publishedDate;

    private String imagePath;

    private int stock;

    private int price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Electro{" +
                "id=" + id +
                ", company='" + company + '\'' +
                ", description='" + description + '\'' +
                ", title='" + title + '\'' +
                ", publishedDate=" + publishedDate +
                ", imagePath='" + imagePath + '\'' +
                ", stock=" + stock +
                ", price=" + price +
                '}';
    }
}