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
    boolean status = false;
    String phoneNum;
    String formattedRate;
    String formattedWeight;

    public Astronaut(String name, String dob, int serial, String address, String email, int number, double rate,
            double weight, String kin, boolean status) {
        this.name = name;
        this.dob = dob;
        this.serial = serial;
        this.address = address;
        this.email = email;
        this.number = number;
        this.rate = rate;
        formattedRate = String.format("%1$,10.2f");
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

    public void setDOB(String dob) {
        this.dob = dob;
    }

    public void setSerial (int serial) {
        this.serial = serial;
    }

    public void setAddress (String address) {
        this.address = address;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public void setPhone (int number) {
        this.number = number;
        phoneNum = String.format("(%03d)%03d-%04d",
                number / 10000000,
                (number / 10000) % 1000,
                number % 10000);
    }

    public void setRate (double rate) {
        this.rate = rate;
        formattedRate = String.format("$" + "%1$,10.2f");
    }

    public void setWeight (double weight) {
        this.weight = weight;
        formattedWeight = String.format("%.2f lbs");
    }

    public void setKin (String kin) {
        this.kin = kin;
    }

    public void setStatus (boolean status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getDOB() {
        return dob;
    }

    public int getSerial() {
        return serial;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phoneNum;
    }

    public String getWeight() {
        return formattedWeight;
    }

    public String getPayRate() {
        return formattedRate;
    }

    public String getKin() {
        return kin;
    }

    public String getStatus() {
        if (status) {
            return "Astronaut is currently in space!";
        }
        return "Astronaut is on Earth";
    }
}
