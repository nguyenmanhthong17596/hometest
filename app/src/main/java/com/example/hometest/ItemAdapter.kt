package com.example.hometest

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_list.*

class ItemAdapter (var itemList: ArrayList<Items>) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_list,parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: Items = itemList[position]
        handleData(item.word)
        val context : Context? = null
        holder.itemName.text = context?.getString(R.string.item_name,
                firstWord,
                lastWord
            )
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val itemName = itemView.findViewById(R.id.txt_itemname) as TextView
    }

    fun handleData(name: String)  {

        // chuyen chuoi thanh mang ki tu
        val midPosition = name.length / 2
        var delta1 = 0
        var delta2 = 0

        // chuoi sau khi tach
        var first: String
        var last: String
        var textAfterChange: String = ""
        var firstWord1: String = ""
        var firstWord2: String = ""

        var lastWord1: String = ""
        var lastWord2: String = ""

        val arr = name.split(" ")
        // neu co 2 tu thi ngat chu ngay cho dau " "
        if (arr.size-1 == 1) {
            first = arr[0]
            last = arr[1]
            textAfterChange = first + "\n" + last
        } else {
            for (i in midPosition  downTo 0) {
                if (delta2 > 0) {
                    for (j in midPosition until name.length) {
                        val wordToSplit2 = name[j]
                        if (wordToSplit2 == ' ') {
                            first = name.substring(0,j)
                            last = name.substring(j)
                            delta2 = last.length - first.length

                            // choose test
                            if (delta2 > 0) {
                                firstWord2 = first
                                lastWord2 = last
                                Log.i("THONG", "String1 : $first-String2: $last")
                            } else break
                        }
                    }
                }

                val wordToSplit1 = name[i]
                if (wordToSplit1 == ' ') {
                    first = name.substring(0,i)
                    last = name.substring(i)

                    // set text for UI
                    delta1 = last.length - first.length
                    if (delta1 > 0) {
                        firstWord1 = first
                        lastWord1 = last
                        Log.i("THONG", "String1 : $first - String2: $last")
                    } else break
                }
            }
        }
        Log.i("thong", textAfterChange)

        if (delta1 < delta2 ) {
            firstWord = firstWord1
            lastWord = lastWord1
        } else {
            firstWord = firstWord2
            lastWord = lastWord2
        }

        Log.i("thong", "text cui cung : $textAfterChange")

    }

    companion object {
        var firstWord: String = ""
        var lastWord: String = ""
    }

}