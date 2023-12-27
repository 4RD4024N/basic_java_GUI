private void loadFriendsData() {
    try {
        File file = new File("C:\\Users\\arda0\\OneDrive\\Masaüstü\\java ödev\\name.csv"); // CSV dosyasının yolu
        Scanner scanner = new Scanner(file);
        // İlk satırı atla (başlıklar)
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] data = line.split(","); // Virgülle ayrılmış değerleri ayır
            if (data.length == 2) { // Her satırın tam olarak iki sütunu olduğundan emin ol
                model.addRow(new Object[]{data[0].trim(), data[1].trim()});
            }
        }
        scanner.close();
    } catch (FileNotFoundException e) {
        JOptionPane.showMessageDialog(null, "Dosya bulunamadı: " + e.getMessage());
        e.printStackTrace();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Bir hata oluştu: " + e.getMessage());
        e.printStackTrace();
    }
}
