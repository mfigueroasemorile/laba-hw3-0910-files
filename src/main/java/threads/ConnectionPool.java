package threads;

import java.io.File;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ConnectionPool {

    private static ConnectionPool instance = null;
    private List<File> connectionPool;
    private List<File> connectionInUse;

    private ConnectionPool(String[] filePath, int maxSize){
        this.connectionPool = new CopyOnWriteArrayList<>();
        this.connectionInUse = new CopyOnWriteArrayList<>();

        for (String path : filePath) {
            if (connectionPool.size() < maxSize) {
                connectionPool.add(new File(path));
            }
        }
    }

    public static ConnectionPool getInstance(String[] filePaths, int maxSize) {
        if (instance == null) {
            instance = new ConnectionPool(filePaths, maxSize);
        }
        return instance;
    }

    public File getConnection() {
        if (!connectionPool.isEmpty()) {
            File connection = connectionPool.remove(0);
            connectionInUse.add(connection);
            System.out.println("Connections availables : " + connectionPool.size() + ", connections in use: " + connectionInUse.size());
            return connection;
        } else {
            System.out.println("Connection list is empty");
            return null;
        }
    }

    public synchronized void getConnectionBack(File connection) {
        if (connectionInUse.remove(connection)) {
            connectionPool.add(connection);
            System.out.println("Connection in use is back to the connecitonPool list");
            System.out.println("Connections available : " + connectionPool.size() + ", connections in use: " + connectionInUse.size());
        } else {
            System.out.println("This connection was not in use");
        }
    }
}
