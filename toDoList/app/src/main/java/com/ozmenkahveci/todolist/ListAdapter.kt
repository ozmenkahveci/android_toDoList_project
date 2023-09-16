package com.ozmenkahveci.todolist

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.PopupMenu
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.snackbar.Snackbar
import java.util.Calendar

class ListAdapter(private val mContext: Context
,private var todolistList:List<Lists>
,private val vt:VeritabaniYardimcisi): RecyclerView.Adapter<ListAdapter.CardTasarimTutucu>() {


    inner class CardTasarimTutucu(tasarim:View):RecyclerView.ViewHolder(tasarim){
        var list_card: CardView
        var todo_listTV: TextView
        var noktaIV: ImageView

        init {
            list_card = tasarim.findViewById(R.id.list_card)
            todo_listTV =tasarim.findViewById(R.id.todo_listTV)
            noktaIV = tasarim.findViewById(R.id.noktaIV)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardTasarimTutucu {
        val tasarim = LayoutInflater.from(mContext).inflate(R.layout.todo_list_tasarim,parent,false)
        return CardTasarimTutucu(tasarim)
    }

    override fun getItemCount(): Int {
        return todolistList.size
    }

    override fun onBindViewHolder(holder: CardTasarimTutucu, position: Int) {

        val todo = todolistList.get(position)

        holder.todo_listTV.text = "${todo.list_date}"

        holder.list_card.setOnClickListener {

            alertDetay(todo)

        }

        holder.noktaIV.setOnClickListener {

            val popupMenu = PopupMenu(mContext,holder.noktaIV)
            popupMenu.menuInflater.inflate(R.menu.popup_menu,popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { menuItem ->

                when(menuItem.itemId){
                    R.id.action_sil ->{
                        Snackbar.make(holder.noktaIV,"${todo.list_todo} Silinsin mi?", Snackbar.LENGTH_LONG).setAction("Evet"){
                            Listdao().listSil(vt,todo.list_id)
                            todolistList = Listdao().tumList(vt)
                            notifyDataSetChanged()
                        }.show()
                        true
                    }
                    R.id.action_guncelle -> {
                        alertGoster(todo)
                        true
                    }
                    else -> false
                }
            }

            popupMenu.show()
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun alertGoster(todo:Lists){
        val tasarim = LayoutInflater.from(mContext).inflate(R.layout.alert_tasarim,null)
        val todoListET = tasarim.findViewById(R.id.todoListET) as EditText
        val calendarView = tasarim.findViewById(R.id.calendarView) as CalendarView
        //val todoDateET = tasarim.findViewById(R.id.todoDateET) as EditText

        todoListET.setText(todo.list_todo)

        var selectedYear = 0
        var selectedMonth = 0
        var selectedDay = 0

        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            selectedYear = year
            selectedMonth = month + 1 // 0-11 arası değerleri 1-12'ye dönüştür
            selectedDay = dayOfMonth
        }

        val list = AlertDialog.Builder(mContext)

        list.setTitle("Aktiviteyi Güncelle")
        list.setView(tasarim)
        list.setPositiveButton("Güncelle"){
                dialogInterface, i ->
            val list_todo = todoListET.text.toString().trim()
            //val list_date = todoDateET.text.toString().trim()

            val list_date = "$selectedDay-$selectedMonth-$selectedYear".trim()


            Listdao().listGuncelle(vt,todo.list_id,list_todo,list_date)
            todolistList = Listdao().tumList(vt)
            notifyDataSetChanged()

            Toast.makeText(mContext,"$list_todo - $list_date  Güncellendi!!", Toast.LENGTH_LONG).show()

        }
        list.setNegativeButton("İptal"){
                dialogInterface, i ->


        }
        list.create().show()
    }
    @SuppressLint("MissingInflatedId")
    fun alertDetay(todo:Lists){
        val tasarim = LayoutInflater.from(mContext).inflate(R.layout.alert_detay,null)
        val dateDetayTV = tasarim.findViewById(R.id.dateDetayTV) as TextView
        val todoDetayTV = tasarim.findViewById(R.id.todoDetayTV) as TextView

        dateDetayTV.setText(todo.list_date)
        todoDetayTV.setText(todo.list_todo)

        val list = AlertDialog.Builder(mContext)

        list.setView(tasarim)
        list.create().show()
    }

}