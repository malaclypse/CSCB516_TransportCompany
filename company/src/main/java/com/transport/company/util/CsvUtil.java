package com.transport.company.util;


import com.transport.company.entity.Client;
import com.transport.company.entity.Driver;
import com.transport.company.entity.Freight;
import com.transport.company.entity.Vehicle;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class CsvUtil {

    public static void downloadDriverCsv(PrintWriter writer, List<Driver> drivers)  {
        writer.write("Driver ID, First Name, Last Name, Email, Yearly Salary, Qualifications \n");
        for (Driver driver : drivers) {
            StringBuilder row = new StringBuilder();
            row.append(driver.getId()
            + "," + driver.getFirstName()
            + "," + driver.getLastName()
            + "," + driver.getEmailId()
            + "," +driver.getYearlySalary()
            + "," +driver.getDriverQualifications());

            row.append("\n");
            writer.write(row.toString());
        }
        writer.write("Total drivers: " +","+ (long) drivers.size());
    }

    public static void downloadVehiclesCsv(PrintWriter writer, List<Vehicle> vehicles)
          {
        writer.write("Vehicle ID, Brand, VehicleType \n");
        for (Vehicle vehicle : vehicles) {
            StringBuilder row = new StringBuilder();
            row.append(vehicle.getId()
                    + "," + vehicle.getBrand()
                    + "," + vehicle.getVehicleType());

            row.append("\n");
            writer.write(row.toString());
        }
        writer.write("Total Vehicles: " +","+ (long) vehicles.size());
    }

    public static void downloadClientsCsv(PrintWriter writer, List<Client> clients)
         {
        writer.write("Client ID, Client Type, Client Name \n");
        for (Client client : clients) {
            StringBuilder row = new StringBuilder();
            row.append(client.getId()
                    + "," + client.getClientType()
                    + "," + client.getName());
            row.append("\n");
            writer.write(row.toString());
        }
        writer.write("Total clients: " +","+ (long) clients.size());
    }

    public static void downloadFreightCsv(PrintWriter writer, List<Freight> freights)
          {
              writer.write("Freight ID, Driver ID, Client ID, Type, Start Location, Destination, Price, Paid, Delivered, Date Delivered \n");
              for (Freight freight : freights) {
                  StringBuilder row = new StringBuilder();
                  row.append(freight.getId()
                          + "," + freight.getDriver().getId()
                          + "," + freight.getClient().getId()
                          + "," + freight.getType()
                          + "," + freight.getStartLocation()
                          + "," + freight.getDestination()
                          + "," + freight.getPrice()
                          + "," + freight.isPaid()
                          + "," + freight.isDelivered()
                          + "," + freight.getDateDelivered()
                  );
                  row.append("\n");
                  writer.write(row.toString());
              }
        writer.write("Total freights: " +","+ (long) freights.size());
    }
}