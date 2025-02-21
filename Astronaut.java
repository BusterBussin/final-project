import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Astronaut {
    // SQL URL string
    String url = "jdbc:sqlite:spaceprogram.db";
    // SQL String to grab the ID
    String idgrabber = "SELECT * FROM Astronauts;";
    // Needed variables
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
    int id;
    // Constructor that takes everything from the tables
    public Astronaut(ResultSet rs) throws SQLException {
        name = rs.getString("AstroName");
        dob = rs.getString("dob");
        serial = rs.getInt("serial");
        number = rs.getLong("phone");
        address = rs.getString("address");
        email = rs.getString("email");
        rate = rs.getDouble("rate");
        // Format the weight
        formattedRate = String.format("%1$,10.2f", rate);
        realRate = "$" + formattedRate;
        weight = rs.getDouble("weight");
        kin = rs.getString("kin");
        status = rs.getBoolean("status");
        // Format the number
        phoneNum = String.format("(%03d)%03d-%04d",
                number / 10000000,
                (number / 10000) % 1000,
                number % 10000);
        // Format the weight
        formattedWeight = String.format("%.2f lbs", weight);
        spaceshipID = rs.getInt("spacecraftID");
        id = rs.getInt("id");
    }
    // Constructor for inputs given by user
    public Astronaut(String name, String dob, int serial, String address, String email, long number, double rate,
            double weight, String kin, boolean status) {
        this.name = name;
        this.dob = dob;
        this.serial = serial;
        this.address = address;
        this.email = email;
        this.number = number;
        this.rate = rate;
        // Format the weight
        formattedRate = String.format("%1$,10.2f", rate);
        // Display the rate as "$(rate)"
        realRate = "$" + formattedRate;
        this.weight = weight;
        // Format weight and display as "(weight) lbs"
        formattedWeight = String.format("%.2f lbs", weight);
        this.kin = kin;
        this.status = status;
        // Format number
        phoneNum = String.format("(%03d)%03d-%04d",
                number / 10000000,
                (number / 10000) % 1000,
                number % 10000);
        // Autoset the spaceship ID to 0
        spaceshipID = 0;
        // Inserting values into SQL
        sqlInput = "INSERT INTO Astronauts(AstroName,dob,serial,phone,address,email,rate,weight,kin,status, spacecraftID) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        try (var conn = DriverManager.getConnection(url);
                var pstmt = conn.prepareStatement(sqlInput)) {
            pstmt.setString(1, name);
            pstmt.setString(2, dob);
            pstmt.setInt(3, serial);
            pstmt.setLong(4, number);
            pstmt.setString(5, address);
            pstmt.setString(6, email);
            pstmt.setDouble(7, rate);
            pstmt.setDouble(8, weight);
            pstmt.setString(9, kin);
            pstmt.setBoolean(10, status);
            pstmt.setInt(11, spaceshipID);
            // Execute update
            pstmt.executeUpdate();

        } catch (SQLException e) {
            // If an error occurs, display error.
            System.err.println(e.getMessage());
        }
        // Grab the ID after entering data.
        idGrab();
    }
    // ID grabbing
    public void idGrab() {
        try (var conn = DriverManager.getConnection(url);
                var stmt = conn.createStatement();
                var rs = stmt.executeQuery(idgrabber)) {

            while (rs.next()) {
                // Set ID to what is on the table.
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            
            System.err.println(e.getMessage());
        }
    }
    // Setting name
    public void setName(String name) {
        this.name = name;
        // String for SQL
        sqlInput = "INSERT INTO Astronauts(AstroName) VALUES(?)";
        try (var conn = DriverManager.getConnection(url);
                var pstmt = conn.prepareStatement(sqlInput)) {

            for (int i = 0; i < 3; i++) {
                pstmt.setString(2, name);
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            // If an error occurs, display error.
            System.err.println(e.getMessage());
        }
    }
    // Setting date of birth
    public void setDOB(String dob) {
        this.dob = dob;
        // String for SQL
        sqlInput = "INSERT INTO Astronauts(dob) VALUES(?)";
        try (var conn = DriverManager.getConnection(url);
                var pstmt = conn.prepareStatement(sqlInput)) {

            for (int i = 0; i < 3; i++) {
                pstmt.setString(3, dob);
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            // If an error occurs, display error.
            System.err.println(e.getMessage());
        }
    }
    // Set serial
    public void setSerial(int serial) {
        this.serial = serial;
        // SQL string
        sqlInput = "INSERT INTO Astronauts(serial) VALUES(?)";
        try (var conn = DriverManager.getConnection(url);
                var pstmt = conn.prepareStatement(sqlInput)) {

            for (int i = 0; i < 3; i++) {
                pstmt.setInt(4, serial);
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            // If an error occurs, display error.
            System.err.println(e.getMessage());
        }
    }
    // Setting address
    public void setAddress(String address) {
        this.address = address;
        // You already know.
        sqlInput = "INSERT INTO Astronauts(address) VALUES(?)";
        try (var conn = DriverManager.getConnection(url);
                var pstmt = conn.prepareStatement(sqlInput)) {

            for (int i = 0; i < 3; i++) {
                pstmt.setString(6, address);
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            // If an error occurs, display error.
            System.err.println(e.getMessage());
        }
    }
    // Setting email
    public void setEmail(String email) {
        this.email = email;
        // String for SQL
        sqlInput = "INSERT INTO Astronauts(email) VALUES(?)";
        try (var conn = DriverManager.getConnection(url);
                var pstmt = conn.prepareStatement(sqlInput)) {

            for (int i = 0; i < 3; i++) {
                pstmt.setString(7, email);
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            // If an error occurs, display error.
            System.err.println(e.getMessage());
        }
    }
    // Setting phone number
    public void setPhone(long number) {
        this.number = number;
        // Format the number in the correct format
        phoneNum = String.format("(%03d)%03d-%04d",
                number / 10000000,
                (number / 10000) % 1000,
                number % 10000);
        // String for SQL
        sqlInput = "INSERT INTO Astronauts(phone) VALUES(?)";
        try (var conn = DriverManager.getConnection(url);
                var pstmt = conn.prepareStatement(sqlInput)) {

            for (int i = 0; i < 3; i++) {
                pstmt.setString(5, phoneNum);
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            // If an error occurs, display error.
            System.err.println(e.getMessage());
        }
    }

    public void setRate(double rate) {
        this.rate = rate;
        // Format rate
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
            // 
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

    public int SQLDelete() {
        try (var conn = DriverManager.getConnection(url);
                var stmt = conn.createStatement();
                var rs = stmt.executeQuery(idgrabber)) {

            while (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return id;
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

    public void astroDeleter() {
        sqlInput = "DELETE FROM Astronauts WHERE id = ?";
        try (var conn = DriverManager.getConnection(url);
             var pstmt = conn.prepareStatement(sqlInput)) {

            pstmt.setInt(1, id);

            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
