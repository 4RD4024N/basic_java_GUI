public class Job {
    private String companyName;
    private int salary;

    public Job(String companyName, int salary) {
        this.companyName = companyName;
        this.salary = salary;
    }

    public String getCompanyName() {
        return companyName;
    }

    public int getSalary() {
        return salary;
    }
}
