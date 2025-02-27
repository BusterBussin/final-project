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
        sqlInput = "UPDATE Astronauts SET AstroName = ? WHERE ID = " + id + ";";
        try (var conn = DriverManager.getConnection(url);
                var pstmt = conn.prepareStatement(sqlInput)) {

                pstmt.setString(1, name);
                pstmt.executeUpdate();
            

        } catch (SQLException e) {
            // If an error occurs, display error.
            System.err.println(e.getMessage());
        }
    }

    // Setting date of birth
    public void setDOB(String dob) {
        this.dob = dob;
        // String for SQL
        sqlInput = "UPDATE Astronauts SET dob = ? WHERE ID = " + id + ";";
        try (var conn = DriverManager.getConnection(url);
                var pstmt = conn.prepareStatement(sqlInput)) {

                pstmt.setString(1, dob);
                pstmt.executeUpdate();
            

        } catch (SQLException e) {
            // If an error occurs, display error.
            System.err.println(e.getMessage());
        }
    }

    // Set serial
    public void setSerial(int serial) {
        this.serial = serial;
        // SQL string
        sqlInput = "UPDATE Astronauts SET serial = ? WHERE ID = " + id + ";";
        try (var conn = DriverManager.getConnection(url);
                var pstmt = conn.prepareStatement(sqlInput)) {

                pstmt.setInt(1, serial);
                pstmt.executeUpdate();
            

        } catch (SQLException e) {
            // If an error occurs, display error.
            System.err.println(e.getMessage());
        }
    }

    // Setting address
    public void setAddress(String address) {
        this.address = address;
        // You already know.
        sqlInput = "UPDATE Astronauts SET address = ? WHERE ID = " + id + ";";
        try (var conn = DriverManager.getConnection(url);
                var pstmt = conn.prepareStatement(sqlInput)) {

            pstmt.setString(1, address);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            // If an error occurs, display error.
            System.err.println(e.getMessage());
        }
    }

    // Setting email
    public void setEmail(String email) {
        this.email = email;
        // String for SQL
        sqlInput = "UPDATE Astronauts SET email = ? WHERE ID = " + id + ";";
        try (var conn = DriverManager.getConnection(url);
                var pstmt = conn.prepareStatement(sqlInput)) {

                pstmt.setString(1, email);
                pstmt.executeUpdate();
            

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
        sqlInput = "UPDATE Astronauts SET phone = ? WHERE ID = " + id + ";";
        try (var conn = DriverManager.getConnection(url);
                var pstmt = conn.prepareStatement(sqlInput)) {

                pstmt.setString(1, phoneNum);
                pstmt.executeUpdate();
            

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
        sqlInput = "UPDATE Astronauts SET rate = ? WHERE ID = " + id + ";";
        try (var conn = DriverManager.getConnection(url);
                var pstmt = conn.prepareStatement(sqlInput)) {

                pstmt.setDouble(1, rate);
                pstmt.executeUpdate();
            

        } catch (SQLException e) {
            // If an error occurs, display error.
            System.err.println(e.getMessage());
        }
    }

    // setting weight
    public void setWeight(double weight) {
        this.weight = weight;
        // format weight
        formattedWeight = String.format("%.2f lbs", weight);
        // SQL line
        sqlInput = "UPDATE Astronauts SET weight = ? WHERE ID = " + id + ";";
        try (var conn = DriverManager.getConnection(url);
                var pstmt = conn.prepareStatement(sqlInput)) {

            pstmt.setDouble(1, weight);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            // If an error occurs, display error.
            System.err.println(e.getMessage());
        }
    }

    // Setting next of kin
    public void setKin(String kin) {
        this.kin = kin;
        // SQL line
        sqlInput = "UPDATE Astronauts SET kin = ? WHERE ID = " + id + ";";
        try (var conn = DriverManager.getConnection(url);
                var pstmt = conn.prepareStatement(sqlInput)) {

                pstmt.setString(1, kin);
                pstmt.executeUpdate();
            

        } catch (SQLException e) {
            // If an error occurs, display error.
            System.err.println(e.getMessage());
        }
    }

    // Setting status
    public void setStatus(boolean status) {
        this.status = status;
        // SQL line
        sqlInput = "UPDATE Astronauts SET status = ? WHERE ID = " + id + ";";
        try (var conn = DriverManager.getConnection(url);
                var pstmt = conn.prepareStatement(sqlInput)) {

            for (int i = 0; i < 3; i++) {
                pstmt.setBoolean(1, status);
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            // If an error occurs, display error.
            System.err.println(e.getMessage());
        }
    }

    // Returns the name
    public String getName() {
        return name;
    }

    // Returns date of birth
    public String getDOB() {
        return dob;
    }

    // Returns serial
    public int getSerial() {
        return serial;
    }

    // Returns address
    public String getAddress() {
        return address;
    }

    // Returns email
    public String getEmail() {
        return email;
    }

    // Returns phone
    public String getPhone() {
        return phoneNum;
    }

    // Returns weight
    public String getWeight() {
        return formattedWeight;
    }

    // Returns rate
    public String getPayRate() {
        return formattedRate;
    }

    // Returns kin name
    public String getKin() {
        return kin;
    }

    // Returns status
    public String getStatus() {
        // If the astronaut is in space
        if (status) {
            return "Astronaut is currently in space!";
        }
        // If astronaut is not in space
        return "Astronaut is on Earth";
    }

    // Sets spacecraft id
    public void setID(int spaceshipID) {
        this.spaceshipID = spaceshipID;
        // SQL line
        sqlInput = "UPDATE Astronauts SET spacecraftID = ? WHERE ID = " + id + ";";
        try (var conn = DriverManager.getConnection(url);
                var pstmt = conn.prepareStatement(sqlInput)) {

                pstmt.setInt(1, spaceshipID);
                pstmt.executeUpdate();
            

        } catch (SQLException e) {
            // If an error occurs, display error.
            System.err.println(e.getMessage());
        }
    }

    // Returns ID
    public int getID() {
        return spaceshipID;
    }

    // Gets current ID
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

    // Returns all info
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

    // Deletes astronaut from SQL
    public void astroDeleter() {
        // Line used to delete
        sqlInput = "DELETE FROM Astronauts WHERE id = ?";
        try (var conn = DriverManager.getConnection(url);
                var pstmt = conn.prepareStatement(sqlInput)) {
            pstmt.setInt(1, id);

            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            // If an error occurs, display error.
            System.err.println(e.getMessage());
        }
    }
}
