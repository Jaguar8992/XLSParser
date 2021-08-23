import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {

        String path = "data/To_load";
        String loadedPath = "data/Loaded";
        XLSParser parser = new XLSParser();

        List<File> files = Files.walk(Paths.get(path)).filter(Files::isRegularFile).map(Path::toFile)
                .collect(Collectors.toList());

        File dir = new File(loadedPath);
        if (!dir.exists()) {
            dir.mkdir();
        }

        for (File file : files) {

            if (file.exists()) {
                try {
                    parser.parse(file);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Files.copy(Paths.get(file.getPath()), Paths.get(loadedPath + "/" + file.getName()), StandardCopyOption.REPLACE_EXISTING);
                Files.delete(Paths.get(file.getPath()));
            }

        }
    }

}
