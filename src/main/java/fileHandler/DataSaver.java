package fileHandler;

import data.Group;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.util.List;

public class DataSaver {

    public void saveDataToCSVs(List<Group> groups) {
        for(Group group : groups) {
            String path = group.getName().replace(" ", "");
            path = "group" + path + ".csv";
            URL url = getClass().getClassLoader().getResource("files/" + path);
            if (url == null) {
                throw new IllegalStateException("Could not find csv file for " + group.getName() + "!");
            }
            File file = new File(url.getFile());
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                bw.write("name,tasks\n");
                bw.write(group.getSavableGroupString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
