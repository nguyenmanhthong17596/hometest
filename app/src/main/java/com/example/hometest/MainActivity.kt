package com.example.hometest


import android.annotation.SuppressLint
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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


//        adapter.notifyDataSetChanged()
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
//                Toast.makeText(applicationContext, itemsName, Toast.LENGTH_SHORT).show()
            }
            val view = findViewById<RecyclerView>(R.id.itemList)
            view.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)

            val adapter = ItemAdapter(this@MainActivity , listOfItems)
            view.adapter = adapter


        }
    }

    private fun handleData(name: String) {

        // chuyen chuoi thanh mang ki tu
        Log.i("aaa", "text length: ${name.length}")
        val midPosition = (name.length / 2) - 1
        var delta1 = 0
        var delta2 = 0

        // chuoi sau khi tach
        var first = ""
        var last = ""
        val textAfterChange = ""
        var firstWord1 = ""
        var firstWord2 = ""

        var lastWord1 = ""
        var lastWord2 = ""

        if (!name.contains(' ')) {
            /* neu item chi co 1 chu -> set text k can chia chu */
            listOfItems.add(Items(name, ""))
            Log.i(
                "thong",
                "text cui cung : first word: $name - last word: "
            )
        } else {
            for (i in midPosition downTo 0) {
                for (j in midPosition until (name.length - 1)) {
                    /* xet truong hop dau ' ' nam tai vi tri mid position  */
                    if (i == midPosition && j == midPosition) {
                        if (name[midPosition] == ' ') {
                            first = name.substring(0, midPosition)
                            last = name.substring(midPosition)
                            listOfItems.add(Items(first, last))

                        }
                    } else {
                        val wordToSplit2 = name[j]
                        if (wordToSplit2 == ' ') {
                            first = name.substring(0, j)
                            last = name.substring(j)
                            delta2 = last.length - first.length
                            // choose test
                            if (delta2 > 0) {
                                firstWord2 = first
                                lastWord2 = last
                                Log.i("thong", "String1 : $firstWord2 - String2: $lastWord2")
                            }
                        }
                    }

                }
                val wordToSplit1 = name[i]
                if (wordToSplit1 == ' ') {
                    first = name.substring(0, i)
                    last = name.substring(i)

                    // set text for UI
                    delta1 = last.length - first.length
                    if (delta1 > 0) {
                        firstWord1 = first
                        lastWord1 = last
                        Log.i("thong", "String1 : $firstWord1 - String2: $lastWord1")
                    }
                }
            }
            if (delta1 < delta2) {
                listOfItems.add(Items(firstWord1, lastWord1))
                Log.i(
                    "thong",
                    "text cui cung : first word: $firstWord1 - last word: $lastWord1"
                )
            } else {
                listOfItems.add(Items(firstWord2, lastWord2))
                Log.i(
                    "thong",
                    "text cui cung : firs tword: $firstWord2 - last word: $lastWord2"
                )
            }

        }
    }
}

