//package ru.gameserver;
//
//import java.util.List;
//import java.util.Stack;
//
//public class TestMain {
//
//
//    Stack<Integer> st = new Stack<>();
//    private boolean delim(char c) {
//        return c == ' ';
//    }
//
//    private boolean is_op(char c) {
//        return c=='+' || c=='-' || c=='*' || c=='/';
//    }
//
//
//    private int priority(char op) {
//        return
//            op == '+' || op == '-' ? 1 :
//                op == '*' || op == '/' ? 2 :
//                    -1;
//    }
//
//    private void process_op(char op) {
//        int r = st.pop();
//        int l = st.pop();
//        switch (op) {
//            case '+':  st.push (l + r);  break;
//            case '-':  st.push (l - r);  break;
//            case '*':  st.push (l * r);  break;
//            case '/':  st.push (l / r);  break;
//        }
//    }
//
//    private int calc (String str) {
//        char[] s = str.toCharArray();
//        Stack<Character> op = new Stack<>();
//        for (int i=0; i<str.length(); ++i)
//            if (!delim (s[i]))
//                if (s[i] == '(')
//                    op.push ('(');
//                else if (s[i] == ')') {
//                    while (op.peek() != '(')
//                        process_op (op.peek());
//                    op.pop();
//                    op.pop();
//                }
//                else if (is_op (s[i])) {
//                    char current_op = s[i];
//                    while (!op.empty() && priority(op.peek()) >= priority(s[i]))
//                        process_op (op.peek());  op.pop();
//                    op.push (current_op);
//                }
//                else {
//                    StringBuilder operand= new StringBuilder();
//                    while (i < str.length() && (s[i] >= '0' && s[i] <= '9')) {
//                        operand.append(s[i++]);
//                    }
//                    --i;
//                    if (Character.isDigit(operand.charAt(0)))
//                        st.push (Integer.parseInt(operand.toString()));
//                    else
//                        st.push (Integer.parseInt(operand.toString()));
//                }
//        while (!op.empty())
//            process_op (op.peek());  op.pop();
//        return st.peek();
//    }
//
//    public static void main(String[] args) {
//
//
//        String strMath="52-38*(11-15)/33-71*3+2";
//
//        calc(strMath);
//
//    }
//}
//
//
//
//
//
//
//
