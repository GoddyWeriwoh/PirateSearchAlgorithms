import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class HashTable {
    private int size;
    public ArrayList<MapBucket> bucket;
    private int[] ages  = new int[37];

    public HashTable(int size) {
        this.size = size;
        this.bucket = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            bucket.add(new MapBucket());
        }
    }

    public boolean containsPirate(String key) {
        int hash = getHash(key);
        // If the object for that key is not null, return true
        return !(Objects.isNull(bucket.get(hash)) || Objects.isNull(getPirate(key)));
    }

    public Pirate getPirate(String key) {
        int hash = getHash(key);
        // Going through all the pirate bucket (pirates with same hash)
        for (int i = 0; i < bucket.get(hash).getPirateBucket().size(); i++) {
            Pirate auxPirate = bucket.get(hash).getPirateBucket().get(i);
            // Return the pirate with that name
            if(auxPirate.getName().equals(key)) {
                return auxPirate;
            }
        }
        return null;
    }

    public Integer getHash(String key) {
        int hash = 7;
        for (int i = 0; i < key.length(); i++) {
            hash = hash*31 + key.charAt(i);
        }
        return Math.abs(hash)%size;
    }

    public void addPirate(String key, Pirate pirate) {
        // Getting the hash
        int hash = getHash(key);
        // if the position for that hash is null, create the bucket on that position
        if(bucket.get(hash) == null) {
           bucket.get(hash).pirateBucket = new ArrayList<Pirate>();
        }
        // Add the pirate
        bucket.get(hash).addPirate(pirate);
        // Update the age array for the histogram
        //System.out.println(pirate.getAge()-14);
        ages[pirate.getAge()-14] ++;

    }

    public void removePirate(String key){
        int hash = getHash(key);
        // Going through all the pirate bucket (pirates with same hash)
        for (int i = 0; i < bucket.get(hash).getPirateBucket().size(); i++) {
            Pirate auxPirate = bucket.get(hash).getPirateBucket().get(i);
            // Remove the pirate with that name
            if(auxPirate.getName().equals(key)) {
                ages[auxPirate.getAge()-14]--;
                bucket.get(hash).removePirate(auxPirate);
                System.out.println();
                break;
            }
        }


    }

    public int[] getAges() {
        return ages;
    }
}


class MapBucket{
    public List<Pirate> pirateBucket;

    public MapBucket() {
        pirateBucket = new LinkedList<>();
    }

    public List<Pirate> getPirateBucket() {
        return pirateBucket;
    }

    public void addPirate(Pirate pirate) {
        this.pirateBucket.add(pirate);
    }

    public void removePirate(Pirate pirate) {
        this.pirateBucket.remove(pirate);
    }

}

class Pirate{
    private String name;
    private String role;
    private String key;
    private int age;

    public Pirate(String name, String role, int age, String key) {
        this.name = name;
        this.role = role;
        this.age = age;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
    public void setValue(Pirate pirate){
        this.age = pirate.age;
        this.name = pirate.name;
        this.role = pirate.role;

    }
}

