package org.ucomplex.ucomplex.LoginModuleTests;

import org.junit.Test;

import java.util.LinkedList;



/**
 * ---------------------------------------------------
 * Created by Sermilion on 20/11/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class LoginPresenterTest {
    @Test
    public void presenterCreatedCorrectly(){


    }

    @Test(expected = RuntimeException.class)
    public void testList(){
        //You can mock concrete classes, not just interfaces
//        LinkedList mockedList = mock(LinkedList.class);
//
//        //stubbing
//        when(mockedList.get(0)).thenReturn("first");
//        when(mockedList.get(1)).thenThrow(new RuntimeException());

        //following prints "first"
//        System.out.println(mockedList.get(0));

//        //following throws runtime exception
//        System.out.println(mockedList.get(1));
//
//        //following prints "null" because get(999) was not stubbed
//        System.out.println(mockedList.get(999));

        //Although it is possible to verify a stubbed invocation, usually it's just redundant
        //If your code cares what get(0) returns, then something else breaks (often even before verify() gets executed).
        //If your code doesn't care what get(0) returns, then it should not be stubbed. Not convinced? See here.
//        verify(mockedList).get(0);
    }
}
