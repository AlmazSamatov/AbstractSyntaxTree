import java.lang.*;

public class Parser {

    private enum Type { number, less, less_eq, greater, greater_eq, equal, not_eq, plus, minus, multiplication, division, and, or, xor, l_paren, r_paren}
    private String input;
    private int curCharIndex = 0;
    private Character currentChar;
    private String currentToken;
    private Type currentTokenType;

    Parser(String s) {
        input = s;
        currentChar = input.charAt(curCharIndex);
        getNextToken();
    }

    private Primary parsePrimary(){
        Primary result = null;
        String token = currentToken;
        if(currentTokenType.equals(Type.number)){
            handle(Type.number);
            result = new Number(Integer.parseInt(token));
        } else if(currentToken.equals("(")){
            handle(Type.l_paren);
            result = new Parenthesized(parse());
            handle(Type.r_paren); // skip right parenthesis ')'
        }
        return result;
    }

    private Expression parseFactor(){
        Expression expression = parsePrimary();
        if(tokenIsIn(new String[] {"*", "/"})){
            switch (currentToken) {
                case "*":
                    handle(Type.multiplication);
                    expression = new Factor(Factor.Opcode.multiplication, expression, parseFactor());
                    break;
                case "/":
                    handle(Type.division);
                    expression = new Factor(Factor.Opcode.division, expression, parseFactor());
                    break;
            }
        }
        return expression;
    }

    private Expression parseTerm(){
        Expression expression = parseFactor();
        if(tokenIsIn(new String[] {"+", "-"})){
            switch (currentToken) {
                case "+":
                    handle(Type.plus);
                    expression = new Term(Term.Opcode.plus, expression, parseTerm());
                    break;
                case "-":
                    handle(Type.minus);
                    expression = new Term(Term.Opcode.minus, expression, parseTerm());
                    break;
            }
        }
        return expression;
    }

    private Expression parseRelation(){
        Expression expression = parseTerm();
        if(tokenIsIn(new String[] {">", "<", "<=", ">=", "==", "/="})){
            switch (currentToken){
                case "<":
                    handle(Type.less);
                    expression = new Relation(Relation.Opcode.less, expression, parseRelation());
                    break;
                case ">":
                    handle(Type.greater);
                    expression = new Relation(Relation.Opcode.greater, expression, parseRelation());
                    break;
                case "<=":
                    handle(Type.less_eq);
                    expression = new Relation(Relation.Opcode.less_eq, expression, parseRelation());
                    break;
                case ">=":
                    handle(Type.greater_eq);
                    expression = new Relation(Relation.Opcode.greater_eq, expression, parseRelation());
                    break;
                case "==":
                    handle(Type.equal);
                    expression = new Relation(Relation.Opcode.equal, expression, parseRelation());
                    break;
                case "!=":
                    handle(Type.not_eq);
                    expression = new Relation(Relation.Opcode.not_eq, expression, parseRelation());
                    break;
            }
        }
        return expression;
    }

    private Expression parseLogical(){
        Expression expression = parseRelation();
        if(tokenIsIn(new String[] {"and", "or", "xor"})){
            switch (currentToken){
                case "or":
                    handle(Type.or);
                    expression = new Logical(Logical.Opcode.or, expression, parseLogical());
                    break;
                case "xor":
                    handle(Type.xor);
                    expression = new Logical(Logical.Opcode.xor, expression, parseLogical());
                    break;
                case "and":
                    handle(Type.and);
                    expression = new Logical(Logical.Opcode.and, expression, parseLogical());
                    break;
            }
        }
        return expression;
    }

    Expression parse() {
        return parseLogical();
    }

    private void handle(Type expected){
        if(expected == currentTokenType)
            getNextToken();
        else
            error();
    }

    private void goToNextChar(){
        curCharIndex++;
        if(curCharIndex >= input.length())
            currentChar = null;
        else
            currentChar = input.charAt(curCharIndex);
    }

    private void getNextToken(){
        while (currentChar != null) {
            if(currentChar == ' '){
                while(currentChar != null && currentChar == ' '){
                    goToNextChar();
                }
                continue;
            }
            if(Character.isDigit(currentChar)){
                StringBuilder stringBuilder = new StringBuilder();
                while(currentChar != null && Character.isDigit(currentChar)){
                    stringBuilder.append(currentChar);
                    goToNextChar();
                }
                currentToken = stringBuilder.toString();
                currentTokenType = Type.number;
            }
            else if(currentChar == '+'){
                currentToken = "+";
                currentTokenType = Type.plus;
                goToNextChar();
            }
            else if(currentChar == '-'){
                currentToken = "-";
                currentTokenType = Type.minus;
                goToNextChar();
            }
            else if(currentChar == '/'){
                currentToken = "/";
                currentTokenType = Type.division;
                goToNextChar();
            }
            else if(currentChar == '*'){
                currentToken = "*";
                currentTokenType = Type.multiplication;
                goToNextChar();
            }
            else if(currentChar == '>') {
                if (curCharIndex + 1 < input.length() && input.charAt(curCharIndex + 1) == '='){
                    currentToken = ">=";
                    for(int i = 0; i < 2; i++)
                        goToNextChar();
                    currentTokenType = Type.greater_eq;
                }
                else {
                    currentToken = ">";
                    currentTokenType = Type.greater;
                }
                goToNextChar();
            }
            else if(currentChar == '<'){
                if (curCharIndex + 1 < input.length() && input.charAt(curCharIndex + 1) == '='){
                    currentToken = "<=";
                    for(int i = 0; i < 2; i++)
                        goToNextChar();
                    currentTokenType = Type.less_eq;
                }
                else {
                    currentToken = "<";
                    currentTokenType = Type.less;
                }
                goToNextChar();
            }
            else if(currentChar == '='){
                if(curCharIndex + 1 < input.length() && input.charAt(curCharIndex + 1) == '='){
                    currentToken = "==";
                    currentTokenType = Type.equal;
                    for(int i = 0; i < 2; i++)
                        goToNextChar();
                } else
                    error();
            }
            else if(currentChar == '!'){
                if(curCharIndex + 1 < input.length() && input.charAt(curCharIndex + 1) == '='){
                    currentToken = "!=";
                    currentTokenType = Type.not_eq;
                    for(int i = 0; i < 2; i++)
                        goToNextChar();
                } else
                    error();
            }
            else if(currentChar == 'a'){
                if(curCharIndex + 2 < input.length() && input.charAt(curCharIndex + 1) == 'n' &&
                        input.charAt(curCharIndex + 2) == 'd'){
                    currentToken = "and";
                    currentTokenType = Type.and;
                    for(int i = 0; i < 3; i++)
                        goToNextChar();
                } else
                    error();
            }
            else if(currentChar == 'o'){
                if(curCharIndex + 1 < input.length() && input.charAt(curCharIndex + 1) == 'r'){
                    currentToken = "or";
                    currentTokenType = Type.or;
                    for(int i = 0; i < 2; i++)
                        goToNextChar();
                } else
                    error();
            }
            else if(currentChar == 'x'){
                if(curCharIndex + 2 < input.length() && input.charAt(curCharIndex + 1) == 'o' &&
                        input.charAt(curCharIndex + 2) == 'r'){
                    currentToken = "xor";
                    currentTokenType = Type.xor;
                    for(int i = 0; i < 3; i++)
                        goToNextChar();
                } else
                    error();
            }
            else if(currentChar == '('){
                currentToken = "(";
                currentTokenType = Type.l_paren;
                goToNextChar();
            }
            else if(currentChar == ')'){
                currentToken = ")";
                currentTokenType = Type.r_paren;
                goToNextChar();
            }
            else
                error();
            break;
        }
    }

    // checks whether currentToken in strings
    private boolean tokenIsIn (String[] strings){
        for(String s: strings){
            if(currentToken.equals(s))
                return true;
        }
        return false;
    }

    // checks whether string is digit
    private boolean isDigit(String string){
        for(int i = 0; i < string.length(); i++){
            if(!Character.isDigit(string.charAt(i)))
                return false;
        }
        return true;
    }

    private void error(){
        throw new Error("Invalid syntax!");
    }
}