public class House {
    String address;
    String description;
    String startDate;
    String numStories;
    String numBeds;
    String numBaths;
    String price;
    String yearBuilt;
    public House(String address,String description,String numStories,String numBeds,String numBaths,String price, String yearBuilt, String startDate) {
        this.address = address;
        this.description = description;
        this.numStories = numStories;
        this.numBeds = numBeds;
        this.numBaths = numBaths;
        this.price = price;
        this.yearBuilt = yearBuilt;
        this.startDate = startDate;
    }
    public String getInfo() {
        return address+","+description+","+numStories+","+numBeds+","+numBaths+","+price+","+yearBuilt+","+startDate;
    }
    public String getDescription(){
        return description;
    }
}
