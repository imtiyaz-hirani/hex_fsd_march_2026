package com.ecom.main;

import com.ecom.model.Person;

public class HSDemo {
    public static void main(String[] args) {
        Person p1 = new Person(1, "harry potter");
        Person p2 = new Person(2, "ronald weasley");
        System.out.println(p1.getName());

        Person p3 = p1;
        p3 = new Person(3, "hermione granger");
        System.out.println(p3.getName());
    }
}
/*
Person p1 : ref
new Person("1", "harry potter") : Object

References go on stack
Object go in Heap
* */
