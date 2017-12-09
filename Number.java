public class Number extends Primary{

    private int value;

    Number(int value){
        setValue(value);
    }

    public void setValue(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }

    @Override
    int calculate() {
        return value;
    }

    @Override
    String toJSON() {
        return "{\n" + "     \"Number\" : \"" + String.valueOf(value) + "\"" + "\n}";
    }
}
