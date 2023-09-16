package com.ozmenkahveci.todolist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ozmenkahveci.todolist.databinding.FragmentListBinding


class ListFragment : Fragment() {
    private lateinit var todolistList: ArrayList<Lists>
    private lateinit var adapter: ListAdapter

    private lateinit var vt:VeritabaniYardimcisi

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            _binding = FragmentListBinding.inflate(inflater,container,false)

            return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vt = VeritabaniYardimcisi(requireContext())

        binding.rv.setHasFixedSize(true)
        binding.rv.layoutManager= LinearLayoutManager(requireContext())

        tumListAl()

        binding.fab.setOnClickListener {
            alertGoster()
        }
    }
    fun alertGoster() {
        val tasarim = LayoutInflater.from(requireContext()).inflate(R.layout.alert_tasarim, null)
        val todoListET = tasarim.findViewById(R.id.todoListET) as EditText
        val calendarView = tasarim.findViewById(R.id.calendarView) as CalendarView

        val list = AlertDialog.Builder(requireContext())

        // Seçilen tarihi depolamak için değişkenler
        var selectedYear = 0
        var selectedMonth = 0
        var selectedDay = 0

        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            selectedYear = year
            selectedMonth = month + 1 // 0-11 arası değerleri 1-12'ye dönüştür
            selectedDay = dayOfMonth
        }

        list.setTitle("Yapılacak Aktivite Ekleyin")
        list.setView(tasarim)
        list.setPositiveButton("Ekle") { dialogInterface, i ->
            val list_todo = todoListET.text.toString().trim()

            // Seçilen tarihi kullanarak formattedDate oluştur
            val list_date = "$selectedDay-$selectedMonth-$selectedYear"

            Listdao().listEkle(vt, list_todo, list_date)
            tumListAl()

            Toast.makeText(
                requireContext(),
                "$list_todo - $list_date  EKLENDİ!!",
                Toast.LENGTH_LONG
            ).show()
        }
        list.setNegativeButton("İptal") { dialogInterface, i -> }
        list.create().show()
    }


    fun tumListAl(){
        todolistList = Listdao().tumList(vt)
        adapter = ListAdapter(requireContext(),todolistList,vt)
        binding.rv.adapter = adapter
    }


}