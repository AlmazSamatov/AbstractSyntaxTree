public class Logical extends Expression {

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
            case and:
                result = left_number & right_number;
                break;
            case or:
                result = left_number | right_number;
                break;
            case xor:
                result = left_number ^ right_number;
                break;
        }
        return result;
    }

    @Override
    String toJSON() {
        StringBuilder json = new StringBuilder();
        json.append("{\n");
        switch (opcode){
            case and:
                json.append("     \"Operation\" : \"" + "and" + "\",\n");
                break;
            case or:
                json.append("     \"Operation\" : \"" + "or" + "\",\n");
                break;
            case xor:
                json.append("     \"Operation\" : \"" + "xor" + "\",\n");
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

    protected enum Opcode { and, or, xor, none }
    private Opcode opcode;
    private Expression left, right;

    Logical(Opcode opcode, Expression left, Expression right) {
        this.opcode = opcode;
        this.left = left;
        this.right = right;
    }

    public Opcode getOpcode() {
        return opcode;
    }

    public void setOpcode(Opcode opcode) {
        this.opcode = opcode;
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
}
