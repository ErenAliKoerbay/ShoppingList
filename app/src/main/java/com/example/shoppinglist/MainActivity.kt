package com.example.shoppinglist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shoppinglist.ui.theme.ShoppingListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShoppingListTheme {
                SimpleTextField()
                VisualList()
                }
            }
        }
    }

// Jedes Element der Liste ist Teil dieser Klasse
class Groceries{
    var name = ""
    var amount = ""
}

// braucht flexible Klasse, um das UI updaten zu können bei neuen
var einkaufsListe = ArrayList<Groceries>()

// Textfelder zum Eingeben von Element und der Anzahl davon
@Composable
fun SimpleTextField() {
    Column (modifier = Modifier.padding(16.dp)) {
        var text by remember { mutableStateOf(TextFieldValue("")) }
        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it
            },
            //ermöglicht am Handy nur Texteingabe
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            placeholder = { Text(text = "Milch, Beeren, Brot...") }
        )
        var amount by remember { mutableStateOf(TextFieldValue("")) }
        OutlinedTextField(
            value = amount,
            onValueChange = {
                amount = it
            },
            // ermöglicht nur Eingabe von Zahlen am Handy
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(text = "Wie viel?") },
        )
        //Button der die eingegeben Werte aufnimmt und übergibt
        ElevatedButton(onClick = { AddToList(amount.text, text.text) }
        ){
            Text("Auf die Liste!")
        }
    }
}

// fügt neues Objekt zur Liste hinzu
fun AddToList(amount: String, grocery: String) {
    if (amount != "" && grocery != "") {
        var newItem = Groceries()
        newItem.name = grocery
        newItem.amount = amount
        einkaufsListe.add(newItem)
    } else {
    return
    }
}

// soll die Liste visualieren indem es über diese iteriert
@Composable
fun VisualList(){
    einkaufsListe.forEach { item ->
        Text(
            text = "${item.amount}x ${item.name}",
            modifier = Modifier.clickable{einkaufsListe.remove(item)}
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun ListPreview() {
    ShoppingListTheme {
        SimpleTextField()
        VisualList()
    }
}