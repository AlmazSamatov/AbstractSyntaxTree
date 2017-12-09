public class Factor extends Expression {

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
            case multiplication:
                result = left_number * right_number;
                break;
            case division:
                result = left_number / right_number;
                break;
        }
        return result;
    }

    @Override
    String toJSON() {
        StringBuilder json = new StringBuilder();
        json.append("{\n");
        switch (opcode){
            case multiplication:
                json.append("     \"Operation\" : \"" + "*" + "\",\n");
                break;
            case division:
                json.append("     \"Operation\" : \"" + "/" + "\",\n");
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

    protected enum Opcode { multiplication, division }
    private Opcode opcode;
    private Expression left, right;

    Factor (Opcode opcode, Expression left, Expression right) {
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
