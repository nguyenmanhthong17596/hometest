package com.example.hometest


import android.annotation.SuppressLint
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {


    private var listOfItems = ArrayList<Items>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val urlJson = "https://raw.githubusercontent.com/tikivn/android-home-test/v2/keywords.json"
        ReadJSON().execute(urlJson)

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

            val jsonArr = JSONArray(result)

            for (items in 0 until jsonArr.length()) {
                val itemsName = jsonArr.getString(items)
                handleData(itemsName)
            }
            val view = findViewById<RecyclerView>(R.id.itemList)
            view.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)

            val adapter = ItemAdapter(this@MainActivity, listOfItems)
            view.adapter = adapter


        }
    }

    private fun handleData(name: String) {

        // chuyen chuoi thanh mang ki tu
        Log.i("aaa", "text length: ${name.length}")
        val midPosition = (name.length / 2) - 1

        // chuoi sau khi tach
        val first: String
        val last: String

        if (!name.contains(' ')) {
            /* neu item chi co 1 chu -> set text k can chia chu */
            listOfItems.add(Items(name, ""))
            Log.i(
                "thong",
                "text cui cung : first word: $name - last word: "
            )
            return
        } else {
            for (i in midPosition downTo 0) {
                if (name[i] == ' ') {
                    first = name.substring(0, i)
                    last = name.substring(i)
                    listOfItems.add(Items(first, last))
                    return
                }
            }
        }
        return
    }
}

