package test;

import model.Electro;
import model.builder.ElectroBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.electro.ElectroRepository;
import repository.electro.ElectroRepositoryMock;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;



class JUnitMock {
   private ElectroRepository electroRep;
   private Electro electro1;
   private Electro electro2;
   private Electro electro3;

    @BeforeEach
    void initialize()
    {
        electroRep = new ElectroRepositoryMock();

        electro1 = new ElectroBuilder().
                setId(1L).
                setCompany("Apple").
                setTitle("Mac Computer").
                setPublishedDate(LocalDate.of(1999,10,22)).
                setStock(100).
                setDescription("telefon").
                setImagePath("C\\Desktop").
                setPrice(100)
                .build();

        electro2 = new ElectroBuilder().
                setId(2L).
                setCompany("Apple").
                setTitle("AirPods").
                setPublishedDate(LocalDate.of(1999,10,22)).
                setStock(245).
                setDescription("casti").
                setImagePath("C\\Desktop").
                setPrice(456)
                .build();
        electro3 = new ElectroBuilder().
                setId(3L).
                setCompany("Samsung").
                setTitle("Tablet").
                setPublishedDate(LocalDate.of(1999,10,22)).
                setStock(332).
                setDescription("tableta inteligenta").
                setImagePath("C\\Desktop").
                setPrice(33)
                .build();
        electroRep.save(electro1);
        electroRep.save(electro2);
        electroRep.save(electro3);

    }

    @Test
    @DisplayName("save Test")
    void testSave() {
        electroRep.removeAll();
        assert(electroRep.save(electro1));
        assert(electroRep.save(electro2));
        assert(electroRep.save(electro3));
    }

    @Test
    @DisplayName("findbyId Test")
    void testFindById()
    {
        Optional<Electro> b = electroRep.findById(1L);
        //assertEquals("Optional[ Electro author: Ion Alexe | title: Cartea Junglei | Published Date: 1999-10-22 ]",b.toString());
        assertEquals("Optional[Electro{" +
                            "id=" + 1L +
                            ", company='" + "Apple" + '\'' +
                            ", description='" + "telefon" + '\'' +
                            ", title='" + "Mac Computer" + '\'' +
                            ", publishedDate=" + "1999-10-22" +
                            ", imagePath='" + "C\\Desktop" + '\'' +
                            ", stock=" + 100 +
                            ", price=" + 100 +
                            '}'+"]" ,
                b.toString());
    }

    @Test
    void testFindAll()
    {
        List<Electro> allElectros = electroRep.findAll();

        assertTrue(allElectros.size() > 0);
    }


}