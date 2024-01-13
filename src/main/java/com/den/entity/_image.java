package com.den.entity;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.seasar.doma.*;

@Entity(metamodel = @Metamodel)
@Table(name = "image")
@Data
@NoArgsConstructor
public class _image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "public_id")
    private String publicId;

    private  String url;

    public _image(String publicId,String url) {
        this.publicId = publicId;
        this.url = url;
    }
}
