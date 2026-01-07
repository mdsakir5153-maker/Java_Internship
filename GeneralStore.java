class Product {


    private String productName;
    private double price;


    static String storeName = "General Store";

    static void changeName(String newName) {
        storeName = newName;
    }


    Product(String productName) {
        this.productName = productName;
        this.price = 0.0;
    }

    Product(String productName, double price) {
        this.productName = productName;
        this.price = price;
    }


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    void discount(double percentage) {
        price = (price - (price * percentage / 100));
    }


    void discount(int flatAmount) {
        price = price - flatAmount;
    }


    boolean isCheaperThan(Product otherProduct) {
        return this.price < otherProduct.price;
    }


    void display() {
        System.out.println("Store Name: " + storeName);
        System.out.println("Product: " + productName);
        System.out.println("Price: $" + price);
        System.out.println("--------------------------");
    }
}


public class GeneralStore{
    public static void main(String[] args) {


        Product.changeName("Tech World");

 
        Product laptop = new Product("Laptop", 1000);
        Product headphone = new Product("Headphone");


        headphone.setPrice(50);

        
        laptop.discount(10.0);     
        headphone.discount(5);

        System.out.println("is Headphone cheaper than Laptop? " + headphone.isCheaperThan(laptop));
        System.out.println();

   
        laptop.display();
        headphone.display();
    }
}
