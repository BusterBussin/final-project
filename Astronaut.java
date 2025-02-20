import java.sql.DriverManager;
import java.sql.ResultSet;
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
    int spaceshipID;

    public Astronaut(ResultSet rs) throws SQLException {
        name = rs.getString("AstroName");
        dob = rs.getString("dob");
        serial = rs.getInt("serial");
        number = rs.getLong("phone");
        address = rs.getString("address");
        email = rs.getString("email");
        rate = rs.getDouble("rate");
        formattedRate = String.format("%1$,10.2f", rate);
        realRate = "$" + formattedRate;
        weight = rs.getDouble("weight");
        kin = rs.getString("kin");
        status = rs.getBoolean("status");
        phoneNum = String.format("(%03d)%03d-%04d",
                number / 10000000,
                (number / 10000) % 1000,
                number % 10000);
        formattedWeight = String.format("%.2f lbs", weight);
        spaceshipID = rs.getInt("spacecraftID");
    }

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
        spaceshipID = 0;
        // String sqlThing = "SELECT * FROM Astronauts;";
        sqlInput = "INSERT INTO Astronauts(AstroName,dob,serial,phone,address,email,rate,weight,kin,status, spacecraftID) VALUES(?,?,?,?,?,?,?,?,?,?,?)";

        try (var conn = DriverManager.getConnection(url);
                var pstmt = conn.prepareStatement(sqlInput)) {
            pstmt.setString(2, name);
            pstmt.setString(3, dob);
            pstmt.setInt(4, serial);
            pstmt.setLong(5, number);
            pstmt.setString(6, address);
            pstmt.setString(7, email);
            pstmt.setDouble(8, rate);
            pstmt.setDouble(9, weight);
            pstmt.setString(10, kin);
            pstmt.setBoolean(11, status);
            pstmt.setInt(12, spaceshipID);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void setName(String name) {
        this.name = name;
        sqlInput = "INSERT INTO Astronauts(AstroName) VALUES(?)";
        try (var conn = DriverManager.getConnection(url);
                var pstmt = conn.prepareStatement(sqlInput)) {

            for (int i = 0; i < 3; i++) {
                pstmt.setString(2, name);
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void setDOB(String dob) {
        this.dob = dob;
        sqlInput = "INSERT INTO Astronauts(dob) VALUES(?)";
        try (var conn = DriverManager.getConnection(url);
                var pstmt = conn.prepareStatement(sqlInput)) {

            for (int i = 0; i < 3; i++) {
                pstmt.setString(3, dob);
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void setSerial(int serial) {
        this.serial = serial;
        sqlInput = "INSERT INTO Astronauts(serial) VALUES(?)";
        try (var conn = DriverManager.getConnection(url);
                var pstmt = conn.prepareStatement(sqlInput)) {

            for (int i = 0; i < 3; i++) {
                pstmt.setInt(4, serial);
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void setAddress(String address) {
        this.address = address;
        sqlInput = "INSERT INTO Astronauts(address) VALUES(?)";
        try (var conn = DriverManager.getConnection(url);
                var pstmt = conn.prepareStatement(sqlInput)) {

            for (int i = 0; i < 3; i++) {
                pstmt.setString(6, address);
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void setEmail(String email) {
        this.email = email;
        sqlInput = "INSERT INTO Astronauts(email) VALUES(?)";
        try (var conn = DriverManager.getConnection(url);
                var pstmt = conn.prepareStatement(sqlInput)) {

            for (int i = 0; i < 3; i++) {
                pstmt.setString(7, email);
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void setPhone(long number) {
        this.number = number;
        phoneNum = String.format("(%03d)%03d-%04d",
                number / 10000000,
                (number / 10000) % 1000,
                number % 10000);
        sqlInput = "INSERT INTO Astronauts(phone) VALUES(?)";
        try (var conn = DriverManager.getConnection(url);
                var pstmt = conn.prepareStatement(sqlInput)) {

            for (int i = 0; i < 3; i++) {
                pstmt.setString(5, phoneNum);
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void setRate(double rate) {
        this.rate = rate;
        formattedRate = String.format("%1$,10.2f", rate);
        realRate = "$" + formattedRate;
        sqlInput = "INSERT INTO Astronauts(rate) VALUES(?)";
        try (var conn = DriverManager.getConnection(url);
                var pstmt = conn.prepareStatement(sqlInput)) {

            for (int i = 0; i < 3; i++) {
                pstmt.setString(8, realRate);
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void setWeight(double weight) {
        this.weight = weight;
        formattedWeight = String.format("%.2f lbs", weight);
        sqlInput = "INSERT INTO Astronauts(weight) VALUES(?)";
        try (var conn = DriverManager.getConnection(url);
                var pstmt = conn.prepareStatement(sqlInput)) {

            for (int i = 0; i < 3; i++) {
                pstmt.setString(9, formattedWeight);
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void setKin(String kin) {
        this.kin = kin;
        sqlInput = "INSERT INTO Astronauts(kin) VALUES(?)";
        try (var conn = DriverManager.getConnection(url);
                var pstmt = conn.prepareStatement(sqlInput)) {

            for (int i = 0; i < 3; i++) {
                pstmt.setString(10, kin);
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void setStatus(boolean status) {
        this.status = status;
        sqlInput = "INSERT INTO Astronauts(status) VALUES(?)";
        try (var conn = DriverManager.getConnection(url);
                var pstmt = conn.prepareStatement(sqlInput)) {

            for (int i = 0; i < 3; i++) {
                pstmt.setBoolean(11, status);
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
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

    public void setID(int spaceshipID) {
        this.spaceshipID = spaceshipID;
        sqlInput = "INSERT INTO Astronauts(spacecraftID) VALUES(?)";
        try (var conn = DriverManager.getConnection(url);
                var pstmt = conn.prepareStatement(sqlInput)) {

            for (int i = 0; i < 3; i++) {
                pstmt.setInt(12, spaceshipID);
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public int getID() {
        return spaceshipID;
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
