package com.example.lab7

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.firebase.database.*

class FragmentResult : Fragment() {

    private lateinit var v : View
    private lateinit var tV2:TextView
    private lateinit var tV:TextView
    private lateinit var btnShare: Button
    lateinit var _db: DatabaseReference

    private val users: MutableList<User> = mutableListOf()

    private var familyText:String =""
    private var nameText:String =""
    private var emailText:String =""
    private var score:Int = 0
    private var count:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bundle = arguments
        familyText = bundle!!.getString("family").toString()
        nameText = bundle.getString("name").toString()
        emailText = bundle.getString("email").toString()
        score = bundle.getInt("score")
        count = bundle.getInt("count")
        _db = FirebaseDatabase.getInstance().reference
        writeNewResult(familyText, nameText, emailText, score, count)
        v = inflater.inflate(R.layout.fragment_result, container, false)
        tV2 = v.findViewById<View>(R.id.textView2) as TextView
        tV = v.findViewById<View>(R.id.textView) as TextView
        tV2.text = "Было задано $count вопросов, из которых Вы правльно ответили на $score"
        btnShare = v.findViewById<View>(R.id.button) as Button
        btnShare.setOnClickListener {
            share()
        }
      // var ref = FirebaseDatabase.getInstance().getReference("users")//.child(familyText)
        initUsersStat();

        //ref.addListenerForSingleValueEvent(userListener)
        // Inflate the layout for this fragment
        return v
    }

    private fun initUsersStat() {
        val userListener = object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }

            @SuppressLint("SetTextI18n")
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.mapNotNullTo(users) {
                    it.getValue<User>(User::class.java)
                    // Get Post object and use the values to update the UI
                    // val user = dataSnapshot.getValue(User::class.java)
                    // tV.text = user?.family +" "+ user?.name +" "+user?.score+"/"+user?.count
                }
                users.forEach{
                    var str:String = tV.text as String
                    var str2:String = it.family +" "+ it.name +" "+it.score+"/"+it.count
                    tV.text =str.plus("\n").plus(str2)
                    println(it.family)
                }
            }

        }
        _db.child("users").addListenerForSingleValueEvent(userListener)

    }

    private fun share(){
        val email = Intent(Intent.ACTION_SEND)
        //Указываем получателя
        email.putExtra(Intent.EXTRA_EMAIL, arrayOf(emailText))
        //Устанавливаем Тему сообщения
        email.putExtra(Intent.EXTRA_SUBJECT,"Результаты в игре Соответствие цветов")
        //Устанавливаем само сообщение
        email.putExtra(Intent.EXTRA_TEXT,"Было задано $count вопросов, из которых Вы правльно ответили на $score" )
        //тип отправляемого сообщения
        email.type = "message/rfc822"
        //Вызываем intent выбора клиента для отправки сообщения
        startActivity(Intent.createChooser(email, "Выберите email клиент :"))
    }

    private fun writeNewResult(userId: String, name: String, email: String,score:Int,count:Int) {
        val key = _db.child("users").push().key
        val user = User(userId,name, email, score, count)
        user.uuid = key.toString()
        _db.child("users").child(key.toString()).setValue(user)

    }
}

@IgnoreExtraProperties
data class User( var family: String="",var  name: String="",var  email: String="",var  score: Int=0, var  count: Int=0, var uuid: String = "")
{

}
