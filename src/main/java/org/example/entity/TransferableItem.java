package org.example.entity;

import jakarta.persistence.*;

@Entity
@Table(name="transferable item")
public class TransferableItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="weight")
    private double weight;
    @Column(name="type")
    private TransferableItemType type;

    public TransferableItem() {
    }

    public TransferableItem(double weight, TransferableItemType type) {
        this.weight = weight;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public TransferableItemType getType() {
        return type;
    }

    public void setType(TransferableItemType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "TransferableItem{" +
                "id=" + id +
                ", weight=" + weight +
                ", type=" + type +
                '}';
    }
}
