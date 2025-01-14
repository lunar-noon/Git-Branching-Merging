let display = document.getElementById('display');
let currentInput = '';

function appendNumber(number) {
    currentInput += number;
    display.value = currentInput;
}

function appendOperator(operator) {
    currentInput += operator;
    display.value = currentInput;
}

function clearDisplay() {
    currentInput = '';
    display.value = '';
}

function calculate() {
    try {
        display.value = eval(currentInput);
        currentInput = display.value;
    } catch (error) {
        display.value = 'Error';
        currentInput = '';
    }
}