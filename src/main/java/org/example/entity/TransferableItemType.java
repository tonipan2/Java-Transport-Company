package org.example.entity;

public enum TransferableItemType {

     GOODS(10), PEOPLE(20);
     private double fare;

     TransferableItemType(double fare) {
          this.fare = fare;
     }

     public double getFare() {
          return fare;
     }

     public void setFare(double fare) {
          this.fare = fare;
     }
}
