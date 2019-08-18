package data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Person {
    private String name;
    private String phoneNumber;
    private List<Task> tasks;
    private final int PHONE_NUMBER_LENGTH = 10;

    public Person(String name, String phoneNumber) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty or null!");
        }
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be empty or null");
        }
        if (phoneNumber.length() != PHONE_NUMBER_LENGTH) {
            throw new IllegalArgumentException("Phone number must be " + PHONE_NUMBER_LENGTH + " digits long");
        }
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.tasks = new ArrayList<>();
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public String getMessageText() {
        if (this.tasks.isEmpty()) {
            throw new IllegalStateException("Person has no tasks");
        }

        StringBuilder sb = new StringBuilder();
        String taskPlural = this.tasks.size() > 1 ? "tasks are " : "task is";
        String intro = "Hello " + this.name + ",\n" + "Your " + taskPlural + " to:\n";
        sb.append(intro);
        for (int i = 0; i < this.tasks.size(); i++) {
            sb.append(this.tasks.get(i));

            if (i < this.tasks.size() - 1) {
                sb.append(", ");
                if (i == this.tasks.size() - 2) {
                    sb.append("and ");
                }
            } else {
                sb.append(".");
            }
        }

        return sb.toString();
    }

    public void clearTasks() {
        this.tasks.clear();
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return name.equals(person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
