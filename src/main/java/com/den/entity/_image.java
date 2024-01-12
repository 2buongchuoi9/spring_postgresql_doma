package com.den.entity;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Metamodel;
import org.seasar.doma.Table;

@Entity(metamodel = @Metamodel)
@Table(name = "image")
@Data
@NoArgsConstructor
public class _image {
    @Id
    @NotNull(message = "link is must require")
    private String link;

    public _image(String link) {
        this.link = link;
    }
}
