import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

interface Employee extends Serializable {

    int getEmployeeId();

    String getName();

    String getDepartment();
}

class HR implements Employee {
    private final int employeeId;
    private final String name;

    HR(int employeeId, String name) {
        this.employeeId = employeeId;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getEmployeeId() {
        return employeeId;
    }

    @Override
    public String getDepartment() {
        return "Human Resource";
    }
}

class Developer implements Employee {
    private final int employeeId;
    private final String name;

    Developer(int employeeId, String name) {
        this.employeeId = employeeId;
        this.name = name;
    }

    @Override
    public int getEmployeeId() {
        return employeeId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDepartment() {
        return "Technology";
    }
}

public class LabWeek3 {

    public static void main(String[] args) {

        String fileName = "Employee.ser";

        List<Object> objects = Arrays.asList(
                new Developer(1, "Developer 1"),
                new Developer(2, "Developer 2"),
                new HR(3, "HR Employee 1"),
                new HR(4, "HR Employee 2")
        );

        writeObjectsData(objects, fileName);
        System.out.println("-------------------------------------");

        List<Employee> employees = readEmployeesData(fileName);
        if (!employees.isEmpty()) {
            printEmployees(employees);
        } else {
            System.out.println("Unable to read employees data");
        }
    }


    private static void printEmployees(List<Employee> employees) {
        employees.stream()
                .map(
                        each -> "Department = "
                                .concat(each.getDepartment())
                                .concat(" | EmployeeId = ")
                                .concat(each.getEmployeeId() + "")
                                .concat(" | Name = ")
                                .concat(each.getName())
                                .concat("------------------------")
                )
                .forEach(System.out::println);
    }

    private static void writeObjectsData(List<Object> objects, String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            for (Object obj : objects) {
                oos.writeObject(obj);
            }
            System.out.println("Data has been written to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Employee> readEmployeesData(String fileName) {
        List<Employee> employees = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            while (true) {
                Object obj = ois.readObject();
                if (obj instanceof Employee) {
                    employees.add((Employee) obj);
                }
            }
        } catch (EOFException e) {

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return employees;
    }
}
