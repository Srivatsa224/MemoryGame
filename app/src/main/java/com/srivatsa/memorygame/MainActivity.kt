package com.srivatsa.memorygame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import kotlinx.android.synthetic.main.activity_main.*
import com.srivatsa.memorygame.R.drawable.*

private const val TAG="MainActivity"

class MainActivity : AppCompatActivity() {

    //instance variable
    private lateinit var buttons: List<ImageButton>
    private lateinit var cards: List<MemoryCard>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val images= mutableListOf(ic_fire, ic_phone, ic_plane, ic_watch)
        //Add each image place
        images.addAll(images)
        //making images random
        images.shuffle()

        buttons= listOf(imageButton1, imageButton2,imageButton3,imageButton4,
            imageButton5,imageButton6,imageButton7,imageButton8)
            cards= buttons.indices.map{ index->
            MemoryCard(images[index])
        }
        buttons.forEachIndexed { index,button ->
            button.setOnClickListener {
                Log.i(TAG,"Button Clicked")
                val card=cards[index]
                card.isFaceUp=!card.isFaceUp
                //in-line if/else
                button.setImageResource(if(card.isFaceUp)images[index] else ic_code)


            }
        }

    }
}
