package com.example.simplecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tVInput = findViewById<TextView>(R.id.tVInput)
        val tVOutput = findViewById<TextView>(R.id.tVOutput)

        val inputUnits = mutableListOf<InputUnit>()

        var curResult = 0f;

        tVInput.text = "";
        tVOutput.text = "";

        val btnInputIds = arrayOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
            R.id.btnAdd, R.id.btnSubtract, R.id.btnMultiply, R.id.btnDivide,
            R.id.btnPower, R.id.btnMod, R.id.btnDot
        )

        for (btnId in btnInputIds) {
            val button = findViewById<Button>(btnId)
            button.setOnClickListener {
                // Append the text of the clicked button to the input text
                tVInput.append(button.text)
                evaluate(tVInput.text.toString(), inputUnits)
                curResult = calculateResult(inputUnits)
                tVOutput.text = curResult.toString()
            }
        }

        val btnOperationIds = arrayOf(
            R.id.btnAdd, R.id.btnSubtract, R.id.btnMultiply, R.id.btnDivide,
            R.id.btnPower, R.id.btnMod
        )

        for (btnID in btnOperationIds) {
            val button = findViewById<Button>(btnID)
            button.setOnClickListener {
                // Append the operation symbol to the input text
                tVInput.append(button.text)
            }
        }

        val btnAC = findViewById<TextView>(R.id.btnAC)
        btnAC.setOnClickListener {
            tVInput.text = ""
            tVOutput.text = ""
        }

        val btnEquals = findViewById<TextView>(R.id.btnEquals)
        btnEquals.setOnClickListener {
            val inputText = tVInput.text.toString()
            evaluate(inputText, inputUnits)
            curResult = calculateResult(inputUnits)
            tVOutput.text = curResult.toString()
        }

    }
}

fun evaluate(input: String, inputUnits: MutableList<InputUnit>){
    var lastIndex = 0
    var operand = '.'
    var index = 0;
    input.forEach { c ->
        if(c !in '0'..'9' && c != '.') {
            val num = input.substring(lastIndex, index).toFloat()
            inputUnits.add(InputUnit(operand, num))
            operand = input[index++]
            lastIndex = index
            return@forEach
        }
        if(index == input.length-1) {
            val num = input.substring(lastIndex, index+1).toFloat()
            inputUnits.add(InputUnit(operand, num))
        }
        index++
    }

    inputUnits.forEach {
        println(it.operand + it.number.toString())
    }

}

fun calculateResult(inputUnits: MutableList<InputUnit>): Float {
    var result = 0f
    inputUnits.forEach {
        when(it.operand) {
            '.' -> result = it.number
            '+' -> result += it.number
            '-' -> result -= it.number
            '*' -> result *= it.number
            '/' -> result /= it.number
            '.' -> result /= it.number
        }
    }
    return result
}

data class InputUnit (
    val operand: Char,
    val number: Float,
)