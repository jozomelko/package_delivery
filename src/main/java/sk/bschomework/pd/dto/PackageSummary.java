package sk.bschomework.pd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PackageSummary {
    public Double weight;
    public String postalCode;
}
