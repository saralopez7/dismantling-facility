package data;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import data.Database;

public class DatabaseHelper<T> {
	private Database<T> db;
	private Connection connection = null;
	
	public DatabaseHelper() throws SQLException, ClassNotFoundException {
		//Set actual username and password
    	this.db = new Database<T>("postgres", "postgres");
    	this.connection = db.openDatabase();
    }
	   
	public Connection getConnection() {
		return connection;
	}

	public String getChassisNumber(String chassisNumber) throws SQLException {
		String sql = "SELECT car.chassisNo FROM car WHERE car.chassisNo = '"
				+ chassisNumber + "';";
	    ArrayList<Object[]> result = db.query(sql);
	    String s = "";
	    s += result.get(0)[0];
	    
	    return s;
	}

	public String getModel(String chassisNumber) throws SQLException {
		String sql = "Select car.model FROM car WHERE car.chassisNo = "
	            + "'" + chassisNumber + "';";
	    ArrayList<Object[]> result = db.query(sql);
	    String s = "";
	    s += result.get(0)[0];
	    
	    return s;
	}
	   
	//Check if it works double not tried in the ATM project
	public double getWeight(String chassisNumber) throws SQLException {
		String sql = "Select car.weight FROM car WHERE car.chassisNo = "
				+ "'" + chassisNumber + "';";
		ArrayList<Object[]> result = db.query(sql);
		return (double) result.get(0)[0];
	}

	public void addCar(String chassisNumber, String model, double weight) throws SQLException {
		String sql = "INSERT INTO car(chassisNo, model, weight) VALUES ('"
	            + chassisNumber + "','" + model + "'," + weight + ")";
	    db.update(sql);
	}

	public void changeModel(String chassisNumber, String model) throws SQLException {
		String sql = "UPDATE car SET model = '" + model + "' WHERE car.chassisNo = '" + chassisNumber + "';";
	     db.update(sql);
	}
	   
	public void changeWeight(String chassisNumber, double weight) throws SQLException {
		String sql = "UPDATE car SET weight = " + weight + " WHERE car.chassisNo = '" + chassisNumber + "';";
	    db.update(sql);
	}
	   
	public void deleteCar(String chassisNo) throws SQLException {
		String sql = "DELETE FROM car WHERE chassisNo= '" + chassisNo + "'";
		db.update(sql);
	}

	private PreparedStatement prepare(Connection connection, String sql, Object[] parameters) throws SQLException {
		PreparedStatement stat = connection.prepareStatement(sql);
		for(int i = 0; i < parameters.length; i++) {
			stat.setObject(i + 1, parameters[i]);
		}
		
		return stat;
	}

	public T mapSingle(DataMapper<T> mapper, String sql, Object... parameters) throws RemoteException {
		try (Connection connection = db.openDatabase()) {
			PreparedStatement stat = prepare(connection, sql, parameters);
			ResultSet rs = stat.executeQuery();
			if(rs.next()) {
				return mapper.create(rs);
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new RemoteException(e.getMessage(), e);
		}
	}
	
	public List<T> map(DataMapper<T> mapper, String sql, Object... parameters) throws RemoteException {
		try (Connection connection = db.openDatabase()) {
			PreparedStatement stat = prepare(connection, sql, parameters);
			ResultSet rs = stat.executeQuery();
			LinkedList<T> allCars = new LinkedList<>();
			while(rs.next()) {
				allCars.add(mapper.create(rs));
			}
			return allCars;
		} catch (SQLException e) {
			throw new RemoteException(e.getMessage(), e);
		}
	}
	
}
