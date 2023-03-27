
class Calculator {

    float calculate(String str) {
        while (str.contains('(') || str.contains(')')) {
            int openPos = -1
            int closePos = -1
            int size = str.size();

            for (i in size - 1..0) {
                if (str[i] == '(') {
                    closePos = openPos = i;
                    while (closePos < size - 1) {
                        if (str[closePos] == ')') break
                        closePos++
                    }
                    break
                }
            }
            if (openPos > -1 && closePos > -1) {
                str = replaceExpression(openPos, closePos, str)
            }
        }
        doCalculating(str)
    }

    private String replaceExpression(int openPos, int closePos, String str) {
        String[] expression = Arrays.copyOfRange(str as String[], openPos + 1, closePos)
        String oldExpression = arrayToString(expression)
        float result = doCalculating(oldExpression)
        str.replace("($oldExpression)", "$result")
    }

    private float doCalculating(String expression) {
        float result
        def check = expression ==~ /^(\d+\.\d+|\d+)$/
        if(check){
            return Float.parseFloat(expression)
        }
        while(expression != "$result"){
            def matcher = expression =~ /(\d+\.\d+|\d+)([\*|\/])(\d+\.\d+|\d+)/
            if (matcher.count) {
                String firstExpression = matcher[0][0]
                String operand = matcher[0][2]
                def firstNumber = Float.parseFloat(matcher[0][1] as String)
                def secondNumber = Float.parseFloat(matcher[0][3] as String)
                if (operand == "*") result = firstNumber * secondNumber
                if (operand == "/") result = firstNumber / secondNumber
                expression = expression.replace("$firstExpression", "$result")
            } else {
                matcher = expression =~ /(\d+\.\d+|\d+)([\+|\-])(\d+\.\d+|\d+)/
                String firstExpression = matcher[0][0]
                String operand = matcher[0][2]
                def firstNumber = Float.parseFloat(matcher[0][1] as String)
                def secondNumber = Float.parseFloat(matcher[0][3] as String)
                if (operand == "+") result = firstNumber + secondNumber
                if (operand == "-") result = firstNumber - secondNumber
                expression = expression.replace("$firstExpression", "$result")
            }
        }
        return result
    }

    private String arrayToString(String[] arr) {
        StringBuilder builder = new StringBuilder()
        arr.collect(item -> builder.append(item))
        builder.toString()
    }

}
