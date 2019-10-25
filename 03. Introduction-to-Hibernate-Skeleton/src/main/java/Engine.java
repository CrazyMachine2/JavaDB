import entities.Address;
import entities.Employee;
import entities.Project;
import entities.Town;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Engine implements Runnable {
    private final EntityManager entityManager;
    private Scanner scanner;

    public Engine(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void run() {
        this.removeObjects();
//        this.containsEmployee();
//        this.employeesWithSalaryOver();
//        this.employeesFromDepartment();
//        this.addNewAddressAndUpdateEmployee();
//        this.addressesWithEmployeeCount();
//        this.getEmployeeWithProject();
//        this.findLatest10Projects();
//        this.increaseSalaries();
//        this.findEmployeesByFirstName();
        this.employeesMaximumSalaries();
    }

    /*
     *Problem 2
     * */
    private void removeObjects() {
        this.entityManager.getTransaction().begin();

        this.entityManager
                .createQuery("FROM Town WHERE LENGTH(name) > 5", Town.class)
                .getResultStream()
                .forEach(t -> {
                    t.setName(t.getName().toLowerCase());
                    this.entityManager.persist(t);
                });

//            for (Town town : towns) {
//                town.setName(town.getName().toUpperCase());
//                this.entityManager.persist(town);
//            }

        this.entityManager.getTransaction().commit();
    }

    /*
     * Problem 3
     * */
    private void containsEmployee() {
        String name = this.scanner.nextLine();
        this.entityManager.getTransaction().begin();

        try {
            Employee employee = this.entityManager
                    .createQuery("FROM Employee WHERE CONCAT(firstName,' ',lastName) = :name", Employee.class)
                    .setParameter("name", name)
                    .getSingleResult();
            System.out.println("Yes");
        } catch (Exception e) {
            System.out.println("No");
        }


        this.entityManager.getTransaction().commit();
    }

    /*
     * Problem 4
     * */
    private void employeesWithSalaryOver() {
        this.entityManager.getTransaction().begin();
        double dSalary = Double.parseDouble(this.scanner.nextLine());
        BigDecimal salary = BigDecimal.valueOf(dSalary);

        this.entityManager
                .createQuery("FROM Employee WHERE salary > :salary", Employee.class)
                .setParameter("salary", salary)
                .getResultStream()
                .forEach(e -> {
                    System.out.println(e.getFirstName());
                });
        this.entityManager.getTransaction().commit();
    }


    /*
     * Problem 5
     * */
    private void employeesFromDepartment() {
        this.entityManager.getTransaction().begin();

        List<Employee> employees = this.entityManager
                .createQuery("FROM Employee AS e WHERE e.department.name = 'Research and Development' ORDER BY e.salary"
                        , Employee.class)
                .getResultList();

        for (Employee e : employees) {
            System.out.printf("%s %s %s - $%.2f%n",
                    e.getFirstName(),
                    e.getLastName(),
                    e.getDepartment().getName(), e.getSalary());
        }
        /*Another method for solving the problem*/
//        @SuppressWarnings("unchecked")
//        List<Object[]> resultList = this.entityManager
//                .createNativeQuery("SELECT e.first_name, e.last_name, d.name, e.salary\n" +
//                        "FROM employees e\n" +
//                        "INNER JOIN departments d\n" +
//                        "ON e.department_id = d.department_id\n" +
//                        "WHERE d.name = \"Research and Development\"\n" +
//                        "ORDER BY e.salary,e.employee_id;")
//                .getResultList();
//
//        for (Object[] o : resultList) {
//            String firstName = (String) o[0];
//            String lastName = (String) o[1];
//            String departmentName = (String) o[2];
//            BigDecimal salary = (BigDecimal) o[3];
//
//            System.out.printf("%s %s %s - $%.2f%n", o[0], o[1], o[2], o[3]);
//        }

        this.entityManager.getTransaction().commit();
    }


    /*
     * Problem 6
     * */
    private void addNewAddressAndUpdateEmployee() {
        this.entityManager.getTransaction().begin();

        Address address = new Address();
        address.setText("Vitoshka 15");
        this.entityManager.persist(address);

        String lastName = this.scanner.nextLine();

        try {
            Employee employee = this.entityManager
                    .createQuery("FROM Employee WHERE lastName = :name", Employee.class)
                    .setParameter("name", lastName)
                    .getSingleResult();

            employee.setAddress(address);
            this.entityManager.persist(employee);
        } catch (Exception e) {
            System.out.println("Exception");
        }
        this.entityManager.getTransaction().commit();
    }

    /*
     * Problem 7
     * */
    private void addressesWithEmployeeCount() {
        this.entityManager.getTransaction().begin();

        @SuppressWarnings("unchecked")
        List<Object[]> resultList = this.entityManager
                .createNativeQuery("SELECT a.address_text,t.name,COUNT(*) as counts\n" +
                        "FROM employees e\n" +
                        "JOIN addresses a ON e.address_id = a.address_id\n" +
                        "JOIN towns t ON a.town_id = t.town_id\n" +
                        "GROUP BY e.address_id\n" +
                        "ORDER BY counts DESC,a.town_id\n" +
                        "LIMIT 10;")
                .getResultList();

        for (Object[] o : resultList) {
            System.out.printf("%s, %s - %s employees%n", o[0], o[1], o[2]);
        }

        this.entityManager.getTransaction().commit();
    }

    /*
     * Problem 8
     * */
    private void getEmployeeWithProject() {
        this.entityManager.getTransaction().begin();

        int id = Integer.parseInt(this.scanner.nextLine());

        try {
            Employee employee = this.entityManager
                    .createQuery("FROM Employee e WHERE e.projects.size != 0 AND e.id = :id", Employee.class)
                    .setParameter("id", id)
                    .getSingleResult();

            System.out.printf("%s %s - %s%n", employee.getFirstName()
                    , employee.getLastName()
                    , employee.getJobTitle());

            employee.getProjects()
                    .stream()
                    .sorted(Comparator.comparing(Project::getName))
                    .forEach(p -> {
                        System.out.printf("      %s%n", p.getName());
                    });

            this.entityManager.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("The employee has no projects");
        }
    }

    /*
     * Problem 9
     * */
    private void findLatest10Projects() {
        this.entityManager.getTransaction().begin();

        List<Project> projects = this.entityManager
                .createQuery("FROM Project p ORDER BY p.startDate DESC", Project.class)
                .setMaxResults(10)
                .getResultList();

        projects.stream()
                .sorted(Comparator.comparing(Project::getName))
                .forEach(p -> {
                    System.out.printf("Project name: %s%n" +
                                    "   Project Description: %s%n" +
                                    "   Project Start Date: %s%n" +
                                    "   Project End Date: %s%n",
                            p.getName(), p.getDescription(), p.getStartDate(), p.getEndDate());
                });

        this.entityManager.getTransaction().commit();
    }

    /*
     * Problem 10
     * */

    private void increaseSalaries() {
        this.entityManager.getTransaction().begin();

         List<Employee> employees = this.entityManager
                .createQuery("FROM Employee e WHERE e.department.name IN ('Engineering','Tool', 'Design','Marketing','Information Services')", Employee.class)
                .getResultList();

        for (Employee e : employees) {
            BigDecimal bonus = new BigDecimal(0.12);
            e.setSalary(e.getSalary().add(e.getSalary().multiply(bonus)));
        }
        this.entityManager.flush();

       this.entityManager
               .createQuery("FROM Employee e WHERE e.department.name IN ('Engineering','Tool', 'Design','Marketing','Information Services')", Employee.class)
                .getResultStream()
                .forEach(e -> {
                    System.out.printf("%s %s ($%.2f)%n",e.getFirstName(),e.getLastName(),e.getSalary());
                });

        this.entityManager.getTransaction().commit();
    }

    private void findEmployeesByFirstName(){
        this.entityManager.getTransaction().begin();

        String pattern = this.scanner.nextLine() + "%";

        this.entityManager
                .createQuery("FROM Employee WHERE firstName LIKE :pattern",Employee.class)
                .setParameter("pattern",pattern)
                .getResultStream()
                .forEach(e -> {
                    System.out.printf("%s %s - %s - ($%.2f)%n",
                            e.getFirstName(),e.getLastName(),e.getJobTitle(),e.getSalary());
                });

        this.entityManager.getTransaction().commit();
    }

    private void employeesMaximumSalaries(){
        this.entityManager.getTransaction().begin();

        List<Object[]> salaries = this.entityManager
                .createNativeQuery("SELECT d.name, MAX(e.salary) as maxs\n" +
                        "FROM employees e\n" +
                        "JOIN departments d ON d.department_id = e. department_id\n" +
                        "GROUP BY e.department_id\n" +
                        "HAVING maxs NOT BETWEEN 30000 AND 70000;")
                .getResultList();

        for (Object[] s: salaries) {
            System.out.printf("%s - %.2f%n",
                    s[0],s[1]);
        }


        this.entityManager.getTransaction().commit();
    }
}




























