public class Relation extends Expression {

    @Override
    int calculate() {
        int left_number = 0;
        int right_number = 0;
        if(left != null)
            left_number = left.calculate();
        if(right != null)
            right_number = right.calculate();
        int result = 0;
        switch (opcode){
            case less:
                result = (left_number < right_number) ? 1 : 0;
                break;
            case less_eq:
                result = (left_number <= right_number) ? 1 : 0;
                break;
            case greater:
                result = (left_number > right_number) ? 1 : 0;
                break;
            case equal:
                result = (left_number == right_number) ? 1 : 0;
                break;
            case not_eq:
                result = (left_number != right_number) ? 1 : 0;
                break;
            case greater_eq:
                result = (left_number >= right_number) ? 1 : 0;
                break;
        }
        return result;
    }

    @Override
    String toJSON() {
        StringBuilder json = new StringBuilder();
        json.append("{\n");
        switch (opcode){
            case less:
                json.append("     \"Operation\" : \"" + "<" + "\",\n");
                break;
            case less_eq:
                json.append("     \"Operation\" : \"" + "<=" + "\",\n");
                break;
            case greater:
                json.append("     \"Operation\" : \"" + ">" + "\",\n");
                break;
            case greater_eq:
                json.append("     \"Operation\" : \"" + ">=" + "\",\n");
                break;
            case equal:
                json.append("     \"Operation\" : \"" + "==" + "\",\n");
                break;
            case not_eq:
                json.append("     \"Operation\" : \"" + "!=" + "\",\n");
                break;
        }
        if(left != null){
            json.append("     \"LeftExpression\" : \n").append(JSONHandler.handle(left.toJSON()));
        }
        if(right != null){
            if(left != null)
                json.append(",\n");
            json.append("     \"RightExpression\" : \n").append(JSONHandler.handle(right.toJSON()));
        }
        json.append("\n}");
        return json.toString();
    }

    protected enum Opcode { less, less_eq, greater, greater_eq, equal, not_eq, none}
    private Opcode opcode;
    private Expression left, right;

    public Relation(Opcode opcode, Expression left, Expression right) {
        this.opcode = opcode;
        this.left = left;
        this.right = right;
    }

    public Expression getLeft() {
        return left;
    }

    public void setLeft(Expression left) {
        this.left = left;
    }

    public Expression getRight() {
        return right;
    }

    public void setRight(Expression right) {
        this.right = right;
    }

    public Opcode getOpcode() {
        return opcode;
    }

    public void setOpcode(Opcode opcode) {
        this.opcode = opcode;
    }
}
