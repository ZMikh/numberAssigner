package com.jmikh;

import com.jmikh.assign.IElement;
import com.jmikh.assign.IElementNumberAssigner;
import com.jmikh.assign.impl.ElementExampleImpl;
import com.jmikh.assign.impl.Task2Impl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        IElementNumberAssigner task = Task2Impl.INSTANCE;

        ElementExampleImpl.Context context = new ElementExampleImpl.Context();

        List<IElement> elementExamples = List.of(
                new ElementExampleImpl(context, 3),
                new ElementExampleImpl(context, 5),
                new ElementExampleImpl(context, 2),
                new ElementExampleImpl(context, 0),
                new ElementExampleImpl(context, 1));

        task.assignNumbers(elementExamples);

        elementExamples.forEach(iElement -> System.out.println(iElement.getNumber()));
        assert context.getOperationCount() == 5;
    }
}