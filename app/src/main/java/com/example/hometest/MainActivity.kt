package com.example.hometest

import android.annotation.SuppressLint
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val urlJson = "https://raw.githubusercontent.com/tikivn/android-home-test/v2/keywords.json"
        ReadJSON().execute(urlJson)
        listItems
    }


    @SuppressLint("StaticFieldLeak")
    inner class ReadJSON : AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg p0: String?): String {
            val content = StringBuilder()
            val url = URL(p0[0])
            val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
            val inputStreamReader = InputStreamReader(urlConnection.inputStream)
            val bufferedReader = BufferedReader(inputStreamReader)


            var line: String
            try {
                do {
                    line = bufferedReader.readLine()
                    if (line != null) {
                        content.append(line)
                    }

                } while (true)

            } catch (e: Exception) {
                Log.i("AAA", e.toString())
            }

            return content.toString()
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            var itemAfterEdit : String
            val jsonArr = JSONArray(result)

            for (items in 0 until jsonArr.length()) {
                val itemsName = jsonArr.getString(items)
                itemAfterEdit = handleData(itemsName)
                Toast.makeText(applicationContext,itemsName,Toast.LENGTH_SHORT).show()
                listItems.add(itemAfterEdit)

            }
        }
    }

    fun handleData(name: String): String {
        // chuyen chuoi thanh mang ki tu
        val position = (name.length) / 2

        // chuoi sau khi tach
        var first: String
        var last: String
        var textAfterChange : String = ""

        // lay gia tri tai vi tri giua cua chuoi
        // so sanh gia tri do voi " " neu bang tra ve chuoi moi
        // else se di chuyen ve 2 phia den khi tim dc " " moi-> so sanh 2 chuoi sau khi tach neu nho nhat -> tach chuoi

        for (i in 0 until position) {
            for (j in (name.length) downTo position) {
                val wordToSplit = name[position]
                if (wordToSplit == ' ') {
                    first = name.substring(position)
                    last = name.substring(position, name.length)
                    Log.i("THONG", "String1 : $first\nString2: $last")
                    // set text to UI
                    textAfterChange = "$first\n$last"
                    Toast.makeText(applicationContext, textAfterChange, Toast.LENGTH_LONG).show()
                }
            }

        }
        return textAfterChange
    }

    companion object {
        var listItems : ArrayList<String> = ArrayList()
    }
}
