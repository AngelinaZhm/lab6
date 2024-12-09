import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.List;


public class Main {
    public static void main(String[] args) throws IOException {
        Path directoryPath = Path.of("Директорія користувача");
        if (!Files.exists(directoryPath)) {
            Files.createDirectory(directoryPath);
            System.out.println("Директорію створено : " + directoryPath.toAbsolutePath());
        }

        try {
            Path userFilePath = directoryPath.resolve("user.txt");
            if (!Files.exists(userFilePath)) {
                Files.createFile(userFilePath);
                System.out.println("Файл створено : " + userFilePath.toAbsolutePath());
            }

            List<String> users = List.of(
                    "Ім'я : Олег, Вік : 22, Стать : Чоловік.",
                    "Ім'я : Руслан, Вік : 32, Стать : Чоловік.",
                    "Ім'я : Тетяна, Вік : 28, Стать : Жінка.",
                    "студент виконує лабораторну роботу",
                    "студент йде на тренування"
            );
            Files.write(userFilePath, users);
            System.out.println("Дані записано у файл.");

            long lineCount = Files.lines(userFilePath).count();
            System.out.println("Кількість рядків у файлі : " + lineCount);

            System.out.println("Введіть шлях для нового файлу : ");
            InputStream stream = System.in;
            InputStreamReader reader = new InputStreamReader(stream);
            BufferedReader buff = new BufferedReader(reader);
            String line = buff.readLine();

            if (line != null && !line.isEmpty()) {
                Path linePath = Path.of(line);
                Files.copy(userFilePath, linePath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Дані скопійовано у файл: " + linePath.toAbsolutePath());
            } else {
                System.out.println("Невірний шлях для файлу.");
            }
            System.out.println("Виконується заміна слова 'студент' на 'курсант'...");
            String content = Files.readString(userFilePath);
            String updatedContent = content.replaceAll("(?i)студент", "курсант");
            Files.writeString(userFilePath, updatedContent, StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println("Слово 'студент' успішно замінено на 'курсант' у файлі: " + userFilePath.toAbsolutePath());
        } catch (IOException e) {
            System.err.println("Сталася помилка : " + e.getMessage());
        }

    }
}
