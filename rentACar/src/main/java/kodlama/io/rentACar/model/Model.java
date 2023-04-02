package kodlama.io.rentACar.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "models")
public class Model {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "brand_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Brand brand;

    @OneToMany(mappedBy = "model", cascade = CascadeType.ALL)
    private List<Car> cars;
}