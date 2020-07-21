package Habitats;

public class Restaurant extends Habitat {
    /**
     * variables for capacity
     */
    private int maxCapacity;
    private int capacity = 0;

    /**
     * Constructor for Restaurant
     * @param areaType
     * @param positionX
     * @param positionY
     * @param dimH
     * @param dimW
     * @param maxCapacity
     */
    public Restaurant(String areaType, int positionX, int positionY, int dimH, int dimW, int maxCapacity) {
        super(areaType, positionX, positionY, dimH, dimW);
      this.maxCapacity = maxCapacity;
    }

    /**
     * getter for maxcapacity
     * @return
     */
    public int getMaxCapacity() {
        return maxCapacity;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                ", areaType='" + this.getAreaType() +
                ", dimH=" + this.getDimH() +
                ", dimW=" + this.getDimW() +
                ", habitatID=" + this.getHabitatID() +
                ", maxCapacity=" + maxCapacity +
                ", capacity=" + capacity +
                '}';
    }
}
