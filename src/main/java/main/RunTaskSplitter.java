package main;

import data.Group;
import data.Person;
import fileHandler.DataGetter;
import fileHandler.DataSaver;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class RunTaskSplitter {
    public static void main(String[] args) {
        DataGetter dg = new DataGetter();
        DataSaver sv = new DataSaver();
        try {
            List<Group> groups = dg.getDataFromFile();
            Collection<Person> people = dg.getAllPeople();
            System.out.println(groups);
            groups.forEach(Group::assignTasks);
            System.out.println(groups);
            sv.saveDataToCSVs(groups);
            System.out.println("");

            groups.forEach((g) -> System.out.println(g.getSavableGroupString()));
            System.out.println("");
            people.forEach((p) -> System.out.println(p.getMessageText() + "\n"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
