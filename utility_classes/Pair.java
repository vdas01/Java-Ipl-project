package utility_classes;

public class Pair implements Comparable<Pair> {
      private int priority_;
       private String value_;

   public Pair(int priority, String value) {
        this.priority_ = priority;
        this.value_ = value;
    }

    public int getPriority(){
        return priority_;
    }

    public String getValue(){
        return value_;
    }

    @Override
    public int compareTo(Pair other) {
        // Compare Pairs based on their priority_
        return Integer.compare(this.priority_, other.priority_);
    }
}