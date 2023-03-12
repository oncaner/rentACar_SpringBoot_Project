package kodlama.io.rentACar.entities.concretes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Table(name = "brands")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity // It is a database entity
public class Brand {
    @Column(name = "id")
    @Id // Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto Increment
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
    List<Model> models;
}
