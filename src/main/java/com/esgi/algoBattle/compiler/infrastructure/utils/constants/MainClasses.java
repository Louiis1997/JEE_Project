package com.esgi.algoBattle.compiler.infrastructure.utils.constants;

public final class MainClasses {
    public static final String JAVA = """
            public class Main%s {
            \tpublic static void main(String[] args) {
            \t\t%s
            \t}

            \tstatic %s
            }""";

    public static final String CPP = """
            #include <iostream>
            using namespace std;

            %s

            int main() {
                %s    return 0;
            }""";


}
