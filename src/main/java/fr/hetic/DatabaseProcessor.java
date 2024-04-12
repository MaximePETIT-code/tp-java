package fr.hetic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

public class DatabaseProcessor {
    private static final String URL = "jdbc:postgresql://SG-hetic-mt4-java-5275-pgsql-master.servers.mongodirector.com:5432/TP";
    private static final String USER = "etudiant";
    private static final String PASSWORD = "MT4@hetic2324";
    private FileProcessor fileProcessor;

    public DatabaseProcessor(OperationFactory operationFactory) {
        this.fileProcessor = new FileProcessor(operationFactory);
    }

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void processDatabase() {
        try (Connection connection = connect();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT ligne.*, fichier.nom AS nom_fichier FROM ligne JOIN fichier ON ligne.fichier_id = fichier.id WHERE fichier.type = 'OP' ORDER BY ligne.position")) {

            String currentFile = null;
            PrintWriter writer = null;
            while (rs.next()) {
                String fileName = rs.getString("nom_fichier");
                if (!fileName.equals(currentFile)) {
                    if (writer != null) {
                        writer.close();
                    }
                    currentFile = fileName;
                    File file = new File(fileName.replace(".op", ".res"));
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    writer = new PrintWriter(new FileWriter(file, true));
                }

                int param1 = rs.getInt("param1");
                int param2 = rs.getInt("param2");
                String operator = rs.getString("operateur");
                String line = param1 + " " + param2 + " " + operator;
                fileProcessor.processLineAndWriteResult(line, writer);
            }
            if (writer != null) {
                writer.close();
            }
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }
    }
}


