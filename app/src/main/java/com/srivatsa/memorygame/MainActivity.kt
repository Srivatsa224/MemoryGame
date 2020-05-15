package com.srivatsa.memorygame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import com.srivatsa.memorygame.R.drawable.*
import java.text.FieldPosition

private const val TAG="MainActivity"

class MainActivity : AppCompatActivity() {

    //instance variable
    private lateinit var buttons: List<ImageButton>
    private lateinit var cards: List<MemoryCard>
    private var indexOfSingleSelectedCard: Int?=null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val images= mutableListOf(csk, mi, dc, rcb)
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
                //update models onClick and UI of the game
                updateModels(index)
                updateViews()
            }
        }
        resetBtn.setOnClickListener {
                // code to perform reset action
            restoreCards()
            updateViews()
            }
    }

    private fun updateViews()
    {
        cards.forEachIndexed { index, card->
            val button=buttons[index]
            if(card.isMatch){
                button.alpha=0.2f
            }
            button.setImageResource(if(card.isFaceUp)card.identifier else ipl)
        }
    }

    private fun updateModels(position: Int)
    {
        val card=cards[position]
        //error checking
        if (card.isFaceUp){
            Toast.makeText(this,"Invalid move!", Toast.LENGTH_SHORT).show()
            return
        }
        //3 cases
        //0 cards previously flipped  -> restore cards + flip over selected card
        //1 cards previously flipped  -> flip over the selected card + check if same ID
        //2 cards previously flipped  ->  restore cards + flip over selected cards
        if (indexOfSingleSelectedCard==null)
        {
            // 0 or 2 cards sleected previously
            restoreCards()
            indexOfSingleSelectedCard=position
        }
        else
        {
            checkforMatch(indexOfSingleSelectedCard!!, position)
            indexOfSingleSelectedCard=null

        }

        card.isFaceUp=!card.isFaceUp
    }

    private fun restoreCards() {
        for (card in cards)
        {
            if(!card.isMatch)
            {
                card.isFaceUp=false
            }
        }
    }

    private fun checkforMatch(position1: Int, position2: Int) {

        if(cards[position1].identifier==cards[position2].identifier)
        {


            Toast.makeText(this,"It's a match!", Toast.LENGTH_SHORT).show()
            cards[position1].isMatch=true
            cards[position2].isMatch=true
        }

    }
}
