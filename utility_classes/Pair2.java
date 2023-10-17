package utility_classes;

public class Pair2 implements Comparable<Pair2> {
   private float priority_;
   private String value_;

   public Pair2(float priority, String value) {
        this.priority_ = priority;
        this.value_ = value;
    }

    public float getPriority(){
        return priority_;
    }

    public String getValue(){
        return value_;
    }

    @Override
    public int compareTo(Pair2 other) {
        // Compare Pairs based on their priority_
        return Float.compare(this.priority_, other.priority_);
    }
}
