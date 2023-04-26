package com.jmikh.assign.impl;

import com.jmikh.assign.IElement;
import com.jmikh.assign.IElementNumberAssigner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <h1>Задание №2</h1>
 * Реализуйте интерфейс {@link IElementNumberAssigner}.
 *
 * <p>Помимо качества кода, мы будем обращать внимание на оптимальность предложенного алгоритма по времени работы
 * с учетом скорости выполнения операции присвоения номера:
 * большим плюсом (хотя это и не обязательно) будет оценка числа операций, доказательство оптимальности
 * или указание области, в которой алгоритм будет оптимальным.</p>
 */
public class Task2Impl implements IElementNumberAssigner {

    // ваша реализация должна работать, как singleton. даже при использовании из нескольких потоков.
    public static final IElementNumberAssigner INSTANCE = new Task2Impl();

    private Task2Impl() {
    }

    @Override
    public void assignNumbers(final List<IElement> elements) {
        final Map<IElement, Integer> isNeedToAssign = new HashMap<>();

        if (elements == null || elements.isEmpty()) {
            return;
        }

        int freeNumber = elements.stream()
                .map(IElement::getNumber).max(Integer::compareTo).get() + 1;

        for (int i = 0; i < elements.size(); i++) {
            int currentElement = elements.get(i).getNumber();
            if (currentElement == i) {
                continue;
            }
            isNeedToAssign.put(elements.get(i), i);
        }

        assignNumbers(isNeedToAssign, freeNumber, true);
    }

    private void assignNumbers(Map<IElement, Integer> isNeedToChange, int freeNumber, boolean onlyChangeable) {
        // Обновления больше не требуются
        if (isNeedToChange.isEmpty()) {
            return;
        }
        // Оставшиеся элементы невозможно изменить не использовав промежуточное значение
        if (!onlyChangeable) {
            Set<Map.Entry<IElement, Integer>> entries = isNeedToChange.entrySet();
            entries.stream()
                    .findFirst()
                    .get()
                    .getKey()
                    .setupNumber(freeNumber);

            Map<IElement, Integer> remainedElements = entries.stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            assignNumbers(remainedElements, freeNumber, true);
            return;
        }

        Set<Map.Entry<IElement, Integer>> entries = isNeedToChange.entrySet();
        Set<Integer> keys = entries.stream()
                .map(it -> it.getKey().getNumber())
                .collect(Collectors.toSet());

        var iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<IElement, Integer> entry = iterator.next();
            Integer targetNum = entry.getValue();
            if (!keys.contains(targetNum)) {
                entry.getKey().setupNumber(targetNum);
                iterator.remove();
            }
        }

        Map<IElement, Integer> remainedElements = entries.stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        assignNumbers(remainedElements, freeNumber, isNeedToChange.size() != remainedElements.size());
    }
}
