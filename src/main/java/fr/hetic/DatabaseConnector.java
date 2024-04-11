package fr.hetic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class DatabaseConnector {
    private static final String URL = "jdbc:postgresql://SG-hetic-mt4-java-5275-pgsql-master.servers.mongodirector.com:5432/TP";
    private static final String USER = "etudiant";
    private static final String PASSWORD = "MT4@hetic2324";

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void processDatabase() {
        try (Connection connection = connect();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT ligne.*, fichier.nom AS nom_fichier FROM ligne JOIN fichier ON ligne.fichier_id = fichier.id WHERE fichier.type = 'OP' ORDER BY ligne.position")) {

            OperationFactory operationFactory = new OperationFactory();
            String currentFile = null;
            BufferedWriter writer = null;
            while (rs.next()) {
                String fileName = rs.getString("nom_fichier");
                if (!fileName.equals(currentFile)) {
                    if (writer != null) {
                        writer.close();
                    }
                    currentFile = fileName;
                    writer = new BufferedWriter(new FileWriter(fileName.replace(".op", ".res"), true));
                }

                int param1 = rs.getInt("param1");
                int param2 = rs.getInt("param2");
                String operator = rs.getString("operateur");
                if (operator != null) {
                    Operation operation = operationFactory.getOperation(operator);
                    if (operation != null) {
                        int result = operation.execute(param1, param2);
                        writer.write(String.valueOf(result));
                        writer.newLine();
                    } else {
                        writer.write("ERROR");
                        writer.newLine();
                    }
                } else {
                    writer.write("ERROR");
                    writer.newLine();
                }
            }
            if (writer != null) {
                writer.close();
            }
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }
    }
}