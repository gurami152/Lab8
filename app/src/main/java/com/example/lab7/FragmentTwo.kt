package com.example.lab7

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.PopupMenu
import android.widget.TextView

class FragmentTwo : Fragment(){

    private lateinit var tV6 : TextView
    private lateinit var tV7 : TextView
    private lateinit var btn3: Button
    private lateinit var checkBox:CheckBox
    private var playTime:Int= 60000
    private lateinit var familyText:String
    private lateinit var nameText:String
    private lateinit var emailText:String

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_two,container,false)
        checkBox = v.findViewById<View>(R.id.checkBox) as CheckBox
        btn3 = v.findViewById<View>(R.id.button3) as Button
        btn3.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("family", familyText)
            bundle.putString("name", nameText)
            bundle.putString("email", emailText)
            bundle.putInt("time",playTime)
            if(checkBox.isChecked){
                bundle.putBoolean("background",true)
            }
            else {bundle.putBoolean("background",false)}
            val manager = activity!!.supportFragmentManager
            val transaction = manager.beginTransaction()
            val fragment = FragmentGame()
            fragment.arguments=bundle
            transaction.replace(R.id.fragment_holder,fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        tV6 = v.findViewById<View>(R.id.textView6) as TextView
        tV7 = v.findViewById<View>(R.id.textView7) as TextView
        val bundle = arguments
        familyText = bundle!!.getString("family").toString()
        nameText = bundle.getString("name").toString()
        emailText=bundle.getString("email").toString()
        val popupMenu = PopupMenu(activity, tV6)
        popupMenu.inflate(R.menu.menu_popup)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu1 -> {
                    tV6.text = "10 sec"
                    playTime=10000
                    true
                }
                R.id.menu2 -> {
                    tV6.text = "20 sec"
                    playTime=20000
                    true
                }
                R.id.menu3 -> {
                    tV6.text = "30 sec"
                    playTime=30000
                    true
                }
                R.id.menu4 -> {
                    tV6.text = "40 sec"
                    playTime=40000
                    true
                }
                R.id.menu5 -> {
                    tV6.text = "50 sec"
                    playTime=50000
                    true
                }
                R.id.menu6 -> {
                    tV6.text = "60 sec"
                    playTime=60000
                    true
                }
                else -> false
            }
        }
        tV6.setOnClickListener{
            popupMenu.show()
        }
        return v

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onDetach() {
        super.onDetach()
    }
}
