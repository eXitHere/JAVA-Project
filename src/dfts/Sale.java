package dfts;

public class Sale {
    private String phone;
    private String start;
    private String stop;
    private int    count;
    private String date;
    private double price;

    public Sale(String phone, String start, String stop, int count, String date, double price) {
        this.phone = phone;
        this.start = start;
        this.stop = stop;
        this.count = count;
        this.date = date;
        this.price = price;
    }

    public String getPhone() {
        return phone;
    }

    public String getStart() {
        return start;
    }

    public String getStop() {
        return stop;
    }

    public int getCount() {
        return count;
    }

    public String getDate() {
        return date;
    }

    public double getPrice() {
        return price;
    }

    
    
    @Override
    public String toString() {
        return "Sale{" + "phone=" + phone + ", start=" + start + ", stop=" + stop + ", count=" + count + ", date=" + date + ", price=" + price + '}';
    }
}
