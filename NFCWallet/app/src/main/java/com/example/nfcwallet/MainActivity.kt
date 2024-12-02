package com.example.nfcwallet

import CardListAdapter
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nfcwallet.data.PaymentMethod
import com.example.nfcwallet.data.PaymentMethodList
import com.example.nfcwallet.databinding.ActivityMainBinding
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var cardsList : List<PaymentMethod> = emptyList()

    private suspend fun sendPaymentMethodListRequest(customerId: String): PaymentMethodList? {
        val apiService = Utils.retrofit.create(ApiService::class.java)
        try {
            return apiService.listPaymentMethods(customerId)
        } catch (e: Exception) {
            println("Error occurred: ${e.message}")
        }
        return null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        val cardsListView = binding.cardsList
        val customerId = "cus_R4YRXTzRhA6lJt"
        cardsList = emptyList()
        try {
            runBlocking {
                cardsList = sendPaymentMethodListRequest(customerId)!!.data
            }
        }catch(e:Exception){
            println(e.message)
        }
        cardsListView.adapter = CardListAdapter(this@MainActivity, cardsList)
        cardsListView.setOnItemClickListener { _, _, position, _ ->
            val pmId = cardsList[position].id
            var i = Intent(this, CardActivity::class.java)
            i.putExtra("pmId",pmId)
            startActivity(i)
        }
        val addCardButton = binding.addCardBtn
        addCardButton.setOnClickListener{
            val i2 = Intent(this, AddCardActivity::class.java)
            startActivity(i2)
        }

    }

}
