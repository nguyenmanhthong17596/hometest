package com.example.hometest

import android.annotation.SuppressLint
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.INotificationSideChannel
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_list.*
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    var listOfItems = ArrayList<Items>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val view = findViewById<RecyclerView>(R.id.itemList)
        view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val danhsach = ArrayList<Items>()

        danhsach.add(Items("Sach tao lao"))
        danhsach.add(Items("Onepiece"))
        danhsach.add(Items("asdczx"))
        danhsach.add(Items("1245"))
        danhsach.add(Items("anh chính là thanh xuân của em"))

        val urlJson = "https://raw.githubusercontent.com/tikivn/android-home-test/v2/keywords.json"
//        ReadJSON().execute(urlJson)

        val adapter = ItemAdapter(danhsach)

        view.adapter = adapter

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

            var itemAfterEdit: String
            val jsonArr = JSONArray(result)

            for (items in 0 until jsonArr.length()) {
                val itemsName = jsonArr.getString(items)
                Toast.makeText(applicationContext, itemsName, Toast.LENGTH_SHORT).show()
//                listOfItems.add(Items(itemsName))
            }
        }
    }

    /*  lay gia tri tai vi tri giua cua chuoi
    so sanh gia tri do voi " " neu bang tra ve chuoi moi
    neu chi co 1 dau " ", ngat chu ngay cho do
    else se di chuyen ve 2 phia den khi tim dc " " moi-> so sanh 2 chuoi sau khi tach neu nho nhat -> tach chuoi */


}
