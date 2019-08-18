package data;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class Group {
    private String name;
    private LinkedBlockingQueue<Person> people;
    private List<Task> allGroupTasks;
    private Map<Person, List<Task>> groupSpecificTaskList;

    public Group(String name) {
        this.name = name;
        this.people = new LinkedBlockingQueue<>();
        this.allGroupTasks = new ArrayList<>();
        this.groupSpecificTaskList = new HashMap<>();
    }

    public void addPeople(List<Person> people){
        this.people.addAll(people);
    }

    public void addTasks(List<Task> tasks) {
        this.allGroupTasks.addAll(tasks);
    }

    public void assignTasks() {
        if(this.allGroupTasks.isEmpty()) {
            throw new IllegalStateException("Cannot assign tasks if none exist!");
        }
        if(this.people.size() == 0) {
            throw new IllegalStateException("No people to assign tasks to!");
        }

        for(Task task : this.allGroupTasks) {
            Person person = this.people.poll();
            if(person == null) {
                throw new IllegalStateException("Person was null in Group::assignTasks");
            }

            //gather data to save to group specific csv files
            List<Task> tasks = this.groupSpecificTaskList.get(person);
            if(tasks == null) {
                tasks = new ArrayList<>();
            }
            tasks.add(task);
            this.groupSpecificTaskList.put(person, tasks);

            person.addTask(task);
            this.people.offer(person);
        }
    }

    public String getName() {
        return name;
    }

    public String getSavableGroupString() {
        StringBuilder sb = new StringBuilder();
        for(Person person : this.people) {
            sb.append(person.getName());
            sb.append(",");
            sb.append(getSavableTaskString(this.groupSpecificTaskList.get(person)));
            sb.append("\n");
        }
        return sb.toString();
    }

    private String getSavableTaskString(List<Task> tasks) {
        if(tasks == null) {
            return ""; //if there are fewer tasks than people, return empty string to be saved in csv
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            sb.append(task.toString());
            if (i < tasks.size() - 1) {
                sb.append(";");
            }
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "Group " + this.name + " contains the following people: " + this.people.toString()
                + " and the following tasks: " + this.allGroupTasks.toString();
    }
}
