package com.digital.playground.unittest;

public class CollaboratorForPartialMocking {

    public static String staticMethod() {
        return "Hello Baeldung!";
    }

    public final String finalMethod() {
        return "Hello Baeldung!";
    }

    private String privateMethod() {
        return "Hello Baeldung!";
    }

    public String privateMethodCaller() {
        return privateMethod() + " Welcome to the Java world.";
    }

    private int privateApi(String whatever, int num) throws Exception {
        System.out.println("In privateAPI");
        int resp = 10;
        return resp;
    }

    public void publicApi() {
        System.out.println("In publicApi");
        int result = 0;
        try {
            result = privateApi("hello", 1);
        } catch (Exception e) {
            /// Assert.fail();
        }
        System.out.println("result : " + result);
        if (result == 20) {
            throw new RuntimeException("boom");
        }
    }

}