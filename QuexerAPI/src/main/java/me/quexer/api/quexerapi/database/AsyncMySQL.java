package me.quexer.api.quexerapi.database;

import me.quexer.api.quexerapi.QuexerAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.sql.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class AsyncMySQL {

    private ExecutorService executor;
    private MySQL sql;
    private Plugin plugin;
    private String host;
    private String user;
    private String passwort;
    private String database;
    private int port;



    public AsyncMySQL() {
        try {
            sql = new MySQL("localhost", 3306, "root", "", "elija.de");
            executor = Executors.newCachedThreadPool();
            plugin = QuexerAPI.getInstance();
            System.out.println("Connected MySQL");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(PreparedStatement statement) {
        executor.execute(() -> sql.queryUpdate(statement));
    }
    public void update(String statement) {
        executor.execute(() -> sql.queryUpdate(statement));
    }

    public void query(PreparedStatement statement, Consumer<ResultSet> consumer) {
        executor.execute(() -> {
            ResultSet result = sql.query(statement);
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> consumer.accept(result));
        });
    }

    public void query(String statement, Consumer<ResultSet> consumer) {
        executor.execute(() -> {
            ResultSet result = sql.query(statement);
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> consumer.accept(result));
        });
    }

    public PreparedStatement prepare(String query) {
        try {
            return sql.getConnection().prepareStatement(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public MySQL getMySQL() {
        return sql;
    }

    public static class MySQL {

        private String host, user, password, database;
        private int port;

        private Connection conn;

        public MySQL(String host, int port, String user, String password, String database) throws Exception {
            this.host = host;
            this.port = port;
            this.user = user;
            this.password = password;
            this.database = database;

            this.openConnection();
        }

        public void queryUpdate(String query) {
            checkConnection();
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                queryUpdate(statement);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void queryUpdate(PreparedStatement statement) {
            checkConnection();
            try {
                statement.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    statement.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public ResultSet query(String query) {
            checkConnection();
            try {
                return query(conn.prepareStatement(query));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        public ResultSet query(PreparedStatement statement) {
            checkConnection();
            try {
                return statement.executeQuery();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        public Connection getConnection() {
            return this.conn;
        }

        private void checkConnection() {
            try {
                if (this.conn == null || !this.conn.isValid(10) || this.conn.isClosed()) openConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public Connection openConnection() throws Exception {
            Class.forName("com.mysql.jdbc.Driver");
            return this.conn = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database+"?autoReconnect=true", this.user, this.password);
        }

        public void closeConnection() {
            try {
                this.conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                this.conn = null;
            }
        }
    }
}
