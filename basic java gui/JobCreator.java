import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class JobCreator {
    public static List<Job> readJobsFromFile(String filePath) {
        List<Job> jobs = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Başlık satırını atla

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String companyName = parts[0].trim();
                    int salary = Integer.parseInt(parts[1].trim());
                    jobs.add(new Job(companyName, salary));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jobs;
    }
}
