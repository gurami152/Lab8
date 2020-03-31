package com.example.lab7

import android.annotation.SuppressLint
import android.content.res.TypedArray
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class FragmentGame : Fragment() {

    private lateinit var v : View
    private lateinit var btn: Button
    private lateinit var btn2: Button
    private lateinit var tV9 : TextView
    private lateinit var tV13 : TextView
    private lateinit var tV14 : TextView
    private var colorRight : Int = 1
    private var score: Int =0
    private var numberOfQuestions: Int = 0
    private var colorLeftText: Int = 1
    private var colorRightText: Int = 1
    private var colorLeft: Int = 1


    // переменные получнные из других фрагментов

    private var playTime:Int = 60000
    private var familyText:String =""
    private var nameText:String =""
    private var emailText:String =""
    private var backgroundChange :Boolean=true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_game,container,false)
        // получаем из Bundle
        val bundle = arguments
        familyText= bundle!!.getString("family").toString()
        nameText=bundle.getString("name").toString()
        emailText=bundle.getString("email").toString()
        playTime=bundle.getInt("time")
        backgroundChange = bundle.getBoolean("background")

        btn = v.findViewById<View>(R.id.button4) as Button
        btn.setOnClickListener {
            numberOfQuestions++
            if(colorRight==colorLeftText){
                score++
            }
            generateColors()
        }
        btn2 = v.findViewById<View>(R.id.button5) as Button
        btn2.setOnClickListener {
            numberOfQuestions++
            if(colorRight!=colorLeft){
                score++
            }
            generateColors()
        }
        tV9 = v.findViewById<View>(R.id.textView9) as TextView
        tV13 = v.findViewById<View>(R.id.textView13) as TextView
        tV14= v.findViewById<View>(R.id.textView14) as TextView
        generateColors()
        // таймер
        val timer = object: CountDownTimer(playTime.toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                tV9.text = "Времени осталось 0:" + millisUntilFinished / 1000
            }

            override fun onFinish() {
                resultsFragment()
            }
        }
        timer.start()
        // Inflate the layout for this fragment
        return v
    }

    private fun resultsFragment() {
        val bundle = Bundle()
        bundle.putString("family", familyText)
        bundle.putString("name", nameText)
        bundle.putString("email", emailText)
        bundle.putInt("score",score)
        bundle.putInt("count",numberOfQuestions)

        val manager = activity!!.supportFragmentManager
        val transaction = manager.beginTransaction()
        val fragment = FragmentResult()
        fragment.arguments=bundle
        transaction.replace(R.id.fragment_holder,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun rand() : Int {
        return (0..6).random()
    }

    @SuppressLint("Recycle", "ResourceType")
    fun generateColors() {

        val colors: TypedArray = resources.obtainTypedArray(R.array.rainbow)
        val strings: Array<out String> = resources.getStringArray(R.array.color_name)
        var color= rand()
        tV13.setTextColor(colors.getColor(color,0))
        colorLeft=color

        color = rand()
        tV13.text = strings[color]
        colorLeftText=color

        color = rand()
        tV14.setTextColor(colors.getColor(color,0))
        colorRight=color

        color = rand()
        tV14.text = strings[color]
        colorRightText= color
        if(backgroundChange) {
            while ((color == colorRight) || (color == colorLeft)) {
                color = rand()
            }
           v.setBackgroundColor(colors.getColor(color, 0))
        }

    }



}
