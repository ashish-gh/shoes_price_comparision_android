package com.example.shoespricecomparision;

import com.example.shoespricecomparision.BLL.LoginBLL;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class ShopWithUsTest {


    @Test
    public void testLogin(){
        LoginBLL loginBLL = new LoginBLL("ashish.gh123@gmail.com", "ashish");
        boolean result = loginBLL.checkUser();
        assertEquals(true,  result);
    }



}
