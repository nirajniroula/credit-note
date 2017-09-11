package com.wolf.nniroula.creditrecorder.controller;

import com.wolf.nniroula.creditrecorder.ui.interfaces.AddToExistedListener;

/**
 * Created by Niraj Niroula on 9/6/17.
 */

public class HomeController {

    public static AddToExistedListener addInterface;


    public HomeController(AddToExistedListener myInterface) {
        addInterface = myInterface;

    }
}
