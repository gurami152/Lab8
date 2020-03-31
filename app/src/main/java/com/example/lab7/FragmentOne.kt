package com.example.lab7

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText


class FragmentOne : Fragment(){

    private lateinit var familyEdit: EditText
    private lateinit var nameEdit: EditText
    private lateinit var emailEdit: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_one,container,false)
        val btn = v.findViewById<View>(R.id.button8) as Button
        familyEdit = v.findViewById(R.id.editText5) as EditText
        nameEdit = v.findViewById(R.id.editText6) as EditText
        emailEdit = v.findViewById(R.id.editText8) as EditText
        btn.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("family", familyEdit.text.toString())
            bundle.putString("name", nameEdit.text.toString())
            bundle.putString("email", emailEdit.text.toString())
            val manager = activity!!.supportFragmentManager
            val transaction = manager.beginTransaction()
            val fragment = FragmentTwo()
            fragment.arguments = bundle
            transaction.replace(R.id.fragment_holder,fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        return v
    }

}
