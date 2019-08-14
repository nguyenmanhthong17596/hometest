package com.example.hometest

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
    }


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

            val jsonArr = JSONArray(result)
            for (items in 0 until jsonArr.length()) {
                val itemsName = jsonArr.getString(items)
                handleData(itemsName)
            }
            listItems.

        }
    }

    fun handleData(name: String) {
        // chuyen chuoi thanh mang ki tu
        val position = (name.length) / 2

        // lay gia tri tai vi tri giua cua chuoi
        // so sanh gia tri do voi " " neu bang tra ve chuoi moi
        // else se di chuyen ve 2 phia den khi tim dc " " moi-> so sanh 2 chuoi sau khi tach neu nho nhat -> tach chuoi

        for (i in 0 until position) {
            for (j in (name.length) downTo position) {
                val wordToSplit = name[position]
                if (wordToSplit == ' ') {
                    val string1 = name.substring(position)
                    val string2 = name.substring(position, name.length)
                    Log.i("THONG", "String1 : $string1\nString2: $string2")
                    // set text to UI
                    val textAfterChangeError = "$string1\n$string2"
                    Toast.makeText(applicationContext, textAfterChangeError, Toast.LENGTH_LONG).show()
                }
            }

        }

    }

    companion object {
        var listItems : List<String>? = null
    }
}
