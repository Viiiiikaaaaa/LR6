package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class TVSet {
    private int ID;
    private String name;
    private String brand;
    private int diagonal;
    private int pricePerItem;
    private double rating;

}
