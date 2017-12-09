public class Parenthesized extends Primary {

    private Expression expression;

    public Parenthesized(Expression expression) {
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    int calculate() {
        return expression.calculate();
    }

    @Override
    String toJSON() {
        return expression.toJSON();
    }
}
