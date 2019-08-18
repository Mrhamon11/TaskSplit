package data;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PersonTest {
    private static Person person;

    @BeforeClass
    public static void initPerson() {
        person = new Person("Avi", "2063561863");
    }

    @Test
    public void testSingleTaskMessage(){
        person.clearTasks();
        person.addTask(new Task("Mop & Sweep"));
        String message = person.getMessageText();
        Assert.assertEquals("Mop & Sweep.", message.split(":")[1].split("\n")[1]);
    }

    @Test
    public void testDoubleTaskMessage() {
        person.clearTasks();
        person.addTask(new Task("Garbage"));
        person.addTask(new Task("Recycling"));
        String message = person.getMessageText();
        Assert.assertEquals("Garbage, and Recycling.", message.split(":")[1].split("\n")[1]);
    }

    @Test
    public void testMultiTaskMessage() {
        person.clearTasks();
        person.addTask(new Task("Garbage"));
        person.addTask(new Task("Recycling"));
        person.addTask(new Task("Dust main room"));
        String message = person.getMessageText();
        Assert.assertEquals("Garbage, Recycling, and Dust main room.", message.split(":")[1].split("\n")[1]);
    }
}
