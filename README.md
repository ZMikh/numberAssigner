# numberAssigner
Механизм «умного» присвоения номеров элементам списка

## Ограничения
- метод работает только с существующими элементами,
- элементы уникальны,
- вызов element.setNumber(i) возможен, если выполняется условие elements (e.number ≠ i),
- аргументом метода может быть неизменяемый лист,
- минимизировать использование IElement.setupNumber(int)
