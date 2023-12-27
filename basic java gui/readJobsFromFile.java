private static List<Job> readJobsFromFile(String filePath) {
    List<Job> jobs = new ArrayList<>();
    BufferedReader reader = null;

    try {
        reader = new BufferedReader(new FileReader(filePath));
        String line;

        // İlk satır başlık satırı olduğu için atlanıyor
        reader.readLine();

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(","); // CSV dosyası virgülle ayrılmış varsayılıyor
            if (parts.length >= 2) {
                String cmpname = parts[0];
                int salary = Integer.parseInt(parts[1]);
                jobs.add(new Job(cmpname, salary));
            }
        }
    } catch (FileNotFoundException e) {
        System.out.println("Dosya bulunamadı: " + e.getMessage());
    } catch (IOException e) {
        System.out.println("Dosya okunurken hata oluştu: " + e.getMessage());
    } finally {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                System.out.println("Dosya kapatılırken hata oluştu: " + e.getMessage());
            }
        }
    }

    return jobs;
}
