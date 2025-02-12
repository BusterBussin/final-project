import java.sql.DriverManager;
import java.sql.SQLException;

public class Astronaut {
    String url = "jdbc:sqlite:spaceprogram.db";
    String name;
    String dob;
    int serial;
    String address;
    String email;
    long number;
    double rate;
    double weight;
    String kin;
    boolean status = false;
    String phoneNum;
    String formattedRate;
    String formattedWeight;
    String realRate;
    String sqlInput;

    public Astronaut(String name, String dob, int serial, String address, String email, long number, double rate,
            double weight, String kin, boolean status) {
        this.name = name;
        this.dob = dob;
        this.serial = serial;
        this.address = address;
        this.email = email;
        this.number = number;
        this.rate = rate;
        formattedRate = String.format("%1$,10.2f", rate);
        realRate = "$" + formattedRate;
        this.weight = weight;
        formattedWeight = String.format("%.2f lbs", weight);
        this.kin = kin;
        this.status = status;
        phoneNum = String.format("(%03d)%03d-%04d",
                number / 10000000,
                (number / 10000) % 1000,
                number % 10000);
        String sqlThing = "SELECT * FROM Astronauts;";
        sqlInput = "INSERT INTO Astronauts(name,dob,serial,phone,address,email,rate,weight,kin,status) VALUES(?,?,?,?,?,?,?,?,?,?)";

         try (var conn = DriverManager.getConnection(url);
             var pstmt = conn.prepareStatement(sqlInput)) {
                pstmt.setString(1, name);
                pstmt.setString(2, dob);
                pstmt.setInt(3, serial);
                pstmt.setString(4, phoneNum);
                pstmt.setString(5, address);
                pstmt.setString(6, email);
                pstmt.setDouble(7, rate);
                pstmt.setDouble(8, weight);
                pstmt.setString(9, kin);
                pstmt.setBoolean(10, status);
                pstmt.executeUpdate();
            

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
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

    public void setPhone (long number) {
        this.number = number;
        phoneNum = String.format("(%03d)%03d-%04d",
                number / 10000000,
                (number / 10000) % 1000,
                number % 10000);
    }

    public void setRate (double rate) {
        this.rate = rate;
        formattedRate = String.format("%1$,10.2f", rate);
        realRate = "$" + formattedRate;
    }

    public void setWeight (double weight) {
        this.weight = weight;
        formattedWeight = String.format("%.2f lbs", weight);
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

    public String toString() {
        return "Name: " + name + 
               "\nDOB: " + dob +
               "\nSerial: " + serial +
               "\nAddress: " + address +
               "\nEmail: " + email +
               "\nPhone: " + phoneNum +
               "\nPay Rate: " + realRate +
               "\nWeight: " + formattedWeight +
               "\nNext of Kin: " + kin +
               "\nStatus: " + getStatus();
    }
}
