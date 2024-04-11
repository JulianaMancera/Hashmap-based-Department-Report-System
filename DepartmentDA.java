import java.util.*;
import java.io.*;
import java.text.DecimalFormat;

public class DepartmentDA {

    public DepartmentDA() {
        loadDepartments();
    }

    private void loadDepartments() {
        try (Scanner depFile = new Scanner(new FileReader("dep.csv"))) {

            while (depFile.hasNextLine()) {
                String departmentLine = depFile.nextLine();
                String[] depEmpSplitData = departmentLine.split(",");

                Department department = new Department();
                department.setDepCode(depEmpSplitData[0].trim());
                department.setDepName(depEmpSplitData[1].trim());
                readDepEmp(department);
                printDepartment(department);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void readDepEmp(Department department) {
        try (Scanner depEmpInput = new Scanner(new FileReader("deptemp.csv"))) {
            Integer key = 0; 
            HashMap<String, Employee> employeeMap = new HashMap<>();
    
            while (depEmpInput.hasNextLine()) {
                String depEmpLineData = depEmpInput.nextLine();
                String[] depEmpSplitData = depEmpLineData.split(", ");
                EmployeeDA employeeDA = new EmployeeDA(depEmpSplitData[1].trim());
    
                if (department.getDepCode().equals(depEmpSplitData[0].trim())) {
                    Employee employee = employeeDA.getEmployee();
                    employee.setSalary(Double.parseDouble(depEmpSplitData[2]));
                    employeeMap.put(employee.getEmpNo() + key, employee);
                    key++;
             
                }
            }
    
            
            // Calculate total salary and update department's total salary
            double totalSalary = 0.0;
            for (Employee employee : employeeMap.values()) {
                totalSalary += employee.getSalary();
            }
            department.setDepTotalSalary(totalSalary);
            department.setEmployeeMap(employeeMap);
    
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    private void printDepartment(Department department) {
        DecimalFormat df = new DecimalFormat("#,###.00");
        System.out.println("Department Code: " + department.getDepCode());
        System.out.println("Department Name: " + department.getDepName());
        System.out.println("Department total Salary: " + df.format(department.getDepTotalSalary()));
        System.out.println("--------------------Details----------------------");
        System.out.println("EmpNo \t\tEmployee Name \t\tSalary");

        for (Map.Entry<String, Employee> entry : department.getEmployeeMap().entrySet()) {
            System.out.printf("%s \t\t%-20s %12s\n", entry.getKey(),
                    entry.getValue().getLastName() + ", " + entry.getValue().getFirstName(),
                    df.format(entry.getValue().getSalary()));
        }
        System.out.println();
    }
}
