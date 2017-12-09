public class Term extends Expression {

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
            case plus:
                result = left_number + right_number;
                break;
            case minus:
                result = left_number - right_number;
                break;
        }
        return result;
    }

    @Override
    String toJSON() {
        StringBuilder json = new StringBuilder();
        json.append("{\n");
        switch (opcode){
            case plus:
                json.append("     \"Operation\" : \"" + "+" + "\",\n");
                break;
            case minus:
                json.append("     \"Operation\" : \"" + "-" + "\",\n");
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

    protected enum Opcode { plus, minus }
    private Opcode opcode;
    private Expression left, right;

    Term (Opcode opcode, Expression left, Expression right) {
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
