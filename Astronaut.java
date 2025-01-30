public class Astronaut {
    String name;
    String dob;
    int serial;
    String address;
    String email;
    int number;
    double rate;
    double weight;
    String kin;
    boolean status;
    String phoneNum;

    public Astronaut(String name, String dob, int serial, String address, String email, int number, double rate,
            double weight, String kin, boolean status) {
        this.name = name;
        this.dob = dob;
        this.serial = serial;
        this.address = address;
        this.email = email;
        this.number = number;
        this.rate = rate;
        this.weight = weight;
        this.kin = kin;
        this.status = status;
        phoneNum = String.format("(%03d)%03d-%04d",
                number / 10000000,
                (number / 10000) % 1000,
                number % 10000);
    }

    public void setName(String name) {
        this.name = name;
    }
}
