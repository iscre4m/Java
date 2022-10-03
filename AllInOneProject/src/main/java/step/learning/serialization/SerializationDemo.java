package step.learning.serialization;

import step.learning.annotations.DemoClass;
import step.learning.annotations.EntryPoint;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
@DemoClass(priority = 1)
public class SerializationDemo {
    private final String OBJ_FILE = "./src/main/java/step/learning/serialization/data.ser";
    private final String LIST_FILE = "./src/main/java/step/learning/serialization/list.ser";

    @EntryPoint
    public void run() {
        //serialize();
        //deserialize();
        //serializeList();
        deserializeList();
    }

    public void serialize() {
        final DataObject first = new DataObject();
        final DataObject second = new DataObject(7, 1d);
        final DataObject third = new DataObject(7, 2d);
        final DataObject fourth = new DataObject(7, 3d, "string", 13);

        try (FileOutputStream file = new FileOutputStream(OBJ_FILE)) {
            ObjectOutputStream oos = new ObjectOutputStream(file);
            oos.writeObject(first);
            oos.writeObject(fourth);
            oos.flush();
        } catch (IOException ex) {
            System.out.printf("Serialization error: %s", ex.getMessage());
            return;
        }
        System.out.println("--Done serializing--");
    }

    public void deserialize() {
        try (FileInputStream file = new FileInputStream(OBJ_FILE)) {
            ObjectInputStream ois = new ObjectInputStream(file);

            while (file.available() > 0) {
                DataObject data = (DataObject) ois.readObject();
                System.out.println(data);
            }
        } catch (Exception ex) {
            System.out.printf("Deserialization error: %s", ex.getMessage());
            return;
        }
        System.out.println("--Done deserializing--");
    }

    public void serializeList() {
        List<DataObject> list = new ArrayList<>();
        list.add(new DataObject(1));
        list.add(new DataObject(2));
        list.add(new DataObject(3));
        list.add(new DataObject(4));
        list.add(new DataObject(5));

        try (FileOutputStream file = new FileOutputStream(LIST_FILE)) {
            ObjectOutputStream oos = new ObjectOutputStream(file);
            oos.writeObject(list);
            oos.flush();
        } catch (IOException ex) {
            System.out.printf("Serialization error: %s", ex.getMessage());
            return;
        }
        System.out.println("--Done serializing--");
    }

    public void deserializeList() {
        try (FileInputStream file = new FileInputStream(LIST_FILE)) {
            ObjectInputStream ois = new ObjectInputStream(file);

            @SuppressWarnings("unchecked")
            List<DataObject> list = (List<DataObject>)ois.readObject();
            for (DataObject data : list) {
                System.out.println(data);
            }

        } catch (Exception ex) {
            System.out.printf("Deserialization error: %s", ex.getMessage());
            return;
        }
        System.out.println("--Done deserializing--");
    }
}