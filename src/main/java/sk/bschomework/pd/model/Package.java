package sk.bschomework.pd.model;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class Package {
    public Double weight;
    public String postalCode;
    public Double fee;
}
