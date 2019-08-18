package fileHandler;

import data.Group;
import data.Person;
import data.Task;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.util.*;

public class DataGetter {
    private List<Group> groups;
    private Map<String, Person> people;

    public DataGetter() {
        this.groups = new ArrayList<>();
        this.people = new HashMap<>();
    }

    public Collection<Person> getAllPeople() {
        return this.people.values();
    }

    public List<Group> getDataFromFile() throws IOException {
        URL path = getClass().getClassLoader().getResource("files/data.json");
        if (path == null) {
            throw new IllegalStateException("Could not find file data.json!");
        }
        File file = new File(path.getFile());
        String jsonString = getJsonString(file);
        JSONObject json = new JSONObject(jsonString);
        JSONArray groups = (JSONArray) json.get("Groups");

        for(int i = 0; i < groups.length(); i++) {
            JSONObject g = (JSONObject) groups.get(i);
            String name = (String) g.get("Name");
            Group group = new Group(name);
            List<Person> people = getCorrectOrder(getListOfPeople(((JSONArray) g.get("People")).toList()), name);
            List<Task> tasks = getListOfTasks(((JSONArray) g.get("Tasks")).toList());
            group.addPeople(people);
            group.addTasks(tasks);
            this.groups.add(group);
        }

        return this.groups;
    }

    private String getJsonString(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        StringBuilder jsonString = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            jsonString.append(line);
        }
        br.close();
        return jsonString.toString();
    }

    private List<Person> getListOfPeople(List<Object> p) {
        List<Person> people = new ArrayList<>();
        p.forEach((o) -> {
            Map<String, String> jp = (Map<String, String>) o;
            String name = jp.get("Name");
            Person person = this.people.get(name);
            if(person == null) {
                String phoneNumber = jp.get("PhoneNumber");
                person = new Person(name, phoneNumber);
                this.people.put(name, person);
            }
            people.add(person);
        });
        return people;
    }

    private List<Person> getCorrectOrder(List<Person> people, String groupName) {
        String path = groupName.replace(" ", "");
        path = "group" + path + ".csv";
        URL url = getClass().getClassLoader().getResource("files/" + path);
        if (url == null) {
            throw new IllegalStateException("Could not find csv file for " + groupName + "!");
        }
        File file = new File(url.getFile());
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean first = true;
            List<String> names = new ArrayList<>();
            while((line = br.readLine()) != null) {
                if(first) {
                    first = false;
                }
                else {
                    String name = line.split(",")[0];
                    names.add(name);
                }
            }

            List<Person> correctOrder = new ArrayList<>();
            for(String name : names){
                Person person = new Person(name, "0000000000");
                int index = people.indexOf(person);
                if(index == -1) {
                    throw new IllegalStateException("csv file for " + groupName + " contains a name for a person not in the data.json file");
                }
                correctOrder.add(people.get(index));
            }
            br.close();

            return correctOrder;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            //failed to get correct order from file, to just start from scratch.
            return people;
        } catch (IOException e) {
            e.printStackTrace();
            //failed to get correct order from file, to just start from scratch.
            return people;
        }
    }

    private List<Task> getListOfTasks(List<Object> p) {
        List<Task> tasks = new ArrayList<>();
        p.forEach((o) -> {
            String task = (String) o;
            tasks.add(new Task(task));
        });
        return tasks;
    }
}
